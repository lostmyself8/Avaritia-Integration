package committee.nova.mods.avaritia_integration.module.tconstruct;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.ModMeta;
import committee.nova.mods.avaritia_integration.module.Module;
import committee.nova.mods.avaritia_integration.module.ModuleEntry;
import committee.nova.mods.avaritia_integration.module.tconstruct.registry.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;

@ModuleEntry(
        id = "tconstruct",
        target = {@ModMeta("tconstruct")}
)
public final class TConstructModule implements Module {
    public static final String MOD_ID = "tconstruct";

    public void init(IEventBus bus) {
        bus.register(new TicIntegrationItems());
        bus.register(new TicIntegrationBlocks());
        bus.register(new TicIntegrationFluids());
        bus.register(new TicIntegrationModifiers());
        TicIntegrationModifiers.initRegisters();
        TicRegistry.initRegisters();
        AvaritiaDatakeys.init();
    }

    public static ResourceLocation getResource(String name) {
        return new ResourceLocation(AvaritiaIntegration.MOD_ID, name);
    }
    public static <T> TinkerDataCapability.TinkerDataKey<T> createKey(String name) {
        return TinkerDataCapability.TinkerDataKey.of(getResource(name));
    }
}

