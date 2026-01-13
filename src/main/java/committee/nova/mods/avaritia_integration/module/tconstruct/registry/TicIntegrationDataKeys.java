package committee.nova.mods.avaritia_integration.module.tconstruct.registry;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;

import static slimeknights.tconstruct.library.tools.capability.TinkerDataKeys.INTEGER_REGISTRY;

/**
 * @author cnlimiter
 */
public class TicIntegrationDataKeys {
    public static void init() {}
    public static TinkerDataCapability.TinkerDataKey<Integer> eternity = INTEGER_REGISTRY.register(createKey("eternity"));

    public static <T> TinkerDataCapability.TinkerDataKey<T> createKey(String name) {
        return TinkerDataCapability.TinkerDataKey.of(AvaritiaIntegration.rl(name));
    }
}
