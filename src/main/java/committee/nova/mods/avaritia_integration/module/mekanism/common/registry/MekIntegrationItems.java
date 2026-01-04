package committee.nova.mods.avaritia_integration.module.mekanism.common.registry;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import mekanism.api.math.FloatingLong;
import mekanism.common.capabilities.energy.BasicEnergyContainer;
import mekanism.common.item.ItemEnergized;
import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registration.impl.ItemRegistryObject;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class MekIntegrationItems {

    private MekIntegrationItems() {

    }

    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(AvaritiaIntegration.MOD_ID);

    public static final ItemRegistryObject<ItemEnergized> INFINITY_ENERGY_TABLET = ITEMS.register("infinity_energy_tablet", () -> new ItemEnergized(() -> FloatingLong.create(Integer.MAX_VALUE), () -> FloatingLong.create(Integer.MAX_VALUE), BasicEnergyContainer.alwaysTrue, BasicEnergyContainer.alwaysTrue, new Item.Properties().rarity(Rarity.EPIC)));
    public static final ItemRegistryObject<ItemEnergized> NEUTRON_ENERGY_TABLET = ITEMS.register("neutron_energy_tablet", () -> new ItemEnergized(() -> FloatingLong.create(Integer.MAX_VALUE), () -> FloatingLong.create(Integer.MAX_VALUE), BasicEnergyContainer.alwaysTrue, BasicEnergyContainer.alwaysTrue, new Item.Properties().rarity(Rarity.RARE)));
    public static final ItemRegistryObject<Item> INFINITY_SOLAR_PANEL = ITEMS.register("infinity_solar_panel");
    public static final ItemRegistryObject<Item> NEUTRON_SOLAR_PANEL = ITEMS.register("neutron_solar_panel");
}
