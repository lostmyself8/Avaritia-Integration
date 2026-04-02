package committee.nova.mods.avaritia_integration.module.gregtech.registry;

import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs;
import com.tterrag.registrate.util.entry.RegistryEntry;
import committee.nova.mods.avaritia_integration.init.registry.AIItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import static committee.nova.mods.avaritia_integration.module.gregtech.GregtechModule.REGISTRATE;

public class AICreativeModeTabs {
    public static void init() {}
    public static RegistryEntry<CreativeModeTab> ITEM = REGISTRATE.defaultCreativeTab("item",
                    builder -> builder.displayItems(new GTCreativeModeTabs.RegistrateDisplayItemsGenerator("item", REGISTRATE))
                            .icon(AIItems.CRYSTAL_MATRIX_GEAR.get()::getDefaultInstance)
                            .title(Component.translatable("itemGroup.tab.Integration.materialItem"))
                            .build())
            .register();
}
