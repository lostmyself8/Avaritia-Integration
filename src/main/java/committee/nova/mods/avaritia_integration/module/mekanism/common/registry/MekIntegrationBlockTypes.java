package committee.nova.mods.avaritia_integration.module.mekanism.common.registry;

import committee.nova.mods.avaritia_integration.module.mekanism.common.MekIntegrationLang;
import committee.nova.mods.avaritia_integration.module.mekanism.common.content.blocktype.MekIntegrationFactoryTypes;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.machine.TileEntityNeutronCollector;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.machine.TileEntityNeutronCompressor;
import mekanism.common.config.MekanismConfig;
import mekanism.common.content.blocktype.Machine.FactoryMachine;
import mekanism.common.content.blocktype.Machine.MachineBuilder;
import mekanism.common.registries.MekanismSounds;

public class MekIntegrationBlockTypes {
    private MekIntegrationBlockTypes() {

    }

    // Neutron Collector
    public static final FactoryMachine<TileEntityNeutronCollector> NEUTRON_COLLECTOR = MachineBuilder
            .createFactoryMachine(() -> MekIntegrationTileEntityTypes.NEUTRON_COLLECTOR, MekIntegrationLang.DESCRIPTION_NEUTRON_COLLECTING, MekIntegrationFactoryTypes.NEUTRON_COLLECTING)
            .withGui(() -> MekIntegrationContainerTypes.NEUTRON_COLLECTOR)
            .withSound(MekanismSounds.CHEMICAL_CRYSTALLIZER)
            .withEnergyConfig(MekanismConfig.usage.chemicalCrystallizer, MekanismConfig.storage.chemicalCrystallizer)
            .withComputerSupport("neutronCollector")
            .build();

    // Neutron Compressor
    public static final FactoryMachine<TileEntityNeutronCompressor> NEUTRON_COMPRESSOR = MachineBuilder
            .createFactoryMachine(() -> MekIntegrationTileEntityTypes.NEUTRON_COMPRESSOR, MekIntegrationLang.DESCRIPTION_NEUTRON_COLLECTING, MekIntegrationFactoryTypes.NEUTRON_COLLECTING)
            .withGui(() -> MekIntegrationContainerTypes.NEUTRON_COMPRESSOR)
            .withSound(MekanismSounds.CHEMICAL_CRYSTALLIZER)
            .withEnergyConfig(MekanismConfig.usage.chemicalCrystallizer, MekanismConfig.storage.chemicalCrystallizer)
            .withComputerSupport("neutronCompressor")
            .build();
}
