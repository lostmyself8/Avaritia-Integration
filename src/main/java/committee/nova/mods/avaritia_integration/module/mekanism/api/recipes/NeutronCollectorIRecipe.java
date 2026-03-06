package committee.nova.mods.avaritia_integration.module.mekanism.api.recipes;

import committee.nova.mods.avaritia_integration.module.mekanism.api.recipes.chemicals.GasStackToItemStackRecipe;
import committee.nova.mods.avaritia_integration.module.mekanism.common.recipe.MekIntegrationRecipeType;
import committee.nova.mods.avaritia_integration.module.mekanism.common.registry.MekIntegrationBlocks;
import committee.nova.mods.avaritia_integration.module.mekanism.common.registry.MekIntegrationRecipeSerializers;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient.GasStackIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

@NothingNullByDefault
public class NeutronCollectorIRecipe extends GasStackToItemStackRecipe {

    public NeutronCollectorIRecipe(ResourceLocation id, GasStackIngredient input, ItemStack output) {
        super(id, input, output);
    }

    @Override
    public RecipeType<GasStackToItemStackRecipe> getType() {
        return MekIntegrationRecipeType.COLLECTING.get();
    }

    @Override
    public RecipeSerializer<GasStackToItemStackRecipe> getSerializer() {
        return MekIntegrationRecipeSerializers.COLLECTOR.get();
    }

    @Override
    public String getGroup() {
        return MekIntegrationBlocks.NEUTRON_COLLECTOR.getName();
    }

    @Override
    public ItemStack getToastSymbol() {
        return MekIntegrationBlocks.NEUTRON_COLLECTOR.getItemStack();
    }
}
