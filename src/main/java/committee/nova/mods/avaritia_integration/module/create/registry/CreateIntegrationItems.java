package committee.nova.mods.avaritia_integration.module.create.registry;

import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.CombustibleItem;
import com.tterrag.registrate.util.entry.ItemEntry;
import committee.nova.mods.avaritia.api.common.item.BaseItem;
import committee.nova.mods.avaritia.init.registry.ModRarities;
import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.create.CreateModule;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public final class CreateIntegrationItems {
    private static final CreateRegistrate REGISTRATE = CreateModule.REGISTRATE;
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(Registries.ITEM, AvaritiaIntegration.MOD_ID);

    public static final RegistryObject<Item> CREATIVE_MECHANISM = register("creative_mechanism", () -> new BaseItem(pro -> pro.rarity(ModRarities.EPIC)));
    public static final RegistryObject<Item> CREATIVE_COMPOUND = register("creative_compound", () -> new BaseItem(pro -> pro.rarity(ModRarities.EPIC)));

    //TODO 这里的BurnTime需要重新确定
    public static final ItemEntry<Item> STAR_BLAZE_CAKE_BASE = REGISTRATE.item("star_blaze_cake_base", Item::new)
            .tag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag)
            .model((c, p) -> p.getExistingFile(p.modLoc("item/" + c.getName())))
            .register();
    public static final ItemEntry<CombustibleItem> STAR_BLAZE_CAKE = REGISTRATE.item("star_blaze_cake", CombustibleItem::new)
            .tag(CreateIntegrationTags.ItemTags.BLAZE_BURNER_FUEL_STAR.tag, AllTags.AllItemTags.UPRIGHT_ON_BELT.tag)
            .model((c, p) -> p.getExistingFile(p.modLoc("item/" + c.getName())))
            .onRegister(i -> i.setBurnTime(36000))
            .register();
    public static final ItemEntry<Item> BLAZE_BLAZE_CAKE_BASE = REGISTRATE.item("blaze_blaze_cake_base", Item::new)
            .tag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag)
            .model((c, p) -> p.getExistingFile(p.modLoc("item/" + c.getName())))
            .register();
    public static final ItemEntry<CombustibleItem> BLAZE_BLAZE_CAKE = REGISTRATE.item("blaze_blaze_cake", CombustibleItem::new)
            .tag(CreateIntegrationTags.ItemTags.BLAZE_BURNER_FUEL_BLAZE.tag, AllTags.AllItemTags.UPRIGHT_ON_BELT.tag)
            .model((c, p) -> p.getExistingFile(p.modLoc("item/" + c.getName())))
            .onRegister(i -> i.setBurnTime(36000))
            .register();

    public static void register() {
    }

    public static <T extends Item> RegistryObject<T> register(String id, Supplier<T> obj) {
        return REGISTRY.register(id, obj);
    }
}
