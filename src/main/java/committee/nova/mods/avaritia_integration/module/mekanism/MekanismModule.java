package committee.nova.mods.avaritia_integration.module.mekanism;

import committee.nova.mods.avaritia_integration.module.ModMeta;
import committee.nova.mods.avaritia_integration.module.Module;
import committee.nova.mods.avaritia_integration.module.ModuleEntry;
import committee.nova.mods.avaritia_integration.module.mekanism.common.registries.MekIntegrationChemicals;
import committee.nova.mods.avaritia_integration.module.mekanism.common.registries.MekIntegrationItems;
import net.minecraft.core.Holder;
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters;
import net.minecraft.world.item.CreativeModeTab.Output;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;

@ModuleEntry(id = MekanismModule.MOD_ID, target = @ModMeta(MekanismModule.MOD_ID))
public class MekanismModule implements Module {

    public static final String MOD_ID = "mekanism";

    @Override
    public void init(IEventBus registryBus) {
        MekIntegrationItems.ITEMS.register(registryBus);
        MekIntegrationChemicals.CHEMICALS.register(registryBus);
    }

    @Override
    public void collectCreativeTabItems(ItemDisplayParameters parameters, Output output) {
        for (Holder<Item> itemHolder : MekIntegrationItems.ITEMS.getEntries()) {
            output.accept(itemHolder.value());
        }
    }
}
