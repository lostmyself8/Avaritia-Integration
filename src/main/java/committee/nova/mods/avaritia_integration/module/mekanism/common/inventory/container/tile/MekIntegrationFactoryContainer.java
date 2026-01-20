package committee.nova.mods.avaritia_integration.module.mekanism.common.inventory.container.tile;

import committee.nova.mods.avaritia_integration.module.mekanism.common.registry.MekIntegrationContainerTypes;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.factory.TileEntityGasToItemMIFactory;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.factory.TileEntityMIFactory;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.tier.FactoryTier;
import net.minecraft.world.entity.player.Inventory;

public class MekIntegrationFactoryContainer extends MekanismTileContainer<TileEntityMIFactory<?>> {

    public MekIntegrationFactoryContainer(int id, Inventory inv, TileEntityMIFactory<?> tile) {
        super(MekIntegrationContainerTypes.FACTORY, id, inv, tile);
    }

    @Override
    protected int getInventoryYOffset() {
        if (tile.hasSecondaryResourceBar()) {
            return 95;
        }
        if (tile instanceof TileEntityGasToItemMIFactory<?>) {
            return 98;
        }
        return 85;
    }

    @Override
    protected int getInventoryXOffset() {
        return tile.tier == FactoryTier.ULTIMATE ? 26 : 8;
    }
}
