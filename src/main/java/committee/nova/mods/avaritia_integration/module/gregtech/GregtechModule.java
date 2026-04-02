package committee.nova.mods.avaritia_integration.module.gregtech;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import committee.nova.mods.avaritia_integration.module.ModMeta;
import committee.nova.mods.avaritia_integration.module.Module;
import committee.nova.mods.avaritia_integration.module.ModuleEntry;
import committee.nova.mods.avaritia_integration.module.gregtech.registry.AICreativeModeTabs;
import committee.nova.mods.avaritia_integration.module.gregtech.registry.AIMaterials;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;

@ModuleEntry(id = GTCEu.MOD_ID, target = @ModMeta(GTCEu.MOD_ID))
public class GregtechModule implements Module {
    public static AIGTRegistrate REGISTRATE = AIGTRegistrate.create();

    @Override
    public void init(IEventBus registryBus) {
        REGISTRATE.registerRegistrate();
        AICreativeModeTabs.init();
    }

    @Override
    public void registerEvent(IEventBus modBus, IEventBus gameBus) {
        modBus.addListener(AIMaterials::registerMaterialRegistry);
        modBus.addListener(AIMaterials::init);
    }
}
