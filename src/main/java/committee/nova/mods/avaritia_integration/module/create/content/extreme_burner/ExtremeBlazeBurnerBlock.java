package committee.nova.mods.avaritia_integration.module.create.content.extreme_burner;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllShapes;
import com.simibubi.create.api.schematic.requirement.SpecialBlockItemRequirement;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.logistics.stockTicker.StockTickerBlockEntity;
import com.simibubi.create.content.logistics.stockTicker.StockTickerInteractionHandler;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.simibubi.create.content.schematics.requirement.ItemRequirement;
import com.simibubi.create.foundation.block.IBE;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import committee.nova.mods.avaritia_integration.module.create.CreateModule;
import committee.nova.mods.avaritia_integration.module.create.registry.CreateIntegrationBlockEntityTypes;
import committee.nova.mods.avaritia_integration.module.create.registry.CreateIntegrationBlocks;
import net.createmod.catnip.lang.Lang;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.util.FakePlayer;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ExtremeBlazeBurnerBlock extends HorizontalDirectionalBlock implements IBE<ExtremeBlazeBurnerBlockEntity>, IWrenchable, SpecialBlockItemRequirement {
    public static final EnumProperty<ExtremeHeatLevel> EXTREME_HEAT_LEVEL = EnumProperty.create("extreme_blaze", ExtremeHeatLevel.class);

    public ExtremeBlazeBurnerBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(EXTREME_HEAT_LEVEL, ExtremeHeatLevel.SMOULDERING)
        .setValue(BlazeBurnerBlock.HEAT_LEVEL, BlazeBurnerBlock.HeatLevel.KINDLED));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(EXTREME_HEAT_LEVEL, BlazeBurnerBlock.HEAT_LEVEL, FACING);
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState p_220082_4_, boolean p_220082_5_) {
        if (world.isClientSide)
            return;
        BlockEntity blockEntity = world.getBlockEntity(pos.above());
        if (!(blockEntity instanceof BasinBlockEntity basin))
            return;
        basin.notifyChangeOfContents();
    }

    @Override
    public Class<ExtremeBlazeBurnerBlockEntity> getBlockEntityClass() {
        return ExtremeBlazeBurnerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends ExtremeBlazeBurnerBlockEntity> getBlockEntityType() {
        return CreateIntegrationBlockEntityTypes.EXTREME_HEATER.get();
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockRayTraceResult) {
        ItemStack heldItem = player.getItemInHand(hand);

        if (AllItems.GOGGLES.isIn(heldItem)) {
            return onBlockEntityUse(world, pos, ebe -> {
                if (ebe.goggles)
                    return InteractionResult.PASS;
                ebe.goggles = true;
                ebe.notifyUpdate();
                return InteractionResult.SUCCESS;
            });
        }

        ExtremeBlazeBurnerBlockEntity be = getBlockEntity(world, pos);
        if (be != null && be.stockKeeper) {
            StockTickerBlockEntity stockTicker = BlazeBurnerBlockEntity.getStockTicker(world, pos);
            if (stockTicker != null) {
                StockTickerInteractionHandler.interactWithLogisticsManagerAt(player, world, stockTicker.getBlockPos());
                return InteractionResult.SUCCESS;
            }
        }

        if (heldItem.isEmpty()) {
            return onBlockEntityUse(world, pos, ebe -> {
                if (!ebe.goggles) {
                    return InteractionResult.PASS;
                }
                ebe.goggles = false;
                ebe.notifyUpdate();
                return InteractionResult.SUCCESS;
            });
        }

        boolean doNotConsume = player.isCreative();
        boolean forceOverFlow = !(player instanceof FakePlayer);

        InteractionResultHolder<ItemStack> res = tryInsert(state, world, pos, heldItem, doNotConsume, forceOverFlow, false);
        ItemStack leftOver = res.getObject();
        if (!world.isClientSide && !doNotConsume && !leftOver.isEmpty()) {
            if (heldItem.isEmpty()) {
                player.setItemInHand(hand, leftOver);
            } else if (!player.getInventory().add(leftOver)) {
                player.drop(leftOver, false);
            }
        }

        return res.getResult() == InteractionResult.SUCCESS ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    public static InteractionResultHolder<ItemStack> tryInsert(BlockState state, Level world, BlockPos pos, ItemStack stack, boolean doNotConsume, boolean forceOverflow, boolean simulate) {
        if (!state.hasBlockEntity()) return InteractionResultHolder.fail(ItemStack.EMPTY);

        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof ExtremeBlazeBurnerBlockEntity burnerBE)) return InteractionResultHolder.fail(ItemStack.EMPTY);

        if (burnerBE.isCreativeFuel(stack)) {
            if (!simulate) burnerBE.applyCreativeFuel();
            return InteractionResultHolder.success(ItemStack.EMPTY);
        }

        if (!burnerBE.tryUpdateFuel(stack, forceOverflow, simulate)) return InteractionResultHolder.fail(ItemStack.EMPTY);

        if (!doNotConsume) {
            ItemStack container = stack.hasCraftingRemainingItem() ? stack.getCraftingRemainingItem() : ItemStack.EMPTY;
            if (!world.isClientSide) stack.shrink(1);
            return InteractionResultHolder.success(container);
        }

        return InteractionResultHolder.success(ItemStack.EMPTY);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        ItemStack stack = context.getItemInHand();
        Item item = stack.getItem();
        BlockState defaultState = defaultBlockState();
        if (!(item.equals(CreateIntegrationBlocks.EXTREME_BLAZE_BURNER.asItem()))) return defaultState;

        return defaultState.setValue(EXTREME_HEAT_LEVEL, ExtremeHeatLevel.SMOULDERING)
                .setValue(BlazeBurnerBlock.HEAT_LEVEL, BlazeBurnerBlock.HeatLevel.KINDLED)
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
        return AllShapes.HEATER_BLOCK_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState p_220071_1_, BlockGetter p_220071_2_, BlockPos p_220071_3_, CollisionContext p_220071_4_) {
        if (p_220071_4_ == CollisionContext.empty()) return AllShapes.HEATER_BLOCK_SPECIAL_COLLISION_SHAPE;
        return getShape(p_220071_1_, p_220071_2_, p_220071_3_, p_220071_4_);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState p_149740_1_) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level p_180641_2_, BlockPos p_180641_3_) {
        return Math.max(0, state.getValue(EXTREME_HEAT_LEVEL).ordinal() - 1);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (random.nextInt(10) != 0)
            return;
        world.playLocalSound((float) pos.getX() + 0.5F, (float) pos.getY() + 0.5F,
                (float) pos.getZ() + 0.5F, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS,
                0.5F + random.nextFloat(), random.nextFloat() * 0.7F + 0.6F, false);
    }

    public static BlazeBurnerBlock.HeatLevel getHeatLevelOf(BlockState blockState) {
        if (!blockState.hasProperty(EXTREME_HEAT_LEVEL)) return BlazeBurnerBlock.HeatLevel.KINDLED;

        return blockState.getValue(EXTREME_HEAT_LEVEL).isAtLeast(ExtremeHeatLevel.FADING) ? BlazeBurnerBlock.HeatLevel.SEETHING : BlazeBurnerBlock.HeatLevel.KINDLED;
    }

    public static ExtremeHeatLevel getExtremeHeatLevelOf(BlockState blockState) {
        return blockState.hasProperty(EXTREME_HEAT_LEVEL) ? blockState.getValue(EXTREME_HEAT_LEVEL) : ExtremeHeatLevel.SMOULDERING;
    }

    public static int getLight(BlockState state) {
        ExtremeHeatLevel level = state.getValue(EXTREME_HEAT_LEVEL);
        return level == ExtremeHeatLevel.SMOULDERING ? 8 : 15;
    }

    //TODO 是否要LootTable?

    //TODO 验证是否需要特殊打印材料要求
    @Override
    public ItemRequirement getRequiredItems(BlockState state, @Nullable BlockEntity blockEntity) {
        return new ItemRequirement(ItemRequirement.ItemUseType.CONSUME, CreateIntegrationBlocks.EXTREME_BLAZE_BURNER.asStack());
    }

    public static void blockStateDataGen(DataGenContext<Block, ExtremeBlazeBurnerBlock> c, RegistrateBlockstateProvider p) {
        p.getVariantBuilder(c.get())
                .forAllStates(state -> {
                    ExtremeHeatLevel heatLevel = state.getValue(EXTREME_HEAT_LEVEL);
                    Direction facing = state.getValue(FACING);
                    ResourceLocation modelLoc;

                    if (heatLevel == ExtremeHeatLevel.SMOULDERING) {
                        return ConfiguredModel.builder()
                                .modelFile(new ModelFile.UncheckedModelFile(new ResourceLocation(CreateModule.MOD_ID, "block/blaze_burner/block")))
                                .rotationY(((int) facing.toYRot() + 180) % 360)
                                .build();
                    } else {
                        String fileName = (heatLevel == ExtremeHeatLevel.STAR) ? "block_star" : "block_blaze";
                        modelLoc = p.modLoc("block/create/extreme_blaze_burner/block/" + fileName);
                    }

                    return ConfiguredModel.builder()
                            .modelFile(p.models().getExistingFile(modelLoc))
                            .rotationY(((int) facing.toYRot() + 180) % 360)
                            .build();
                });
    }

    public enum ExtremeHeatLevel implements StringRepresentable {
        SMOULDERING, FADING, BLAZE, STAR;

        public static ExtremeHeatLevel byIndex(int index) {
            return values()[index];
        }

        public ExtremeHeatLevel nextActiveLevel() {
            return byIndex((ordinal() + 1) % values().length);
        }

        public boolean isAtLeast(ExtremeHeatLevel heatLevel) {
            return ordinal() >= heatLevel.ordinal();
        }

        public String getSerializedName() {
            return Lang.asId(name());
        }
    }
}
