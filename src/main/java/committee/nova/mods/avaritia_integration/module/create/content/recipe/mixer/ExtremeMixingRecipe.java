package committee.nova.mods.avaritia_integration.module.create.content.recipe.mixer;

import committee.nova.mods.avaritia_integration.module.create.content.recipe.ExtremeBasinRecipe;
import committee.nova.mods.avaritia_integration.module.create.content.recipe.ExtremeProcessingRecipeBuilder;
import committee.nova.mods.avaritia_integration.module.create.registry.CreateIntegrationRecipeTypes;

public class ExtremeMixingRecipe extends ExtremeBasinRecipe {
    public ExtremeMixingRecipe(ExtremeProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(CreateIntegrationRecipeTypes.EXTREME_MIXING, params);
    }
}
