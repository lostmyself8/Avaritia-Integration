package committee.nova.mods.avaritia_integration.module.tconstruct.registry;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.tconstruct.modifiers.*;
import committee.nova.mods.avaritia_integration.module.tconstruct.modules.CondensingModule;
import committee.nova.mods.avaritia_integration.module.tconstruct.modules.InfinitumModule;
import committee.nova.mods.avaritia_integration.module.tconstruct.modules.RuleOverModule;
import committee.nova.mods.avaritia_integration.module.tconstruct.modules.VaultSplitModule;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import slimeknights.mantle.registration.deferred.SynchronizedDeferredRegister;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import static committee.nova.mods.avaritia_integration.module.tconstruct.TConstructModule.getResource;

public class TicIntegrationModifiers {
    public TicIntegrationModifiers(){
        MODIFIERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
    public static void initRegisters(){
        IEventBus bus =FMLJavaModLoadingContext.get().getModEventBus();
        RECIPE_SERIALIZERS.register(bus);
    }
    private static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(AvaritiaIntegration.MOD_ID);
    protected static final SynchronizedDeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = SynchronizedDeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, AvaritiaIntegration.MOD_ID);
    public static final StaticModifier<BlazeCrownModifier> BlazeCrown = MODIFIERS.register("blaze_crown", BlazeCrownModifier::new);
    public static final StaticModifier<CrystalshineModifier> Crystalshine = MODIFIERS.register("crystalshine", CrystalshineModifier::new);
    public static final StaticModifier<CrystaluixModifier> CrystaluixModifier = MODIFIERS.register("crystaluix", CrystaluixModifier::new);
    public static final StaticModifier<StarDashModifier> StarDash = MODIFIERS.register("star_dash", StarDashModifier::new);
    public static final StaticModifier<SuperheatModifier> Superheat = MODIFIERS.register("superheat", SuperheatModifier::new);

    @SubscribeEvent
    void registerSerializers(RegisterEvent event) {
        if (event.getRegistryKey() == Registries.RECIPE_SERIALIZER) {
            ModifierModule.LOADER.register(getResource("infinitum"), InfinitumModule.LOADER);
            ModifierModule.LOADER.register(getResource("rule_over"), RuleOverModule.LOADER);
            ModifierModule.LOADER.register(getResource("vault_split"), VaultSplitModule.LOADER);
            ModifierModule.LOADER.register(getResource("condensing"), CondensingModule.LOADER);
        }
    }
}
