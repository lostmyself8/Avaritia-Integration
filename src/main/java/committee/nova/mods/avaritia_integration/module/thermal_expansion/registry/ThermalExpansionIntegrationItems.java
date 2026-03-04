package committee.nova.mods.avaritia_integration.module.thermal_expansion.registry;

import cofh.core.util.helpers.AugmentDataHelper;
import cofh.thermal.lib.common.item.AugmentItem;
import committee.nova.mods.avaritia.api.common.item.BaseItem;
import committee.nova.mods.avaritia.init.registry.ModRarities;
import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static cofh.lib.util.constants.NBTTags.*;

public final class ThermalExpansionIntegrationItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(Registries.ITEM, AvaritiaIntegration.MOD_ID);

    public static final RegistryObject<Item> CREATIVE_AUGMENT_BASE = register("creative_augment_base", () -> new BaseItem(pro -> pro.rarity(ModRarities.EPIC)));
    public static final RegistryObject<AugmentItem> CREATIVE_INTEGRAL_COMPONENTS = register("creative_integral_components", () -> new AugmentItem(new Item.Properties().rarity(ModRarities.EPIC), AugmentDataHelper.builder()
            .type(TAG_AUGMENT_TYPE_UPGRADE)
            .mod(TAG_AUGMENT_RF_CREATIVE,1)
            .mod(TAG_AUGMENT_MACHINE_ENERGY, 0).build()
    ));

    public static <T extends Item> RegistryObject<T> register(String id, Supplier<T> obj) {
        return REGISTRY.register(id, obj);
    }
}
