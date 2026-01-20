package committee.nova.mods.avaritia_integration.module.mekanism.client;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.mekanism.client.gui.machine.GuiMIFactory;
import committee.nova.mods.avaritia_integration.module.mekanism.client.gui.machine.GuiNeutronCollector;
import committee.nova.mods.avaritia_integration.module.mekanism.client.gui.machine.GuiNeutronCompressor;
import committee.nova.mods.avaritia_integration.module.mekanism.common.registry.MekIntegrationContainerTypes;
import mekanism.client.ClientRegistrationUtil;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = AvaritiaIntegration.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MekIntegrationClientRegistration {

    private MekIntegrationClientRegistration() {

    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void registerContainers(RegisterEvent event) {
        event.register(Registries.MENU, helper -> {
            ClientRegistrationUtil.registerScreen(MekIntegrationContainerTypes.NEUTRON_COLLECTOR, GuiNeutronCollector::new);
            ClientRegistrationUtil.registerScreen(MekIntegrationContainerTypes.NEUTRON_COMPRESSOR, GuiNeutronCompressor::new);

            ClientRegistrationUtil.registerScreen(MekIntegrationContainerTypes.FACTORY, GuiMIFactory::new);
        });
    }
}
