package committee.nova.mods.avaritia_integration.module.tconstruct;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.ModMeta;
import committee.nova.mods.avaritia_integration.module.Module;
import committee.nova.mods.avaritia_integration.module.ModuleEntry;
import committee.nova.mods.avaritia_integration.module.tconstruct.registry.TicIntegrationFluids;
import committee.nova.mods.avaritia_integration.module.tconstruct.registry.TicIntegrationItems;
import committee.nova.mods.avaritia_integration.module.tconstruct.registry.TicIntegrationModifiers;
import committee.nova.mods.avaritia_integration.module.tconstruct.registry.TicRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;

@ModuleEntry(id = TConstructModule.MOD_ID, target = @ModMeta(TConstructModule.MOD_ID))
public final class TConstructModule implements Module {

    public static final String MOD_ID = "tconstruct";
    @Override
    public void init(IEventBus bus) {
        bus.register(new TicIntegrationItems());
        bus.register(new TicIntegrationFluids());
        bus.register(new TicIntegrationModifiers());
        TicIntegrationModifiers.initRegisters();
        TicRegistry.initRegisters();
    }
    public static ResourceLocation getResource(String name) {
        return new ResourceLocation(AvaritiaIntegration.MOD_ID, name);
    }
}
