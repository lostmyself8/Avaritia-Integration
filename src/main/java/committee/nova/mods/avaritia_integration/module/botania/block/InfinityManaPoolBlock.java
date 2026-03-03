package committee.nova.mods.avaritia_integration.module.botania.block;

import committee.nova.mods.avaritia_integration.module.botania.entity.InfinityManaPoolBlockEntity;
import committee.nova.mods.avaritia_integration.module.botania.registry.BotaniaIntegrationBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.api.state.BotaniaStateProperties;
import vazkii.botania.common.block.BotaniaWaterloggedBlock;
import vazkii.botania.common.block.decor.BotaniaMushroomBlock;
import vazkii.botania.common.item.material.MysticalPetalItem;

import java.util.Optional;

/**
 * @author cnlimiter
 */
public class InfinityManaPoolBlock extends BotaniaWaterloggedBlock implements EntityBlock {
    private static final VoxelShape NORMAL_SHAPE;
    private static final VoxelShape NORMAL_SHAPE_INTERACT = box(0.0F, 0.0F, 0.0F, 16.0F, 8.0F, 16.0F);

    public InfinityManaPoolBlock(BlockBehaviour.Properties builder) {
        super(builder);
        this.registerDefaultState(this.defaultBlockState().setValue(BotaniaStateProperties.OPTIONAL_DYE_COLOR, BotaniaStateProperties.OptionalDyeColor.NONE));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BotaniaStateProperties.OPTIONAL_DYE_COLOR);
    }

    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
        return NORMAL_SHAPE;
    }

    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return NORMAL_SHAPE_INTERACT;
    }

    public @NotNull VoxelShape getInteractionShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return NORMAL_SHAPE_INTERACT;
    }

    public @NotNull InteractionResult use(@NotNull BlockState state, Level world, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        BlockEntity be = world.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand);
        Optional<DyeColor> itemColor = Optional.empty();
        Item var11 = stack.getItem();
        if (var11 instanceof MysticalPetalItem petalItem) {
            itemColor = Optional.of(petalItem.color);
        }

        Block var15 = Block.byItem(stack.getItem());
        if (var15 instanceof BotaniaMushroomBlock mushroomBlock) {
            itemColor = Optional.of(mushroomBlock.color);
        }

        if (itemColor.isPresent() && be instanceof InfinityManaPoolBlockEntity pool) {
            if (!itemColor.equals(pool.getColor())) {
                pool.setColor(itemColor);
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }

                return InteractionResult.sidedSuccess(world.isClientSide());
            }
        }

        if (stack.is(Items.CLAY_BALL) && be instanceof InfinityManaPoolBlockEntity pool) {
            if (pool.getColor().isPresent()) {
                pool.setColor(Optional.empty());
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }

                return InteractionResult.sidedSuccess(world.isClientSide());
            }
        }

        return super.use(state, world, pos, player, hand, hit);
    }

    public @NotNull BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new InfinityManaPoolBlockEntity(pos, state);
    }

    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, BotaniaIntegrationBlockEntities.INFINITY_MANA_POOL.get(), level.isClientSide ? InfinityManaPoolBlockEntity::clientTick : InfinityManaPoolBlockEntity::serverTick);
    }

    public void entityInside(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Entity entity) {
        if (entity instanceof ItemEntity item) {
            InfinityManaPoolBlockEntity tile = (InfinityManaPoolBlockEntity) world.getBlockEntity(pos);
            tile.collideEntityItem(item);
        }

    }

    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
        return true;
    }

    public int getAnalogOutputSignal(@NotNull BlockState state, Level world, @NotNull BlockPos pos) {
        InfinityManaPoolBlockEntity pool = (InfinityManaPoolBlockEntity) world.getBlockEntity(pos);
        return InfinityManaPoolBlockEntity.calculateComparatorLevel(pool.getCurrentMana(), pool.getMaxMana());
    }

    static {
        VoxelShape cutout = box(2.0F, 2.0F, 2.0F, 14.0F, 16.0F, 14.0F);
        NORMAL_SHAPE = Shapes.join(NORMAL_SHAPE_INTERACT, cutout, BooleanOp.ONLY_FIRST);
    }
}
