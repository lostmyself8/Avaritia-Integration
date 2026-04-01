package committee.nova.mods.avaritia_integration.init.mixins.create.accessor;

import com.simibubi.create.content.kinetics.fan.AirCurrent;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AirCurrent.class)
public interface AirCurrentAccessor {
    @Invoker("enableClientPlayerSound")
    void invokeEnableClientPlayerSound(Entity e, float maxVolume);
}
