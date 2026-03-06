package committee.nova.mods.avaritia_integration.module.mekanism.common.recipe.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import committee.nova.mods.avaritia_integration.module.mekanism.api.recipes.chemicals.GasStackToItemStackRecipe;
import mekanism.api.JsonConstants;
import mekanism.api.SerializerHelper;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient.GasStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.Mekanism;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NeutronCollectorRecipeSerializer<RECIPE extends GasStackToItemStackRecipe> implements RecipeSerializer<RECIPE> {

    private final IFactory<RECIPE> factory;

    public NeutronCollectorRecipeSerializer(IFactory<RECIPE> factory) {
        this.factory = factory;
    }

    @Override
    public @NotNull RECIPE fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
        JsonElement gasInput = GsonHelper.isArrayNode(json, JsonConstants.INPUT) ? GsonHelper.getAsJsonArray(json, JsonConstants.INPUT) :
                GsonHelper.getAsJsonObject(json, JsonConstants.INPUT);
        GasStackIngredient gasIngredient = IngredientCreatorAccess.gas().deserialize(gasInput);
        ItemStack output = SerializerHelper.getItemStack(json, JsonConstants.OUTPUT);
        if (output.isEmpty()) {
            throw new JsonSyntaxException("Recipe output must not be empty.");
        }
        return factory.create(recipeId, gasIngredient, output);
    }

    @Override
    public @Nullable RECIPE fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
        try {
            GasStackIngredient gasInput = IngredientCreatorAccess.gas().read(buffer);
            ItemStack output = buffer.readItem();
            return this.factory.create(recipeId, gasInput, output);
        } catch (Exception e) {
            Mekanism.logger.error("Error reading neutron collector recipe from packet.", e);
            throw e;
        }
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull RECIPE recipe) {
        try {
            recipe.write(buffer);
        } catch (Exception e) {
            Mekanism.logger.error("Error writing neutron collector recipe to packet.", e);
            throw e;
        }
    }

    @FunctionalInterface
    public interface IFactory<RECIPE extends GasStackToItemStackRecipe> {
        RECIPE create(ResourceLocation id, GasStackIngredient input, ItemStack output);
    }
}
