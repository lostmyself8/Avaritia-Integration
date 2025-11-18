package committee.nova.mods.avaritia_integration.module.mekanism;

import committee.nova.mods.avaritia_integration.module.ModMeta;
import committee.nova.mods.avaritia_integration.module.Module;
import committee.nova.mods.avaritia_integration.module.ModuleEntry;
import committee.nova.mods.avaritia_integration.module.mekanism.common.registry.MekIntegrationBlocks;
import committee.nova.mods.avaritia_integration.module.mekanism.common.registry.MekIntegrationContainerTypes;
import committee.nova.mods.avaritia_integration.module.mekanism.common.registry.MekIntegrationItems;
import committee.nova.mods.avaritia_integration.module.mekanism.common.registry.MekIntegrationTileEntityTypes;
import mekanism.api.providers.IBlockProvider;
import mekanism.api.providers.IItemProvider;
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters;
import net.minecraft.world.item.CreativeModeTab.Output;
import net.minecraftforge.eventbus.api.IEventBus;

@ModuleEntry(id = MekanismModule.MOD_ID, target = @ModMeta(MekanismModule.MOD_ID))
public final class MekanismModule implements Module {
    public static final String MOD_ID = "mekanism";

    @Override
    public void init(IEventBus registryBus) {
        MekIntegrationItems.ITEMS.register(registryBus);
        MekIntegrationBlocks.BLOCKS.register(registryBus);
        MekIntegrationContainerTypes.CONTAINER_TYPES.register(registryBus);
        MekIntegrationTileEntityTypes.TILE_ENTITY_TYPES.register(registryBus);
    }

    @Override
    public void collectCreativeTabItems(ItemDisplayParameters parameters, Output output) {
        for (IItemProvider itemProvider : MekIntegrationItems.ITEMS.getAllItems()) {
            output.accept(itemProvider);
        }
        for (IBlockProvider blockProvider : MekIntegrationBlocks.BLOCKS.getAllBlocks()) {
            output.accept(blockProvider);
        }
    }
}
