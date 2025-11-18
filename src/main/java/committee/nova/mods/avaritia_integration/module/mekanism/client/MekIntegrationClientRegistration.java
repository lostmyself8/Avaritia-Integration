package committee.nova.mods.avaritia_integration.module.mekanism.client;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.mekanism.common.registry.MekIntegrationBlocks;
import committee.nova.mods.avaritia_integration.module.mekanism.common.registry.MekIntegrationContainerTypes;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.TileEntityInfinityAdvancedSolarGenerator;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.TileEntityInfinitySolarGenerator;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.TileEntityNeutronAdvancedSolarGenerator;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.TileEntityNeutronSolarGenerator;
import mekanism.client.ClientRegistrationUtil;
import mekanism.client.model.baked.ExtensionBakedModel.TransformedBakedModel;
import mekanism.client.render.lib.QuadTransformation;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.generators.client.gui.GuiSolarGenerator;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegisterEvent;

import static mekanism.client.ClientRegistration.addCustomModel;

@Mod.EventBusSubscriber(modid = AvaritiaIntegration.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MekIntegrationClientRegistration {

    private MekIntegrationClientRegistration() {

    }

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        // adv solar gen requires to be translated up 1 block, so handle the model separately
        addCustomModel(MekIntegrationBlocks.NEUTRON_ADVANCED_SOLAR_GENERATOR, (orig, evt) -> new TransformedBakedModel<Void>(orig,
                QuadTransformation.translate(0, 1, 0)));
        addCustomModel(MekIntegrationBlocks.INFINITY_ADVANCED_SOLAR_GENERATOR, (orig, evt) -> new TransformedBakedModel<Void>(orig,
                QuadTransformation.translate(0, 1, 0)));
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void registerContainers(RegisterEvent event) {
        event.register(Registries.MENU, helper -> {
            ClientRegistrationUtil.registerScreen(MekIntegrationContainerTypes.NEUTRON_SOLAR_GENERATOR, (MekanismTileContainer<TileEntityNeutronSolarGenerator> container, Inventory inv, Component title) -> new GuiSolarGenerator<>(container, inv, title));
            ClientRegistrationUtil.registerScreen(MekIntegrationContainerTypes.INFINITY_SOLAR_GENERATOR, (MekanismTileContainer<TileEntityInfinitySolarGenerator> container, Inventory inv, Component title) -> new GuiSolarGenerator<>(container, inv, title));
            ClientRegistrationUtil.registerScreen(MekIntegrationContainerTypes.NEUTRON_ADVANCED_SOLAR_GENERATOR, (MekanismTileContainer<TileEntityNeutronAdvancedSolarGenerator> container, Inventory inv, Component title) -> new GuiSolarGenerator<>(container, inv, title));
            ClientRegistrationUtil.registerScreen(MekIntegrationContainerTypes.INFINITY_ADVANCED_SOLAR_GENERATOR, (MekanismTileContainer<TileEntityInfinityAdvancedSolarGenerator> container, Inventory inv, Component title) -> new GuiSolarGenerator<>(container, inv, title));
        });
    }
}
