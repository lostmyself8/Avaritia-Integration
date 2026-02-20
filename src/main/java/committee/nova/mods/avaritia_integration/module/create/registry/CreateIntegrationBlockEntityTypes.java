package committee.nova.mods.avaritia_integration.module.create.registry;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import committee.nova.mods.avaritia_integration.module.create.CreateModule;
import committee.nova.mods.avaritia_integration.module.create.content.extreme_burner.ExtremeBlazeBurnerBlockEntity;
import committee.nova.mods.avaritia_integration.module.create.content.extreme_burner.ExtremeBlazeBurnerRenderer;
import committee.nova.mods.avaritia_integration.module.create.content.extreme_burner.ExtremeBlazeBurnerVisual;

public class CreateIntegrationBlockEntityTypes {
    private static final CreateRegistrate REGISTRATE = CreateModule.REGISTRATE;
    public static final BlockEntityEntry<ExtremeBlazeBurnerBlockEntity> EXTREME_HEATER;

    public static void register() {
    }

    static {
        EXTREME_HEATER = REGISTRATE.blockEntity("extreme_blaze_heater", ExtremeBlazeBurnerBlockEntity::new)
                .visual(() -> ExtremeBlazeBurnerVisual::new, false)
                .validBlocks(CreateIntegrationBlocks.EXTREME_BLAZE_BURNER)
                .renderer(() -> ExtremeBlazeBurnerRenderer::new)
                .register();
    }
}
