package committee.nova.mods.avaritia_integration.module.tconstruct;

import committee.nova.mods.avaritia_integration.module.ModMeta;
import committee.nova.mods.avaritia_integration.module.Module;
import committee.nova.mods.avaritia_integration.module.ModuleEntry;
import committee.nova.mods.avaritia_integration.module.tconstruct.registry.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;

@ModuleEntry(
        id = TConstructModule.MOD_ID,
        target = {@ModMeta(TConstructModule.MOD_ID)}
)
public final class TConstructModule implements Module {
    public static final String MOD_ID = "tconstruct";

    @Override
    public void init(IEventBus bus) {
        TicIntegrationBlocks.BLOCKS.register(bus);
        TicIntegrationItems.ITEMS.register(bus);
        TicIntegrationFluids.FLUIDS.register(bus);
        TicIntegrationModifiers.MODIFIERS.register(bus);
        TicIntegrationModifiers.RECIPE_SERIALIZERS.register(bus);
        TicIntegrationDataKeys.init();
    }

    @Override
    public void registerEvent(IEventBus modBus, IEventBus gameBus) {
        modBus.addListener(TicIntegrationModifiers::registerSerializers);
    }

    @Override
    public void collectCreativeTabItems(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
        output.accept(TicIntegrationFluids.molten_blaze);
        output.accept(TicIntegrationFluids.molten_crystal_matrix);
        output.accept(TicIntegrationFluids.molten_star);
        output.accept(TicIntegrationFluids.molten_neutron);
        output.accept(TicIntegrationFluids.molten_infinity);
    }
}

