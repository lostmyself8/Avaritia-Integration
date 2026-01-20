package committee.nova.mods.avaritia_integration.module.mekanism.common.registry;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.mekanism.common.inventory.container.tile.MekIntegrationFactoryContainer;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.factory.TileEntityMIFactory;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.machine.TileEntityNeutronCollector;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.machine.TileEntityNeutronCompressor;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.registration.impl.ContainerTypeDeferredRegister;
import mekanism.common.registration.impl.ContainerTypeRegistryObject;

public class MekIntegrationContainerTypes {

    private MekIntegrationContainerTypes() {
    }

    public static final ContainerTypeDeferredRegister CONTAINER_TYPES = new ContainerTypeDeferredRegister(AvaritiaIntegration.MOD_ID);

    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityNeutronCollector>> NEUTRON_COLLECTOR = CONTAINER_TYPES.register(MekIntegrationBlocks.NEUTRON_COLLECTOR, TileEntityNeutronCollector.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityNeutronCompressor>> NEUTRON_COMPRESSOR = CONTAINER_TYPES.register(MekIntegrationBlocks.NEUTRON_COMPRESSOR, TileEntityNeutronCompressor.class);

    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityMIFactory<?>>> FACTORY = CONTAINER_TYPES.register("factory", factoryClass(), MekIntegrationFactoryContainer::new);

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Class<TileEntityMIFactory<?>> factoryClass() {
        return (Class) TileEntityMIFactory.class;
    }
}
