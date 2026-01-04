package committee.nova.mods.avaritia_integration.module.mekanism.common.registry;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.machine.TileEntityNeutronCollector;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.machine.TileEntityNeutronCompressor;
import mekanism.common.block.prefab.BlockTile.BlockTileModel;
import mekanism.common.content.blocktype.Machine;
import mekanism.common.item.block.machine.ItemBlockMachine;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.resource.BlockResourceInfo;

public class MekIntegrationBlocks {

    private MekIntegrationBlocks() {

    }

    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(AvaritiaIntegration.MOD_ID);

    public static final BlockRegistryObject<BlockTileModel<TileEntityNeutronCollector, Machine<TileEntityNeutronCollector>>, ItemBlockMachine> NEUTRON_COLLECTOR = BLOCKS.register("neutron_collector", () -> new BlockTileModel<>(MekIntegrationBlockTypes.NEUTRON_COLLECTOR, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())), ItemBlockMachine::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityNeutronCompressor, Machine<TileEntityNeutronCompressor>>, ItemBlockMachine> NEUTRON_COMPRESSOR = BLOCKS.register("neutron_compressor", () -> new BlockTileModel<>(MekIntegrationBlockTypes.NEUTRON_COMPRESSOR, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())), ItemBlockMachine::new);
}
