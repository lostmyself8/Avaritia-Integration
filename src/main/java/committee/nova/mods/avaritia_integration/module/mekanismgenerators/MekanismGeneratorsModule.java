package committee.nova.mods.avaritia_integration.module.mekanismgenerators;

import committee.nova.mods.avaritia_integration.module.ModMeta;
import committee.nova.mods.avaritia_integration.module.Module;
import committee.nova.mods.avaritia_integration.module.ModuleEntry;
import committee.nova.mods.avaritia_integration.module.mekanismgenerators.common.registry.GenIntegrationBlocks;
import committee.nova.mods.avaritia_integration.module.mekanismgenerators.common.registry.GenIntegrationContainerTypes;
import committee.nova.mods.avaritia_integration.module.mekanismgenerators.common.registry.GenIntegrationItems;
import committee.nova.mods.avaritia_integration.module.mekanismgenerators.common.registry.GenIntegrationTileEntityTypes;
import committee.nova.mods.avaritia_integration.module.mekanismgenerators.common.tile.TileEntityInfinityAdvancedSolarGenerator;
import committee.nova.mods.avaritia_integration.module.mekanismgenerators.common.tile.TileEntityInfinitySolarGenerator;
import committee.nova.mods.avaritia_integration.module.mekanismgenerators.common.tile.TileEntityNeutronAdvancedSolarGenerator;
import committee.nova.mods.avaritia_integration.module.mekanismgenerators.common.tile.TileEntityNeutronSolarGenerator;
import mekanism.api.providers.IBlockProvider;
import mekanism.api.providers.IItemProvider;
import mekanism.client.ClientRegistrationUtil;
import mekanism.client.model.baked.ExtensionBakedModel;
import mekanism.client.render.lib.QuadTransformation;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.generators.client.gui.GuiSolarGenerator;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters;
import net.minecraft.world.item.CreativeModeTab.Output;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegisterEvent;

import static mekanism.client.ClientRegistration.addCustomModel;

@ModuleEntry(id = MekanismGeneratorsModule.MOD_ID, target = @ModMeta(MekanismGeneratorsModule.MOD_ID))
public final class MekanismGeneratorsModule implements Module {
    public static final String MOD_ID = "mekanismgenerators";

    @Override
    public void init(IEventBus registryBus) {
        GenIntegrationItems.ITEMS.register(registryBus);
        GenIntegrationBlocks.BLOCKS.register(registryBus);
        GenIntegrationContainerTypes.CONTAINER_TYPES.register(registryBus);
        GenIntegrationTileEntityTypes.TILE_ENTITY_TYPES.register(registryBus);
    }

    @Override
    public void registerClientEvent(IEventBus modBus, IEventBus gameBus) {
        modBus.addListener(this::clientSetupEvent);
        modBus.addListener(EventPriority.LOW, this::registerScreen);
    }

    private void clientSetupEvent(FMLClientSetupEvent event) {
        addCustomModel(GenIntegrationBlocks.NEUTRON_ADVANCED_SOLAR_GENERATOR, (orig, evt) -> new ExtensionBakedModel.TransformedBakedModel<Void>(orig,
                QuadTransformation.translate(0, 1, 0)));
        addCustomModel(GenIntegrationBlocks.INFINITY_ADVANCED_SOLAR_GENERATOR, (orig, evt) -> new ExtensionBakedModel.TransformedBakedModel<Void>(orig,
                QuadTransformation.translate(0, 1, 0)));
    }

    private void registerScreen(RegisterEvent event) {
        event.register(Registries.MENU, helper -> {
            ClientRegistrationUtil.registerScreen(GenIntegrationContainerTypes.NEUTRON_SOLAR_GENERATOR, (MekanismTileContainer<TileEntityNeutronSolarGenerator> container, Inventory inv, Component title) -> new GuiSolarGenerator<>(container, inv, title));
            ClientRegistrationUtil.registerScreen(GenIntegrationContainerTypes.INFINITY_SOLAR_GENERATOR, (MekanismTileContainer<TileEntityInfinitySolarGenerator> container, Inventory inv, Component title) -> new GuiSolarGenerator<>(container, inv, title));
            ClientRegistrationUtil.registerScreen(GenIntegrationContainerTypes.NEUTRON_ADVANCED_SOLAR_GENERATOR, (MekanismTileContainer<TileEntityNeutronAdvancedSolarGenerator> container, Inventory inv, Component title) -> new GuiSolarGenerator<>(container, inv, title));
            ClientRegistrationUtil.registerScreen(GenIntegrationContainerTypes.INFINITY_ADVANCED_SOLAR_GENERATOR, (MekanismTileContainer<TileEntityInfinityAdvancedSolarGenerator> container, Inventory inv, Component title) -> new GuiSolarGenerator<>(container, inv, title));
        });
    }

    @Override
    public void collectCreativeTabItems(ItemDisplayParameters parameters, Output output) {
        for (IItemProvider blockProvider : GenIntegrationItems.ITEMS.getAllItems()) {
            output.accept(blockProvider);
        }
        for (IBlockProvider blockProvider : GenIntegrationBlocks.BLOCKS.getAllBlocks()) {
            output.accept(blockProvider);
        }
    }
}
