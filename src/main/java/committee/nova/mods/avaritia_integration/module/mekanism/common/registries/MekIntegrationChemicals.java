package committee.nova.mods.avaritia_integration.module.mekanism.common.registries;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import mekanism.api.chemical.Chemical;
import mekanism.common.registration.impl.ChemicalDeferredRegister;
import mekanism.common.registration.impl.DeferredChemical;

public class MekIntegrationChemicals {

    private MekIntegrationChemicals() {}

    public static final ChemicalDeferredRegister CHEMICALS = new ChemicalDeferredRegister(AvaritiaIntegration.MOD_ID);

    public static final DeferredChemical<Chemical> INFINITY = CHEMICALS.register("infinity", AvaritiaIntegration.rl("infuse_type/infinity"), 0x5A4630);
    public static final DeferredChemical<Chemical> NEUTRON = CHEMICALS.register("neutron", AvaritiaIntegration.rl("infuse_type/neutron"), 0x5A4630);
}
