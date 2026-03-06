package committee.nova.mods.avaritia_integration.module.mekanism.common.registry;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.mekanism.MekanismModule;
import mekanism.api.chemical.infuse.InfuseType;
import mekanism.common.registration.impl.InfuseTypeDeferredRegister;
import mekanism.common.registration.impl.InfuseTypeRegistryObject;

public class MekIntegrationInfuseType {

    private MekIntegrationInfuseType() {
    }

    public static final InfuseTypeDeferredRegister INFUSE_TYPES = new InfuseTypeDeferredRegister(AvaritiaIntegration.MOD_ID);

    public static final InfuseTypeRegistryObject<InfuseType> INFINITY = INFUSE_TYPES.register("infinity", MekanismModule.rl("infuse_type/infinity"), 0x5A4630);
    public static final InfuseTypeRegistryObject<InfuseType> NEUTRON = INFUSE_TYPES.register("neutron", MekanismModule.rl("infuse_type/neutron"), 0x5A4630);
}