package committee.nova.mods.avaritia_integration.module.create;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.ModMeta;
import committee.nova.mods.avaritia_integration.module.Module;
import committee.nova.mods.avaritia_integration.module.ModuleEntry;
import committee.nova.mods.avaritia_integration.module.create.config.CreateIntegrationConfigs;
import committee.nova.mods.avaritia_integration.module.create.content.BoilerHeaters;
import committee.nova.mods.avaritia_integration.module.create.registry.*;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;

@ModuleEntry(id = CreateModule.MOD_ID, target = @ModMeta(CreateModule.MOD_ID))
public final class CreateModule implements Module {
    public static final String MOD_ID = "create";
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(AvaritiaIntegration.MOD_ID)
            .setTooltipModifierFactory(item ->
                    new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                            .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
            );

    @Override
    public void init(IEventBus registryBus) {
        REGISTRATE.registerEventListeners(registryBus);
        CreateIntegrationItems.REGISTRY.register(registryBus);

        CreateIntegrationBlocks.register();
        CreateIntegrationBlockEntityTypes.register();
        CreateIntegrationItems.register();
        CreateIntegrationRecipeTypes.register(registryBus);

        CreateIntegrationConfigs.register(ModLoadingContext.get());
    }

    @Override
    public void process() {
        BoilerHeaters.registerDefaults();
    }

    @Override
    public void initClient() {
        CreateIntegrationPartialModels.init();
        CreateIntegrationSpriteShifts.init();
    }

    @Override
    public void registerEvent(IEventBus modBus, IEventBus gameBus) {
        modBus.addListener(CreateIntegrationConfigs::onLoad);
        modBus.addListener(CreateIntegrationConfigs::onReload);
    }

    @Override
    public void collectCreativeTabItems(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
        output.accept(CreateIntegrationItems.CREATIVE_MECHANISM.get());
        output.accept(CreateIntegrationItems.CREATIVE_COMPOUND.get());
        output.accept(CreateIntegrationItems.STAR_BLAZE_CAKE.get());
        output.accept(CreateIntegrationItems.STAR_BLAZE_CAKE_BASE.get());
        output.accept(CreateIntegrationItems.BLAZE_BLAZE_CAKE.get());
        output.accept(CreateIntegrationItems.BLAZE_BLAZE_CAKE_BASE.get());

        output.accept(CreateIntegrationBlocks.EXTREME_BLAZE_BURNER.asItem());
        output.accept(CreateIntegrationBlocks.CRYSTAL_MATRIX_CASING.asItem());
        output.accept(CreateIntegrationBlocks.NEUTRON_MECHANICAL_PRESS.asItem());
        output.accept(CreateIntegrationBlocks.EXTREME_BASIN.asItem());
        output.accept(CreateIntegrationBlocks.MATRIX_MECHANICAL_MIXER.asItem());
    }
}
