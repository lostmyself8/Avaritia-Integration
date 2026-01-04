package committee.nova.mods.avaritia_integration.module.mekanismgenerators;

import committee.nova.mods.avaritia_integration.module.ModMeta;
import committee.nova.mods.avaritia_integration.module.Module;
import committee.nova.mods.avaritia_integration.module.ModuleEntry;
import committee.nova.mods.avaritia_integration.module.mekanismgenerators.common.registry.GenIntegrationBlocks;
import committee.nova.mods.avaritia_integration.module.mekanismgenerators.common.registry.GenIntegrationContainerTypes;
import committee.nova.mods.avaritia_integration.module.mekanismgenerators.common.registry.GenIntegrationTileEntityTypes;
import mekanism.api.providers.IBlockProvider;
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters;
import net.minecraft.world.item.CreativeModeTab.Output;
import net.minecraftforge.eventbus.api.IEventBus;

@ModuleEntry(id = MekanismGeneratorsModule.MOD_ID, target = @ModMeta(MekanismGeneratorsModule.MOD_ID))
public final class MekanismGeneratorsModule implements Module {
    public static final String MOD_ID = "mekanismgenerators";

    @Override
    public void init(IEventBus registryBus) {
        GenIntegrationBlocks.BLOCKS.register(registryBus);
        GenIntegrationContainerTypes.CONTAINER_TYPES.register(registryBus);
        GenIntegrationTileEntityTypes.TILE_ENTITY_TYPES.register(registryBus);
    }

    @Override
    public void collectCreativeTabItems(ItemDisplayParameters parameters, Output output) {
        for (IBlockProvider blockProvider : GenIntegrationBlocks.BLOCKS.getAllBlocks()) {
            output.accept(blockProvider);
        }
    }
}
