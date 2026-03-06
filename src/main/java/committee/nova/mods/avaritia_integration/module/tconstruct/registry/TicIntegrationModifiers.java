package committee.nova.mods.avaritia_integration.module.tconstruct.registry;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.tconstruct.modifiers.*;
import committee.nova.mods.avaritia_integration.module.tconstruct.modules.CondensingModule;
import committee.nova.mods.avaritia_integration.module.tconstruct.modules.InfinitumModule;
import committee.nova.mods.avaritia_integration.module.tconstruct.modules.RuleOverModule;
import committee.nova.mods.avaritia_integration.module.tconstruct.modules.VaultSplitModule;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import slimeknights.mantle.registration.deferred.SynchronizedDeferredRegister;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import static committee.nova.mods.avaritia_integration.AvaritiaIntegration.rl;

public class TicIntegrationModifiers {
    public static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(AvaritiaIntegration.MOD_ID);
    public static final SynchronizedDeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = SynchronizedDeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, AvaritiaIntegration.MOD_ID);


    public static final StaticModifier<BlazeCrownModifier> BLAZE_CROWN = MODIFIERS.register("blaze_crown", BlazeCrownModifier::new);
    public static final StaticModifier<CrystalshineModifier> CRYSTAL_SHINE = MODIFIERS.register("crystalshine", CrystalshineModifier::new);
    public static final StaticModifier<CrystaluixModifier> CRYSTALUIX = MODIFIERS.register("crystaluix", CrystaluixModifier::new);
    public static final StaticModifier<StarDashModifier> STAR_DASH = MODIFIERS.register("star_dash", StarDashModifier::new);
    public static final StaticModifier<SuperheatModifier> SUPERHEAT = MODIFIERS.register("superheat", SuperheatModifier::new);

    public static void registerSerializers(RegisterEvent event) {
        if (event.getRegistryKey() == Registries.RECIPE_SERIALIZER) {
            ModifierModule.LOADER.register(rl("infinitum"), InfinitumModule.LOADER);
            ModifierModule.LOADER.register(rl("rule_over"), RuleOverModule.LOADER);
            ModifierModule.LOADER.register(rl("vault_split"), VaultSplitModule.LOADER);
            ModifierModule.LOADER.register(rl("condensing"), CondensingModule.LOADER);
        }
    }

    public static final ModifierId RULE_OVER = id("rule_over");
    public static final ModifierId ETERNITY = id("eternity");

    public static ModifierId id(String name) {
        return new ModifierId(new ResourceLocation(AvaritiaIntegration.MOD_ID, name));
    }
}
