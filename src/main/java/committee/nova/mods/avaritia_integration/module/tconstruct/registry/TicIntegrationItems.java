package committee.nova.mods.avaritia_integration.module.tconstruct.registry;

import committee.nova.mods.avaritia.init.registry.ModRarities;
import committee.nova.mods.avaritia_integration.module.tconstruct.item.HeavenArrowItem;
import committee.nova.mods.avaritia_integration.module.tconstruct.item.TraceArrowItem;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import slimeknights.mantle.registration.object.ItemObject;

public class TicIntegrationItems extends TicRegistry{
    public static final ItemObject<ArrowItem> HeavenArrowItem = ITEMS.register("heaven_arrow", () -> new HeavenArrowItem(new Item.Properties().rarity(ModRarities.COSMIC)));
    public static final ItemObject<ArrowItem> TraceArrowItem = ITEMS.register("trace_arrow", () -> new TraceArrowItem(new Item.Properties().rarity(ModRarities.COSMIC)));
}
