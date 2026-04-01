package committee.nova.mods.avaritia_integration.module.create.content.extreme_crusher;

import com.simibubi.create.AllDamageTypes;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;

import java.util.List;

public class ExtremeCrushingWheelBlockEntity extends KineticBlockEntity {
    public ExtremeCrushingWheelBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        setLazyTickRate(20);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        registerAwardables(behaviours, AllAdvancements.CRUSHING_WHEEL, AllAdvancements.CRUSHER_MAXED);
    }

    @Override
    public void onSpeedChanged(float prevSpeed) {
        super.onSpeedChanged(prevSpeed);
        fixControllers();
    }

    public void fixControllers() {
        for (Direction d : Iterate.directions)
            ((ExtremeCrushingWheelBlock) getBlockState().getBlock()).updateControllers(getBlockState(), getLevel(), getBlockPos(),
                    d);
    }

    @Override
    protected AABB createRenderBoundingBox() {
        return new AABB(worldPosition).inflate(1);
    }

    @Override
    public void lazyTick() {
        super.lazyTick();
        fixControllers();
    }

    // This increases the drops when dropCustomDeathLoot is called, and LootingEnchantFunctionMixin increases the drops
    // defined in the entity loot table
    public static void crushingIsFortunate(LootingLevelEvent event) {
        DamageSource damageSource = event.getDamageSource();
        if (damageSource == null || !damageSource.is(AllDamageTypes.CRUSH))
            return;
        event.setLootingLevel(2);
    }

    public static void handleCrushedMobDrops(LivingDropsEvent event) {
        DamageSource damageSource = event.getSource();
        if (damageSource == null || !damageSource.is(AllDamageTypes.CRUSH))
            return;
        Vec3 outSpeed = Vec3.ZERO;
        for (ItemEntity outputItem : event.getDrops()) {
            outputItem.setDeltaMovement(outSpeed);
        }
    }

}
