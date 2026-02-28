package committee.nova.mods.avaritia_integration.module.create.content.extreme_depot;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllShapes;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.Create;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.belt.behaviour.DirectBeltInputBehaviour;
import com.simibubi.create.content.logistics.box.PackageEntity;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.block.ProperWaterloggedBlock;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.item.ItemHelper;
import committee.nova.mods.avaritia_integration.module.create.registry.CreateIntegrationBlockEntityTypes;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ExtremeDepotBlock extends Block implements IBE<ExtremeDepotBlockEntity>, IWrenchable, ProperWaterloggedBlock {
    public ExtremeDepotBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false));
    }

    protected static ExtremeDepotBehaviour get(BlockGetter worldIn, BlockPos pos) {
        return BlockEntityBehaviour.get(worldIn, pos, ExtremeDepotBehaviour.TYPE);
    }

    public static int getComparatorInputOverride(BlockState blockState, Level worldIn, BlockPos pos) {
        ExtremeDepotBehaviour depotBehaviour = get(worldIn, pos);
        if (depotBehaviour == null)
            return 0;
        float f = depotBehaviour.getPresentStackSize();
        Integer max = depotBehaviour.maxStackSize.get();
        f = f / (max == 0 ? 64 : max);
        return Mth.clamp(Mth.floor(f * 14.0F) + (f > 0 ? 1 : 0), 0, 15);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(WATERLOGGED));
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return fluidState(pState);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState,
                                  LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        updateWater(pLevel, pState, pCurrentPos);
        return pState;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return withWater(super.getStateForPlacement(pContext), pContext);
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_,
                               CollisionContext p_220053_4_) {
        return AllShapes.CASING_13PX.get(Direction.UP);
    }

    @Override
    public Class<ExtremeDepotBlockEntity> getBlockEntityClass() {
        return ExtremeDepotBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends ExtremeDepotBlockEntity> getBlockEntityType() {
        return CreateIntegrationBlockEntityTypes.EXTREME_DEPOT.get();
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult ray) {
        if (ray.getDirection() != Direction.UP)
            return InteractionResult.PASS;
        if (world.isClientSide)
            return InteractionResult.SUCCESS;

        ExtremeDepotBehaviour behaviour = get(world, pos);
        if (behaviour == null)
            return InteractionResult.PASS;
        if (!behaviour.canAcceptItems.get())
            return InteractionResult.SUCCESS;

        ItemStack heldItem = player.getItemInHand(hand);
        boolean shouldntPlaceItem = AllBlocks.MECHANICAL_ARM.isIn(heldItem);

        ItemStack mainItemStack = behaviour.getHeldItemStack();
        if (!mainItemStack.isEmpty() && !heldItem.isEmpty() && ItemStack.isSameItemSameTags(mainItemStack, heldItem)) {
            ExtremeTransportedItemStack incoming = new ExtremeTransportedItemStack(heldItem, 1024);
            int originalCount = heldItem.getCount();

            behaviour.addHeldItem(incoming);

            if (heldItem.getCount() < originalCount) {
                AllSoundEvents.DEPOT_SLIDE.playOnServer(world, pos);
                behaviour.blockEntity.notifyUpdate();
                return InteractionResult.SUCCESS;
            }
        }

        if (!mainItemStack.isEmpty()) {
            giveItemToPlayer(player, mainItemStack);
            behaviour.removeHeldItem();
            world.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, .2f, 1f + Create.RANDOM.nextFloat());
        }

        ItemStackHandler outputs = behaviour.processingOutputBuffer;
        for (int i = 0; i < outputs.getSlots(); i++) {
            giveItemToPlayer(player, outputs.extractItem(i, 64, false));
        }

        if (!heldItem.isEmpty() && !shouldntPlaceItem) {
            ExtremeTransportedItemStack transported = new ExtremeTransportedItemStack(heldItem.copy(), 1024);
            transported.insertedFrom = player.getDirection();
            transported.prevBeltPosition = .25f;
            transported.beltPosition = .25f;

            behaviour.setHeldItem(transported);
            player.setItemInHand(hand, ItemStack.EMPTY);
            AllSoundEvents.DEPOT_SLIDE.playOnServer(world, pos);
        }

        behaviour.blockEntity.notifyUpdate();
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        IBE.onRemove(state, worldIn, pos, newState);
    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter worldIn, Entity entityIn) {
        super.updateEntityAfterFallOn(worldIn, entityIn);
        ItemStack asItem = ItemHelper.fromItemEntity(entityIn);
        if (asItem.isEmpty())
            return;
        if (entityIn.level().isClientSide)
            return;

        BlockPos pos = entityIn.blockPosition();
        DirectBeltInputBehaviour inputBehaviour = BlockEntityBehaviour.get(worldIn, pos, DirectBeltInputBehaviour.TYPE);
        if (inputBehaviour == null)
            return;
        Vec3 targetLocation = VecHelper.getCenterOf(pos)
                .add(0, 5 / 16f, 0);
        if (!PackageEntity.centerPackage(entityIn, targetLocation))
            return;

        ItemStack remainder = inputBehaviour.handleInsertion(asItem, Direction.DOWN, false);
        if (entityIn instanceof ItemEntity)
            ((ItemEntity) entityIn).setItem(remainder);
        if (remainder.isEmpty())
            entityIn.discard();
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
        return getComparatorInputOverride(blockState, worldIn, pos);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
        return false;
    }

    private void giveItemToPlayer(Player player, ItemStack stack) {
        if (stack.isEmpty()) return;

        if (stack.getCount() <= stack.getMaxStackSize()) {
            player.getInventory().placeItemBackInInventory(stack);
            return;
        }

        while (!stack.isEmpty()) {
            int splitCount = Math.min(stack.getCount(), stack.getMaxStackSize());
            ItemStack splitStack = stack.split(splitCount);
            player.getInventory().placeItemBackInInventory(splitStack);
        }
    }

}
