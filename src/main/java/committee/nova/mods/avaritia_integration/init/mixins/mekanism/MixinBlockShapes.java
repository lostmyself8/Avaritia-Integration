package committee.nova.mods.avaritia_integration.init.mixins.mekanism;

import committee.nova.mods.avaritia_integration.module.mekanism.common.content.blocktype.MekIntegrationFactoryTypes;
import mekanism.common.content.blocktype.BlockShapes;
import mekanism.common.content.blocktype.FactoryType;
import mekanism.common.tier.FactoryTier;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockShapes.class, remap = false)
public class MixinBlockShapes {

    @Shadow
    @Final
    public static VoxelShape[] ENRICHING_FACTORY;

    @Inject(method = "getShape", at = @At(value = "HEAD"), cancellable = true)
    private static void mixinGetShape(FactoryTier tier, FactoryType type, CallbackInfoReturnable<VoxelShape[]> cir) {
        if (type == MekIntegrationFactoryTypes.NEUTRON_COLLECTING) {
            cir.setReturnValue(ENRICHING_FACTORY);
        } else if (type == MekIntegrationFactoryTypes.NEUTRON_COMPRESSING) {
            cir.setReturnValue(ENRICHING_FACTORY);
        }
    }
}
