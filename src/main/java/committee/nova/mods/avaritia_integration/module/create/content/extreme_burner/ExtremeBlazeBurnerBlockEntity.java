package committee.nova.mods.avaritia_integration.module.create.content.extreme_burner;

import com.mrh0.createaddition.network.IObserveTileEntity;
import com.mrh0.createaddition.network.ObservePacket;
import com.simibubi.create.AllItems;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.fluids.tank.FluidTankBlock;
import com.simibubi.create.content.processing.basin.BasinBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import committee.nova.mods.avaritia.init.registry.ModItems;
import committee.nova.mods.avaritia_integration.module.create.compat.CompatInfo;
import committee.nova.mods.avaritia_integration.module.create.compat.cca.CCALiquidBlazeBurnerCompat;
import committee.nova.mods.avaritia_integration.module.create.compat.cca.ICCABurnerCompat;
import committee.nova.mods.avaritia_integration.module.create.compat.cca.CCACompatHelper;
import committee.nova.mods.avaritia_integration.module.create.compat.cca.liquid_burning.LiquidBurningRecipe;
import committee.nova.mods.avaritia_integration.module.create.registry.CreateIntegrationItems;
import committee.nova.mods.avaritia_integration.module.create.registry.CreateIntegrationTags;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.createmod.catnip.animation.LerpedFloat;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class ExtremeBlazeBurnerBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation {
    public static final int MAX_HEAT_CAPACITY = Integer.MAX_VALUE / 2;

    public LerpedFloat headAnimation;
    public boolean stockKeeper;
    public boolean isCreative;
    public boolean goggles;
    public boolean hat;
    public boolean hasStraw;

    protected FuelType activeFuel;
    protected int remainingBurnTime;
    protected LerpedFloat headAngle;

    protected ICCABurnerCompat fluidInventory = null;

    public Fluid lastFluid = null;

    public boolean firstTick = true;

    public ExtremeBlazeBurnerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        activeFuel = FuelType.NONE;
        remainingBurnTime = 0;
        headAnimation = LerpedFloat.linear();
        headAngle = LerpedFloat.angular();
        isCreative = false;
        goggles = false;
        stockKeeper = false;
        hasStraw = false;

        headAngle.startWithValue((AngleHelper.horizontalAngle(state.getOptionalValue(ExtremeBlazeBurnerBlock.FACING)
                .orElse(Direction.SOUTH)) + 180) % 360);
    }

    public FuelType getActiveFuel() {
        return activeFuel;
    }

    public int getRemainingBurnTime() {
        return remainingBurnTime;
    }

    public boolean isCreative() {
        return isCreative;
    }

    @Override
    public void tick() {
        super.tick();

        if (level.isClientSide) {
            if (shouldTickAnimation()) tickAnimation();
            if (!isVirtual()) spawnParticles(getExtremeHeatLevelFromBlock(), 1);
            return;
        }

        fluidBurningTick();

        if (isCreative) return;

        if (remainingBurnTime > 0) remainingBurnTime--;

        if (activeFuel == FuelType.BLAZE) updateBlockState();
        if (remainingBurnTime > 0) return;

        if (activeFuel == FuelType.STAR) {
            activeFuel = FuelType.BLAZE;
            remainingBurnTime = 5000;
        } else {
            activeFuel = FuelType.NONE;
        }

        updateBlockState();
    }

    @Override
    public void lazyTick() {
        super.lazyTick();
        stockKeeper = BlazeBurnerBlockEntity.getStockTicker(level, worldPosition) != null;
    }

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.FLUID_HANDLER && fluidInventory != null)
            return LazyOptional.of(() -> fluidInventory.getFluidInventory()).cast();
        return super.getCapability(cap, side);
    }

    public void fluidBurningTick() {
        if (level.isClientSide())
            return;

        if (!(fluidInventory instanceof CCALiquidBlazeBurnerCompat inv)) return;

        if (firstTick)
            inv.update(inv.getFluidInventory().getFluid());
        firstTick = false;


        if (inv.getRecipeCache().isEmpty()) return;
        LiquidBurningRecipe recipe = inv.getRecipeCache().get();
        if (recipe == null) return;

        if (remainingBurnTime > 6000) return;

        int requiredAmount = recipe.getFluidIngredient().getRequiredAmount();
        if (requiredAmount <= 0) return;

        FluidStack simDrained = inv.getFluidInventory().drain(requiredAmount, IFluidHandler.FluidAction.SIMULATE);

        if (!simDrained.isEmpty() && simDrained.getAmount() >= requiredAmount) {
            FluidStack actualDrained = inv.getFluidInventory().drain(requiredAmount, IFluidHandler.FluidAction.EXECUTE);

            this.activeFuel = recipe.isStarheated() ? FuelType.STAR : FuelType.BLAZE;

            float addedTime = ((float) recipe.getBurnTime() / requiredAmount) * actualDrained.getAmount();
            this.remainingBurnTime += addedTime;
        }

        ExtremeBlazeBurnerBlock.ExtremeHeatLevel prev = getExtremeHeatLevelFromBlock();
        playSound();
        updateBlockState();

        if (prev != getExtremeHeatLevelFromBlock()) {
            level.playSound(null, worldPosition, SoundEvents.BLAZE_AMBIENT, SoundSource.BLOCKS,
                    .125f + level.random.nextFloat() * .125f, 1.15f - level.random.nextFloat() * .25f);

            spawnParticleBurst(activeFuel == FuelType.STAR);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private boolean shouldTickAnimation() {
        return !VisualizationManager.supportsVisualization(level);
    }

    @OnlyIn(Dist.CLIENT)
    void tickAnimation() {
        boolean active = getExtremeHeatLevelFromBlock().isAtLeast(ExtremeBlazeBurnerBlock.ExtremeHeatLevel.FADING) && isValidBlockAbove();

        if (!active) {
            float target = 0;
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null && !player.isInvisible()) {
                double x;
                double z;
                if (isVirtual()) {
                    x = -4;
                    z = -10;
                } else {
                    x = player.getX();
                    z = player.getZ();
                }
                double dx = x - (getBlockPos().getX() + 0.5);
                double dz = z - (getBlockPos().getZ() + 0.5);
                target = AngleHelper.deg(-Mth.atan2(dz, dx)) - 90;
            }
            target = headAngle.getValue() + AngleHelper.getShortestAngleDiff(headAngle.getValue(), target);
            headAngle.chase(target, .25f, LerpedFloat.Chaser.exp(5));
            headAngle.tickChaser();
        } else {
            headAngle.chase((AngleHelper.horizontalAngle(getBlockState().getOptionalValue(ExtremeBlazeBurnerBlock.FACING)
                    .orElse(Direction.SOUTH)) + 180) % 360, .125f, LerpedFloat.Chaser.EXP);
            headAngle.tickChaser();
        }

        headAnimation.chase(active ? 1 : 0, .25f, LerpedFloat.Chaser.exp(.25f));
        headAnimation.tickChaser();
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {}

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        if (!isCreative) {
            compound.putInt("fuelLevel", activeFuel.ordinal());
            compound.putInt("burnTimeRemaining", remainingBurnTime);
        } else
            compound.putBoolean("isCreative", true);
        if (goggles)
            compound.putBoolean("Goggles", true);
        if (hat)
            compound.putBoolean("TrainHat", true);
        if (CompatInfo.isCCALoaded() && hasStraw)
            compound.putBoolean("Straw", true);
        super.write(compound, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        activeFuel = FuelType.values()[compound.getInt("fuelLevel")];
        remainingBurnTime = compound.getInt("burnTimeRemaining");
        isCreative = compound.getBoolean("isCreative");
        goggles = compound.contains("Goggles");
        hat = compound.contains("TrainHat");
        hasStraw = compound.getBoolean("Straw");
        updateFluidInventory();
        super.read(compound, clientPacket);
    }

    public void updateFluidInventory() {
        if (CompatInfo.isCCALoaded() && hasStraw) {
            if (fluidInventory == null) {
                fluidInventory = new CCALiquidBlazeBurnerCompat(this);
            }
        } else {
            fluidInventory = null;
            hasStraw = false;
        }

        if (this.level != null) {
            level.updateNeighborsAt(worldPosition, this.getBlockState().getBlock());
        }
    }

    public ExtremeBlazeBurnerBlock.ExtremeHeatLevel getExtremeHeatLevelFromBlock() {
        return ExtremeBlazeBurnerBlock.getExtremeHeatLevelOf(getBlockState());
    }

    public BlazeBurnerBlock.HeatLevel getHeatLevelFromBlock() {
        return BlazeBurnerBlock.getHeatLevelOf(getBlockState());
    }

    public ExtremeBlazeBurnerBlock.ExtremeHeatLevel getHeatLevelForRender() {
        ExtremeBlazeBurnerBlock.ExtremeHeatLevel heatLevel = getExtremeHeatLevelFromBlock();
        if (!heatLevel.isAtLeast(ExtremeBlazeBurnerBlock.ExtremeHeatLevel.FADING) && stockKeeper) return ExtremeBlazeBurnerBlock.ExtremeHeatLevel.FADING;
        return heatLevel;
    }

    public void updateBlockState() {
        setExtremeBlockHeat(getExtremeHeatLevel());
        setBlockHeat(getHeatLevel());
    }

    protected void setExtremeBlockHeat(ExtremeBlazeBurnerBlock.ExtremeHeatLevel heat) {
        ExtremeBlazeBurnerBlock.ExtremeHeatLevel inBlockState = getExtremeHeatLevelFromBlock();
        if (inBlockState == heat)
            return;
        level.setBlockAndUpdate(worldPosition, getBlockState().setValue(ExtremeBlazeBurnerBlock.EXTREME_HEAT_LEVEL, heat));
        notifyUpdate();
    }

    protected void setBlockHeat(BlazeBurnerBlock.HeatLevel heat) {
        BlazeBurnerBlock.HeatLevel inBlockState = getHeatLevelFromBlock();
        if (inBlockState == heat)
            return;
        level.setBlockAndUpdate(worldPosition, getBlockState().setValue(BlazeBurnerBlock.HEAT_LEVEL, heat));
        notifyUpdate();
    }

    protected FluidActionResult tryUpdateLiquid(ItemStack itemStack, @Nullable Player player, boolean simulate) {
        if (!(fluidInventory instanceof CCALiquidBlazeBurnerCompat inv)) return FluidActionResult.FAILURE;

        FluidActionResult result = FluidUtil.tryEmptyContainerAndStow(itemStack, inv.getFluidInventory(), null, 1000, player, !simulate);

        if(result.isSuccess() && !simulate) {
            level.playSound(null, getBlockPos(), SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, .125f + level.random.nextFloat() * .125f, .75f - level.random.nextFloat() * .25f);
        }
        return result;
    }

    protected boolean tryUpdateFuel(ItemStack itemStack, boolean forceOverflow, boolean simulate) {
        if (isCreative) return false;

        FuelType newFuel = FuelType.NONE;
        int newBurnTime = 0;

        //TODO 是否需要标签驱动?
        if (CreateIntegrationItems.BLAZE_BLAZE_CAKE.isIn(itemStack)) {
            newBurnTime = 36000;
            newFuel = FuelType.BLAZE;
        } else if (itemStack.getItem() == ModItems.blaze_cube.get()) {
            newBurnTime = 6000;
            newFuel = FuelType.BLAZE;
        } else if (CreateIntegrationTags.ItemTags.BLAZE_BURNER_FUEL_BLAZE.matches(itemStack)) {
            newBurnTime = 6000;
            newFuel = FuelType.BLAZE;
        } else if (CreateIntegrationItems.STAR_BLAZE_CAKE.isIn(itemStack)) {
            newBurnTime = 36000;
            newFuel = FuelType.STAR;
        } else if (itemStack.getItem() == ModItems.star_fuel.get()) {
            newBurnTime = 6000;
            newFuel = FuelType.STAR;
        } else if (CreateIntegrationTags.ItemTags.BLAZE_BURNER_FUEL_STAR.matches(itemStack)) {
            newBurnTime = 6000;
            newFuel = FuelType.STAR;
        }

        if (newFuel == FuelType.NONE) return false;
        if (newFuel.ordinal() < activeFuel.ordinal()) return false;

        if (newFuel == activeFuel) {
            if (remainingBurnTime + newBurnTime > MAX_HEAT_CAPACITY && !forceOverflow) return false;
            newBurnTime = Mth.clamp(remainingBurnTime + newBurnTime, 0, MAX_HEAT_CAPACITY);
        }

        if (simulate) return true;

        activeFuel = newFuel;
        remainingBurnTime = newBurnTime;

        if (level.isClientSide) {
            spawnParticleBurst(activeFuel == FuelType.STAR);
            return true;
        }

        ExtremeBlazeBurnerBlock.ExtremeHeatLevel prev = getExtremeHeatLevelFromBlock();
        playSound();
        updateBlockState();

        if (prev != getExtremeHeatLevelFromBlock()) {
            level.playSound(null, worldPosition, SoundEvents.BLAZE_AMBIENT, SoundSource.BLOCKS,
                    .125f + level.random.nextFloat() * .125f, 1.15f - level.random.nextFloat() * .25f);
        }

        return true;
    }

    protected void applyCreativeFuel() {
        activeFuel = FuelType.NONE;
        remainingBurnTime = 0;
        isCreative = true;

        ExtremeBlazeBurnerBlock.ExtremeHeatLevel next = getExtremeHeatLevelFromBlock().nextActiveLevel();

        if (level.isClientSide) {
            spawnParticleBurst(next.isAtLeast(ExtremeBlazeBurnerBlock.ExtremeHeatLevel.STAR));
            return;
        }

        playSound();
        if (next == ExtremeBlazeBurnerBlock.ExtremeHeatLevel.FADING)
            next = next.nextActiveLevel();
        setExtremeBlockHeat(next);
    }

    public boolean isCreativeFuel(ItemStack stack) {
        return AllItems.CREATIVE_BLAZE_CAKE.isIn(stack);
    }

    public boolean isValidBlockAbove() {
        if (isVirtual())
            return false;
        BlockState blockState = level.getBlockState(worldPosition.above());
        return BasinBlock.isBasin(level, worldPosition.above()) || blockState.getBlock() instanceof FluidTankBlock;
    }

    protected void playSound() {
        level.playSound(null, worldPosition, SoundEvents.BLAZE_SHOOT, SoundSource.BLOCKS,
                .125f + level.random.nextFloat() * .125f, .75f - level.random.nextFloat() * .25f);
    }

    protected ExtremeBlazeBurnerBlock.ExtremeHeatLevel getExtremeHeatLevel() {
        ExtremeBlazeBurnerBlock.ExtremeHeatLevel level = ExtremeBlazeBurnerBlock.ExtremeHeatLevel.SMOULDERING;
        switch (activeFuel) {
            case STAR:
                level = ExtremeBlazeBurnerBlock.ExtremeHeatLevel.STAR;
                break;
            case BLAZE:
                boolean lowPercent = (double) remainingBurnTime / BlazeBurnerBlockEntity.MAX_HEAT_CAPACITY < 0.0125;
                level = lowPercent ? ExtremeBlazeBurnerBlock.ExtremeHeatLevel.FADING : ExtremeBlazeBurnerBlock.ExtremeHeatLevel.BLAZE;
                break;
            default:
                break;
        }

        return level;
    }

    protected BlazeBurnerBlock.HeatLevel getHeatLevel() {
        BlazeBurnerBlock.HeatLevel level = BlazeBurnerBlock.HeatLevel.KINDLED;
        if (activeFuel == FuelType.STAR || activeFuel == FuelType.BLAZE) {
            level = BlazeBurnerBlock.HeatLevel.SEETHING;
        }

        return level;
    }

    protected void spawnParticles(ExtremeBlazeBurnerBlock.ExtremeHeatLevel heatLevel, double burstMult) {
        if (level == null)
            return;

        RandomSource r = level.getRandom();

        Vec3 c = VecHelper.getCenterOf(worldPosition);
        Vec3 v = c.add(VecHelper.offsetRandomly(Vec3.ZERO, r, .125f)
                .multiply(1, 0, 1));

        if (r.nextInt(4) != 0)
            return;

        boolean empty = level.getBlockState(worldPosition.above())
                .getCollisionShape(level, worldPosition.above())
                .isEmpty();

        if (empty || r.nextInt(8) == 0)
            level.addParticle(ParticleTypes.LARGE_SMOKE, v.x, v.y, v.z, 0, 0, 0);

        double yMotion = empty ? .0625f : r.nextDouble() * .0125f;
        Vec3 v2 = c.add(VecHelper.offsetRandomly(Vec3.ZERO, r, .5f)
                        .multiply(1, .25f, 1)
                        .normalize()
                        .scale((empty ? .25f : .5) + r.nextDouble() * .125f))
                .add(0, .5, 0);

        if (heatLevel.isAtLeast(ExtremeBlazeBurnerBlock.ExtremeHeatLevel.STAR)) {
            level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, v2.x, v2.y, v2.z, 0, yMotion, 0);
        } else if (heatLevel.isAtLeast(ExtremeBlazeBurnerBlock.ExtremeHeatLevel.FADING)) {
            level.addParticle(ParticleTypes.FLAME, v2.x, v2.y, v2.z, 0, yMotion, 0);
        }
    }

    public void spawnParticleBurst(boolean soulFlame) {
        Vec3 c = VecHelper.getCenterOf(worldPosition);
        RandomSource r = level.random;
        for (int i = 0; i < 20; i++) {
            Vec3 offset = VecHelper.offsetRandomly(Vec3.ZERO, r, .5f)
                    .multiply(1, .25f, 1)
                    .normalize();
            Vec3 v = c.add(offset.scale(.5 + r.nextDouble() * .125f))
                    .add(0, .125, 0);
            Vec3 m = offset.scale(1 / 32f);

            level.addParticle(soulFlame ? ParticleTypes.SOUL_FIRE_FLAME : ParticleTypes.FLAME, v.x, v.y, v.z, m.x, m.y,
                    m.z);
        }
    }

    public enum FuelType {
        NONE, BLAZE, STAR;
    }

    //没有在服务器上试验过
    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        if (CompatInfo.isCCALoaded() && hasStraw) {
            CCACompatHelper.sendObservePacket(worldPosition, 0);
            return containedFluidTooltip(tooltip, isPlayerSneaking, this.getCapability(ForgeCapabilities.FLUID_HANDLER));
        }

        return IHaveGoggleInformation.super.addToGoggleTooltip(tooltip, isPlayerSneaking);
    }
}
