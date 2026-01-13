package committee.nova.mods.avaritia_integration.module.tconstruct;

import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;

import static slimeknights.tconstruct.library.tools.capability.TinkerDataKeys.INTEGER_REGISTRY;

public interface AvaritiaDatakeys {
    static void init() {}
    TinkerDataCapability.TinkerDataKey<Integer>  Eternity = intKey("eternity");
     private static TinkerDataCapability.TinkerDataKey<Integer> intKey(String name) {
        return INTEGER_REGISTRY.register(TConstructModule.createKey(name));
    }
}
