package committee.nova.mods.avaritia_integration.module.tconstruct.registry;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import slimeknights.mantle.registration.deferred.ItemDeferredRegister;
import slimeknights.tconstruct.common.registration.FluidDeferredRegisterExtension;

public class TicRegistry {
    protected static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(AvaritiaIntegration.MOD_ID);
    protected static final FluidDeferredRegisterExtension FLUIDS = new FluidDeferredRegisterExtension(AvaritiaIntegration.MOD_ID);
    public static void initRegisters() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(bus);
        FLUIDS.register(bus);
    }
}
