package committee.nova.mods.avaritia_integration.module.mekanism.common.registry;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import committee.nova.mods.avaritia_integration.module.mekanism.common.MekIntegrationLang;
import committee.nova.mods.avaritia_integration.module.mekanism.common.content.blocktype.MekIntegrationFactory;
import committee.nova.mods.avaritia_integration.module.mekanism.common.content.blocktype.MekIntegrationFactory.MekIntegrationFactoryBuilder;
import committee.nova.mods.avaritia_integration.module.mekanism.common.content.blocktype.MekIntegrationFactoryMachine;
import committee.nova.mods.avaritia_integration.module.mekanism.common.content.blocktype.MekIntegrationFactoryMachine.MekIntegrationMachineBuilder;
import committee.nova.mods.avaritia_integration.module.mekanism.common.content.blocktype.MekIntegrationFactoryType;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.machine.TileEntityNeutronCollector;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.machine.TileEntityNeutronCompressor;
import committee.nova.mods.avaritia_integration.module.mekanism.common.util.MekIntegrationEnumUtils;
import mekanism.common.config.MekanismConfig;
import mekanism.common.registries.MekanismSounds;
import mekanism.common.tier.FactoryTier;
import mekanism.common.util.EnumUtils;

public class MekIntegrationBlockTypes {

    private MekIntegrationBlockTypes() {

    }

    private static final Table<FactoryTier, MekIntegrationFactoryType, MekIntegrationFactory<?>> FACTORIES = HashBasedTable.create();

    // Neutron Collector
    public static final MekIntegrationFactoryMachine<TileEntityNeutronCollector> NEUTRON_COLLECTOR = MekIntegrationMachineBuilder
            .createMekIntegrationFactoryMachine(() -> MekIntegrationTileEntityTypes.NEUTRON_COLLECTOR, MekIntegrationLang.DESCRIPTION_NEUTRON_COLLECTING, MekIntegrationFactoryType.NEUTRON_COLLECTING)
            .withGui(() -> MekIntegrationContainerTypes.NEUTRON_COLLECTOR)
            .withSound(MekanismSounds.CHEMICAL_CRYSTALLIZER)
            .withEnergyConfig(MekanismConfig.usage.chemicalCrystallizer, MekanismConfig.storage.chemicalCrystallizer)
            .withComputerSupport("neutronCollector")
            .build();

    // Neutron Compressor
    public static final MekIntegrationFactoryMachine<TileEntityNeutronCompressor> NEUTRON_COMPRESSOR = MekIntegrationMachineBuilder
            .createMekIntegrationFactoryMachine(() -> MekIntegrationTileEntityTypes.NEUTRON_COMPRESSOR, MekIntegrationLang.DESCRIPTION_NEUTRON_COLLECTING, MekIntegrationFactoryType.NEUTRON_COMPRESSING)
            .withGui(() -> MekIntegrationContainerTypes.NEUTRON_COMPRESSOR)
            .withSound(MekanismSounds.CHEMICAL_CRYSTALLIZER)
            .withEnergyConfig(MekanismConfig.usage.chemicalCrystallizer, MekanismConfig.storage.chemicalCrystallizer)
            .withComputerSupport("neutronCompressor")
            .build();

    static {
        for (FactoryTier tier : EnumUtils.FACTORY_TIERS) {
            for (MekIntegrationFactoryType type : MekIntegrationEnumUtils.FACTORY_TYPES) {
                FACTORIES.put(tier, type, MekIntegrationFactoryBuilder.createMekIntegrationFactory(() -> MekIntegrationTileEntityTypes.getFactoryTile(tier, type), type, tier).build());
            }
        }
    }

    public static MekIntegrationFactory<?> getFactory(FactoryTier tier, MekIntegrationFactoryType type) {
        return FACTORIES.get(tier, type);
    }
}
