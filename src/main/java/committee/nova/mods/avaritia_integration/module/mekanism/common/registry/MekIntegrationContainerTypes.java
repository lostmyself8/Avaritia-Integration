package committee.nova.mods.avaritia_integration.module.mekanism.common.registry;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.TileEntityInfinityAdvancedSolarGenerator;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.TileEntityInfinitySolarGenerator;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.TileEntityNeutronAdvancedSolarGenerator;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.TileEntityNeutronSolarGenerator;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.registration.impl.ContainerTypeDeferredRegister;
import mekanism.common.registration.impl.ContainerTypeRegistryObject;

public class MekIntegrationContainerTypes {

    private MekIntegrationContainerTypes() {
    }

    public static final ContainerTypeDeferredRegister CONTAINER_TYPES = new ContainerTypeDeferredRegister(AvaritiaIntegration.MOD_ID);

    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityInfinitySolarGenerator>> INFINITY_SOLAR_GENERATOR = CONTAINER_TYPES.register("infinity_solar_generator", TileEntityInfinitySolarGenerator.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityNeutronSolarGenerator>> NEUTRON_SOLAR_GENERATOR = CONTAINER_TYPES.register("neutron_solar_generator", TileEntityNeutronSolarGenerator.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityInfinityAdvancedSolarGenerator>> INFINITY_ADVANCED_SOLAR_GENERATOR = CONTAINER_TYPES.register("infinity_advanced_solar_generator", TileEntityInfinityAdvancedSolarGenerator.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityNeutronAdvancedSolarGenerator>> NEUTRON_ADVANCED_SOLAR_GENERATOR = CONTAINER_TYPES.register("neutron_advanced_solar_generator", TileEntityNeutronAdvancedSolarGenerator.class);
}
