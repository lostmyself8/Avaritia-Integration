package committee.nova.mods.avaritia_integration.module.mekanismgenerators.common.registry;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.mekanismgenerators.common.tile.TileEntityInfinityAdvancedSolarGenerator;
import committee.nova.mods.avaritia_integration.module.mekanismgenerators.common.tile.TileEntityInfinitySolarGenerator;
import committee.nova.mods.avaritia_integration.module.mekanismgenerators.common.tile.TileEntityNeutronAdvancedSolarGenerator;
import committee.nova.mods.avaritia_integration.module.mekanismgenerators.common.tile.TileEntityNeutronSolarGenerator;
import mekanism.common.registration.impl.TileEntityTypeDeferredRegister;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tile.base.TileEntityMekanism;

public class GenIntegrationTileEntityTypes {

    private GenIntegrationTileEntityTypes() {

    }
    public static final TileEntityTypeDeferredRegister TILE_ENTITY_TYPES = new TileEntityTypeDeferredRegister(AvaritiaIntegration.MOD_ID);

    public static final TileEntityTypeRegistryObject<TileEntityInfinitySolarGenerator> INFINITY_SOLAR_GENERATOR = TILE_ENTITY_TYPES.register(GenIntegrationBlocks.INFINITY_SOLAR_GENERATOR, TileEntityInfinitySolarGenerator::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
    public static final TileEntityTypeRegistryObject<TileEntityNeutronSolarGenerator> NEUTRON_SOLAR_GENERATOR = TILE_ENTITY_TYPES.register(GenIntegrationBlocks.NEUTRON_SOLAR_GENERATOR, TileEntityNeutronSolarGenerator::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
    public static final TileEntityTypeRegistryObject<TileEntityInfinityAdvancedSolarGenerator> INFINITY_ADVANCED_SOLAR_GENERATOR = TILE_ENTITY_TYPES.register(GenIntegrationBlocks.INFINITY_ADVANCED_SOLAR_GENERATOR, TileEntityInfinityAdvancedSolarGenerator::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
    public static final TileEntityTypeRegistryObject<TileEntityNeutronAdvancedSolarGenerator> NEUTRON_ADVANCED_SOLAR_GENERATOR = TILE_ENTITY_TYPES.register(GenIntegrationBlocks.NEUTRON_ADVANCED_SOLAR_GENERATOR, TileEntityNeutronAdvancedSolarGenerator::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
}
