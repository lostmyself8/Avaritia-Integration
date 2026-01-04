package committee.nova.mods.avaritia_integration.module.mekanism.common.recipe;

import committee.nova.mods.avaritia_integration.module.mekanism.api.recipes.MekCompressorRecipe;
import committee.nova.mods.avaritia_integration.module.mekanism.api.recipes.chemicals.GasStackToItemStackRecipe;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleChemical;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleItem;
import mekanism.common.registration.impl.RecipeTypeRegistryObject;

public class MekIntegrationRecipeType {

    public static RecipeTypeRegistryObject<GasStackToItemStackRecipe, SingleChemical<Gas, GasStack, GasStackToItemStackRecipe>> COLLECTING;
    public static RecipeTypeRegistryObject<MekCompressorRecipe, SingleItem<MekCompressorRecipe>> MEK_COMPRESSING;
}
