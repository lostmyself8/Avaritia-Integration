package committee.nova.mods.avaritia_integration.module.mekanism.common.registry;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import mekanism.common.capabilities.energy.BasicEnergyContainer;
import mekanism.common.config.MekanismConfig;
import mekanism.common.item.ItemEnergized;
import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registration.impl.ItemRegistryObject;
import net.minecraft.world.item.Item;

public class MekIntegrationItems {

    private MekIntegrationItems() {

    }

    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(AvaritiaIntegration.MOD_ID);

    public static final ItemRegistryObject<ItemEnergized> INFINITY_ENERGY_TABLET = ITEMS.register("infinity_energy_tablet", () -> new ItemEnergized(MekanismConfig.gear.tabletChargeRate, MekanismConfig.gear.tabletMaxEnergy, BasicEnergyContainer.alwaysTrue, BasicEnergyContainer.alwaysTrue, new Item.Properties()));
    public static final ItemRegistryObject<ItemEnergized> NEUTRON_ENERGY_TABLET = ITEMS.register("neutron_energy_tablet", () -> new ItemEnergized(MekanismConfig.gear.tabletChargeRate, MekanismConfig.gear.tabletMaxEnergy, BasicEnergyContainer.alwaysTrue, BasicEnergyContainer.alwaysTrue, new Item.Properties()));
    public static final ItemRegistryObject<Item> INFINITY_SOLAR_PANEL = ITEMS.register("infinity_solar_panel");
    public static final ItemRegistryObject<Item> NEUTRON_SOLAR_PANEL = ITEMS.register("neutron_solar_panel");
}
