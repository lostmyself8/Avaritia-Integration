package committee.nova.mods.avaritia_integration.module.mysticalagriculture.registry;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class MysticalAgradditionsIntegrationItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, AvaritiaIntegration.MOD_ID);
    public static final RegistryObject<Item> INFINITY_CRUX = ITEMS.register("infinity_crux", () -> new BlockItem(MysticalAgradditionsIntegrationBlocks.INFINITY_CRUX.get(), new Item.Properties()));
    public static final RegistryObject<Item> CRYSTAL_MATRIX_CRUX = ITEMS.register("crystal_matrix_crux", () -> new BlockItem(MysticalAgradditionsIntegrationBlocks.CRYSTAL_MATRIX_CRUX.get(), new Item.Properties()));
}
