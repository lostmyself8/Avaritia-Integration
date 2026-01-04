package committee.nova.mods.avaritia_integration.init.mixins.mekanism;

import committee.nova.mods.avaritia_integration.module.mekanism.common.MekIntegrationLang;
import committee.nova.mods.avaritia_integration.module.mekanism.common.content.blocktype.MekIntegrationFactoryTypes;
import committee.nova.mods.avaritia_integration.module.mekanism.common.registry.MekIntegrationBlockTypes;
import committee.nova.mods.avaritia_integration.module.mekanism.common.registry.MekIntegrationBlocks;
import mekanism.common.MekanismLang;
import mekanism.common.content.blocktype.FactoryType;
import mekanism.common.content.blocktype.Machine.FactoryMachine;
import mekanism.common.registration.impl.BlockRegistryObject;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

@Mixin(value = FactoryType.class, remap = false)
public class MixinFactoryType {
    @Shadow(remap = false)
    @Final
    @Mutable
    private static FactoryType[] $VALUES;

    @Invoker("<init>")
    public static FactoryType mekIntegration$initInvoker(String internalName, int internalId, String registryNameComponent, MekanismLang langEntry, Supplier<FactoryMachine<?>> baseMachine, Supplier<BlockRegistryObject<?, ?>> baseBlock) {
        throw new AssertionError();
    }

    @Inject(method = {"<clinit>"}, at = @At("TAIL"))
    private static void mekIntegration$clinit(CallbackInfo ci) {
        MekIntegrationFactoryTypes.NEUTRON_COLLECTING = mekIntegration$addVariant("NEUTRON_COLLECTING", "neutron_collecting", MekIntegrationLang.NEUTRON_COLLECTING, () -> (FactoryMachine<?>) MekIntegrationBlockTypes.NEUTRON_COLLECTOR, () -> MekIntegrationBlocks.NEUTRON_COLLECTOR);
//        MekIntegrationFactoryTypes.NEUTRON_COMPRESSING = mekIntegration$addVariant("NEUTRON_COMPRESSING", "neutron_compressing", MekIntegrationLang.NEUTRON_COMPRESSING, () -> EMBlockTypes.ALLOYER, () -> EMBlocks.ALLOYER);
    }

    @Unique
    private static FactoryType mekIntegration$addVariant(String internalName, String registryNameComponent, MekanismLang langEntry, Supplier<FactoryMachine<?>> baseMachine, Supplier<BlockRegistryObject<?, ?>> baseBlock) {
        ArrayList<FactoryType> variants = new ArrayList<>(Arrays.asList($VALUES));
        FactoryType casing = mekIntegration$initInvoker(internalName, variants.get(variants.size() - 1).ordinal() + 1, registryNameComponent, langEntry, baseMachine, baseBlock);
        variants.add(casing);
        $VALUES = variants.toArray(new FactoryType[0]);
        return casing;
    }
}
