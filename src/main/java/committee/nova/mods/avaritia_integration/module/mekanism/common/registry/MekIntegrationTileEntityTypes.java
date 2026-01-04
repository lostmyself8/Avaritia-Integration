package committee.nova.mods.avaritia_integration.module.mekanism.common.registry;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.machine.TileEntityNeutronCollector;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.machine.TileEntityNeutronCompressor;
import mekanism.common.registration.impl.TileEntityTypeDeferredRegister;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tile.base.TileEntityMekanism;

public class MekIntegrationTileEntityTypes {

    private MekIntegrationTileEntityTypes() {

    }
    public static final TileEntityTypeDeferredRegister TILE_ENTITY_TYPES = new TileEntityTypeDeferredRegister(AvaritiaIntegration.MOD_ID);

    public static final TileEntityTypeRegistryObject<TileEntityNeutronCollector> NEUTRON_COLLECTOR = TILE_ENTITY_TYPES.register(MekIntegrationBlocks.NEUTRON_COLLECTOR, TileEntityNeutronCollector::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
    public static final TileEntityTypeRegistryObject<TileEntityNeutronCompressor> NEUTRON_COMPRESSOR = TILE_ENTITY_TYPES.register(MekIntegrationBlocks.NEUTRON_COMPRESSOR, TileEntityNeutronCompressor::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
}
