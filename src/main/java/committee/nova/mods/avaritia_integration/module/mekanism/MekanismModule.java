package committee.nova.mods.avaritia_integration.module.mekanism;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.ModMeta;
import committee.nova.mods.avaritia_integration.module.Module;
import committee.nova.mods.avaritia_integration.module.ModuleEntry;
import committee.nova.mods.avaritia_integration.module.mekanism.client.gui.machine.GuiMIFactory;
import committee.nova.mods.avaritia_integration.module.mekanism.client.gui.machine.GuiNeutronCollector;
import committee.nova.mods.avaritia_integration.module.mekanism.client.gui.machine.GuiSingularityCompressor;
import committee.nova.mods.avaritia_integration.module.mekanism.common.registry.*;
import mekanism.api.providers.IBlockProvider;
import mekanism.api.providers.IItemProvider;
import mekanism.client.ClientRegistrationUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters;
import net.minecraft.world.item.CreativeModeTab.Output;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegisterEvent;

@ModuleEntry(id = MekanismModule.MOD_ID, target = @ModMeta(MekanismModule.MOD_ID))
public final class MekanismModule implements Module {
    public static final String MOD_ID = "mekanism";

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(AvaritiaIntegration.MOD_ID, path);
    }

    @Override
    public void init(IEventBus registryBus) {
        MekIntegrationItems.ITEMS.register(registryBus);
        MekIntegrationBlocks.BLOCKS.register(registryBus);
        MekIntegrationContainerTypes.CONTAINER_TYPES.register(registryBus);
        MekIntegrationTileEntityTypes.TILE_ENTITY_TYPES.register(registryBus);
        MekIntegrationInfuseType.INFUSE_TYPES.register(registryBus);
        MekIntegrationRecipeSerializers.RECIPE_SERIALIZERS.register(registryBus);
    }

    @Override
    public void registerClientEvent(IEventBus modBus, IEventBus gameBus) {
        modBus.addListener(EventPriority.LOW, this::registerScreen);
    }

    private void registerScreen(RegisterEvent event) {
        event.register(Registries.MENU, helper -> {
            ClientRegistrationUtil.registerScreen(MekIntegrationContainerTypes.NEUTRON_COLLECTOR, GuiNeutronCollector::new);
            ClientRegistrationUtil.registerScreen(MekIntegrationContainerTypes.SINGULARITY_COMPRESSOR, GuiSingularityCompressor::new);
            ClientRegistrationUtil.registerScreen(MekIntegrationContainerTypes.FACTORY, GuiMIFactory::new);
        });
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
