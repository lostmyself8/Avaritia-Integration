package committee.nova.mods.avaritia_integration;

import com.mojang.logging.LogUtils;
import committee.nova.mods.avaritia_integration.init.data.AIDataGen;
import committee.nova.mods.avaritia_integration.init.registry.*;
import committee.nova.mods.avaritia_integration.module.ModuleManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(AvaritiaIntegration.MOD_ID)
public class AvaritiaIntegration {
    public static final String MOD_ID = "avaritia_integration";
    public static final Logger LOGGER = LogUtils.getLogger();

    @SuppressWarnings("removal")
    public AvaritiaIntegration() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        AICreativeTabs.REGISTRY.register(bus);
        AIItems.REGISTRY.register(bus);
        AIBlocks.REGISTRY.register(bus);
        AIFluidTypes.REGISTRY.register(bus);
        AIFluids.REGISTRY.register(bus);
        ModuleManager.loadModules(bus);
        bus.addListener(AIDataGen::gatherData);
    }

    public static ResourceLocation rl(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
}
