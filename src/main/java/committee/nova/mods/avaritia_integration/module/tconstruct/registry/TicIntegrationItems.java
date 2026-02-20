package committee.nova.mods.avaritia_integration.module.tconstruct.registry;

import committee.nova.mods.avaritia.init.registry.ModRarities;
import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.tconstruct.item.HeavenArrowItem;
import committee.nova.mods.avaritia_integration.module.tconstruct.item.TraceArrowItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.registration.deferred.ItemDeferredRegister;
import slimeknights.mantle.registration.object.ItemObject;

import java.util.function.Supplier;

public class TicIntegrationItems{
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(AvaritiaIntegration.MOD_ID);
    public static final ItemObject<ArrowItem> HeavenArrowItem = ITEMS.register("heaven_arrow", () -> new HeavenArrowItem(new Item.Properties().rarity(ModRarities.COSMIC)));
    public static final ItemObject<ArrowItem> TraceArrowItem = ITEMS.register("trace_arrow", () -> new TraceArrowItem(new Item.Properties().rarity(ModRarities.COSMIC)));
    public static final ItemObject<BucketItem> MoltenInfinityBucket = ITEMS.register("molten_infinity_bucket", () -> new BucketItem(TicIntegrationFluids.molten_infinity, new Item.Properties().stacksTo(1).rarity(ModRarities.COSMIC)));
}
