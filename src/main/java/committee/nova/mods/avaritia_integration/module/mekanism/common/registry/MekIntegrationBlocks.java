package committee.nova.mods.avaritia_integration.module.mekanism.common.registry;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.TileEntityInfinityAdvancedSolarGenerator;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.TileEntityInfinitySolarGenerator;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.TileEntityNeutronAdvancedSolarGenerator;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.TileEntityNeutronSolarGenerator;
import mekanism.common.block.prefab.BlockTile.BlockTileModel;
import mekanism.common.item.block.machine.ItemBlockMachine;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.generators.common.content.blocktype.Generator;
import net.minecraft.world.level.material.MapColor;

public class MekIntegrationBlocks {

    private MekIntegrationBlocks() {

    }

    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(AvaritiaIntegration.MOD_ID);

    public static final BlockRegistryObject<BlockTileModel<TileEntityInfinitySolarGenerator, Generator<TileEntityInfinitySolarGenerator>>, ItemBlockMachine> INFINITY_SOLAR_GENERATOR = BLOCKS.register("infinity_solar_generator", () -> new BlockTileModel<>(MekIntegrationBlockTypes.INFINITY_SOLAR_GENERATOR, properties -> properties.mapColor(MapColor.COLOR_BLUE)), ItemBlockMachine::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityNeutronSolarGenerator, Generator<TileEntityNeutronSolarGenerator>>, ItemBlockMachine> NEUTRON_SOLAR_GENERATOR = BLOCKS.register("neutron_solar_generator", () -> new BlockTileModel<>(MekIntegrationBlockTypes.NEUTRON_SOLAR_GENERATOR, properties -> properties.mapColor(MapColor.COLOR_BLUE)), ItemBlockMachine::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityInfinityAdvancedSolarGenerator, Generator<TileEntityInfinityAdvancedSolarGenerator>>, ItemBlockMachine> INFINITY_ADVANCED_SOLAR_GENERATOR = BLOCKS.register("infinity_advanced_solar_generator", () -> new BlockTileModel<>(MekIntegrationBlockTypes.INFINITY_ADVANCED_SOLAR_GENERATOR, properties -> properties.mapColor(MapColor.COLOR_BLUE)), ItemBlockMachine::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityNeutronAdvancedSolarGenerator, Generator<TileEntityNeutronAdvancedSolarGenerator>>, ItemBlockMachine> NEUTRON_ADVANCED_SOLAR_GENERATOR = BLOCKS.register("neutron_advanced_solar_generator", () -> new BlockTileModel<>(MekIntegrationBlockTypes.NEUTRON_ADVANCED_SOLAR_GENERATOR, properties -> properties.mapColor(MapColor.COLOR_BLUE)), ItemBlockMachine::new);
}
