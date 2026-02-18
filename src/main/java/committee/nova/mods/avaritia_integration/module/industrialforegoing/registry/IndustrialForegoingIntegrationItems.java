package committee.nova.mods.avaritia_integration.module.industrialforegoing.registry;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.industrialforegoing.item.AddonInfo;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.function.Supplier;

public class IndustrialForegoingIntegrationItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(Registries.ITEM, AvaritiaIntegration.MOD_ID);

    public static final HashMap<String, RegistryObject<Item>> ADDONS = registryAddons();

    private static HashMap<String, RegistryObject<Item>> registryAddons(){
        HashMap<String, RegistryObject<Item>> map = new HashMap<>();
        AddonInfo.create(3,"blaze_cube",ChatFormatting.YELLOW).registry(map,REGISTRY);
        AddonInfo.create(5,"crystal_matrix",ChatFormatting.AQUA).registry(map,REGISTRY);
        AddonInfo.create(8,"neutron",ChatFormatting.AQUA).registry(map,REGISTRY);
        AddonInfo.create(12,"infinity",ChatFormatting.LIGHT_PURPLE).registry(map,REGISTRY);
        return map;
    }
    public static <T extends Item> RegistryObject<T> register(String id, Supplier<T> obj) {
        return REGISTRY.register(id, obj);
    }
}
