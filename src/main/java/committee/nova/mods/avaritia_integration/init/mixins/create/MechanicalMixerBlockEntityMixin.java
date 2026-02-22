package committee.nova.mods.avaritia_integration.init.mixins.create;

import com.simibubi.create.content.kinetics.mixer.MechanicalMixerBlockEntity;
import committee.nova.mods.avaritia_integration.module.create.registry.CreateIntegrationRecipeTypes;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MechanicalMixerBlockEntity.class)
public class MechanicalMixerBlockEntityMixin {
    @Inject(method = "matchStaticFilters", at = @At("HEAD"), cancellable = true, remap = false)
    private void allowExtremeRecipes(Recipe<?> r, CallbackInfoReturnable<Boolean> cir) {
        if (r.getType() == CreateIntegrationRecipeTypes.EXTREME_MIXING.getType()) {
            cir.setReturnValue(true);
        }
    }
}
