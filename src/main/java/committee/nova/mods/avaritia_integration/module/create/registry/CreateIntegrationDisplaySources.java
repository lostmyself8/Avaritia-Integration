package committee.nova.mods.avaritia_integration.module.create.registry;

import com.simibubi.create.api.behaviour.display.DisplaySource;
import com.simibubi.create.content.redstone.displayLink.source.ItemNameDisplaySource;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import committee.nova.mods.avaritia_integration.module.create.CreateModule;

import java.util.function.Supplier;

public class CreateIntegrationDisplaySources {
    private static final CreateRegistrate REGISTRATE = CreateModule.REGISTRATE;

    public static final RegistryEntry<ItemNameDisplaySource> ITEM_NAMES = simple("item_names", ItemNameDisplaySource::new);

    private static <T extends DisplaySource> RegistryEntry<T> simple(String name, Supplier<T> supplier) {
        return REGISTRATE.displaySource(name, supplier).register();
    }

    public static void register() {
    }
}
