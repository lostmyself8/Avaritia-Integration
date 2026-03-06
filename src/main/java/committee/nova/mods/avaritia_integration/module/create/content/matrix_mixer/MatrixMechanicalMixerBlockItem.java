package committee.nova.mods.avaritia_integration.module.create.content.matrix_mixer;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.belt.BeltSlope;
import com.simibubi.create.content.kinetics.simpleRelays.CogWheelBlock;
import com.simibubi.create.content.kinetics.simpleRelays.CogwheelBlockItem;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.content.processing.AssemblyOperatorUseContext;
import com.simibubi.create.content.processing.basin.BasinBlock;
import committee.nova.mods.avaritia_integration.module.create.registry.CreateIntegrationBlocks;
import net.createmod.catnip.placement.IPlacementHelper;
import net.createmod.catnip.placement.PlacementHelpers;
import net.createmod.catnip.placement.PlacementOffset;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Predicate;

public class MatrixMechanicalMixerBlockItem extends BlockItem {
    private final int placementHelperId;
    private final int integratedCogHelperId;

    public MatrixMechanicalMixerBlockItem(MatrixMechanicalMixerBlock block, Properties builder) {
        super(block, builder);

        placementHelperId = PlacementHelpers.register(new MatrixMixerHelper());
        integratedCogHelperId = PlacementHelpers.register(new CogwheelBlockItem.IntegratedLargeCogHelper());
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);

        IPlacementHelper helper = PlacementHelpers.get(placementHelperId);
        Player player = context.getPlayer();
        BlockHitResult ray = new BlockHitResult(context.getClickLocation(), context.getClickedFace(), pos, true);
        if (helper.matchesState(state) && player != null && !player.isShiftKeyDown()) {
            return helper.getOffset(player, world, state, pos, ray)
                    .placeInWorld(world, this, player, context.getHand(), ray);
        }

        if (integratedCogHelperId != -1) {
            helper = PlacementHelpers.get(integratedCogHelperId);

            if (helper.matchesState(state) && player != null && !player.isShiftKeyDown()) {
                return helper.getOffset(player, world, state, pos, ray)
                        .placeInWorld(world, this, player, context.getHand(), ray);
            }
        }

        return super.onItemUseFirst(stack, context);
    }

    @Override
    public InteractionResult place(BlockPlaceContext context) {
        BlockPos placedOnPos = context.getClickedPos()
                .relative(context.getClickedFace()
                        .getOpposite());
        Level level = context.getLevel();
        BlockState placedOnState = level
                .getBlockState(placedOnPos);
        if (operatesOn(level, placedOnPos, placedOnState) && context.getClickedFace() == Direction.UP) {
            if (level.getBlockState(placedOnPos.above(2))
                    .canBeReplaced())
                context = adjustContext(context, placedOnPos);
            else
                return InteractionResult.FAIL;
        }

        return super.place(context);
    }

    protected BlockPlaceContext adjustContext(BlockPlaceContext context, BlockPos placedOnPos) {
        BlockPos up = placedOnPos.above(2);
        return new AssemblyOperatorUseContext(context.getLevel(), context.getPlayer(), context.getHand(), context.getItemInHand(), new BlockHitResult(new Vec3((double)up.getX() + 0.5D + (double) Direction.UP.getStepX() * 0.5D, (double)up.getY() + 0.5D + (double) Direction.UP.getStepY() * 0.5D, (double)up.getZ() + 0.5D + (double) Direction.UP.getStepZ() * 0.5D), Direction.UP, up, false));
    }

    protected boolean operatesOn(LevelReader world, BlockPos pos, BlockState placedOnState) {
        if (AllBlocks.BELT.has(placedOnState))
            return placedOnState.getValue(BeltBlock.SLOPE) == BeltSlope.HORIZONTAL;
        return BasinBlock.isBasin(world, pos) || AllBlocks.DEPOT.has(placedOnState) || AllBlocks.WEIGHTED_EJECTOR.has(placedOnState);
    }

    @MethodsReturnNonnullByDefault
    private static class MatrixMixerHelper extends CogwheelBlockItem.DiagonalCogHelper {

        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return CreateIntegrationBlocks.MATRIX_MECHANICAL_MIXER::is;
        }

        @Override
        public PlacementOffset getOffset(Player player, Level world, BlockState state, BlockPos pos, BlockHitResult ray) {
            if (hitOnShaft(state, ray))
                return PlacementOffset.fail();

            if (ICogWheel.isLargeCog(state)) {
                Direction.Axis axis = ((IRotate) state.getBlock()).getRotationAxis(state);
                Direction side = IPlacementHelper.orderedByDistanceOnlyAxis(pos, ray.getLocation(), axis)
                        .get(0);
                List<Direction> directions = IPlacementHelper.orderedByDistanceExceptAxis(pos, ray.getLocation(), axis);
                for (Direction dir : directions) {
                    BlockPos newPos = pos.relative(dir)
                            .relative(side);

                    if (!CogWheelBlock.isValidCogwheelPosition(true, world, newPos, dir.getAxis()))
                        continue;

                    if (!world.getBlockState(newPos)
                            .canBeReplaced())
                        continue;

                    return PlacementOffset.success(newPos, s -> s.setValue(MatrixMechanicalMixerBlock.AXIS, Direction.Axis.Y));
                }

                return PlacementOffset.fail();
            }

            return super.getOffset(player, world, state, pos, ray);
        }
    }
}
