package committee.nova.mods.avaritia_integration.init.mixins.create;

import com.simibubi.create.AllItems;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(targets = "com.simibubi.create.AllCreativeModeTabs$RegistrateDisplayItemsGenerator")
public class RegistrateDisplayItemsGeneratorMixin {
    @Inject(method = "makeExclusionPredicate", at = @At("RETURN"), cancellable = true, remap = false)
    private static void removeExclusionPredicate(CallbackInfoReturnable<Predicate<Item>> cir) {
        Predicate<Item> originalPredicate = cir.getReturnValue();

        Predicate<Item> modifiedPredicate = item -> {
            if (item == AllItems.SHADOW_STEEL.get() ||
                    item == AllItems.CHROMATIC_COMPOUND.get() ||
                    item == AllItems.REFINED_RADIANCE.get()) {
                return false;
            }
            return originalPredicate.test(item);
        };

        cir.setReturnValue(modifiedPredicate);
    }
}
