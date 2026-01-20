package committee.nova.mods.avaritia_integration.module.mekanism.common.registry;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.mekanism.api.recipes.MekCompressorIRecipe;
import committee.nova.mods.avaritia_integration.module.mekanism.api.recipes.NeutronCollectorIRecipe;
import committee.nova.mods.avaritia_integration.module.mekanism.api.recipes.chemicals.GasStackToItemStackRecipe;
import committee.nova.mods.avaritia_integration.module.mekanism.common.recipe.serializer.NeutronCollectorRecipeSerializer;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.common.recipe.serializer.ItemStackToItemStackRecipeSerializer;
import mekanism.common.registration.impl.RecipeSerializerDeferredRegister;
import mekanism.common.registration.impl.RecipeSerializerRegistryObject;

public class MekIntegrationRecipeSerializers {

    private MekIntegrationRecipeSerializers() {

    }

    public static final RecipeSerializerDeferredRegister RECIPE_SERIALIZERS = new RecipeSerializerDeferredRegister(AvaritiaIntegration.MOD_ID);

    public static final RecipeSerializerRegistryObject<GasStackToItemStackRecipe> COLLECTOR = RECIPE_SERIALIZERS.register("collector", () -> new NeutronCollectorRecipeSerializer<>(NeutronCollectorIRecipe::new));
    public static final RecipeSerializerRegistryObject<ItemStackToItemStackRecipe> MEK_COMPRESSOR = RECIPE_SERIALIZERS.register("compressor", () -> new ItemStackToItemStackRecipeSerializer<>(MekCompressorIRecipe::new));
}
