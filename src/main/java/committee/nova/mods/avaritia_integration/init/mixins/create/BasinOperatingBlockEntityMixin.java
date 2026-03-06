package committee.nova.mods.avaritia_integration.init.mixins.create;

import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import committee.nova.mods.avaritia_integration.init.mixins.create.accessor.BasinOperatingBlockEntityAccessor;
import committee.nova.mods.avaritia_integration.module.create.content.recipe.ExtremeBasinRecipe;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(BasinOperatingBlockEntity.class)
public class BasinOperatingBlockEntityMixin {
    @Inject(method = "matchBasinRecipe", at = @At("RETURN"), cancellable = true, remap = false)
    private void matchExtremeHeat(Recipe<?> recipe, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() || !(recipe instanceof ExtremeBasinRecipe)) {
            return;
        }

        Optional<BasinBlockEntity> basin = ((BasinOperatingBlockEntityAccessor) this).invokeGetBasin();
        cir.setReturnValue(!basin.isPresent() ? false : ExtremeBasinRecipe.match(basin.get(), recipe));
    }

    @Redirect(method = "applyBasinRecipe", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/processing/basin/BasinRecipe;apply(Lcom/simibubi/create/content/processing/basin/BasinBlockEntity;Lnet/minecraft/world/item/crafting/Recipe;)Z"), remap = false)
    private boolean redirectApply(BasinBlockEntity basin, Recipe<?> recipe) {
        if (recipe instanceof ExtremeBasinRecipe) {
            return ExtremeBasinRecipe.apply(basin, recipe);
        }

        return BasinRecipe.apply(basin, recipe);
    }
}
