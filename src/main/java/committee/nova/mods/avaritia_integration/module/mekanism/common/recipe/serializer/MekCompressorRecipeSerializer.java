package committee.nova.mods.avaritia_integration.module.mekanism.common.recipe.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import committee.nova.mods.avaritia_integration.module.mekanism.api.recipes.MekCompressorRecipe;
import mekanism.api.JsonConstants;
import mekanism.api.SerializerHelper;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.Mekanism;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MekCompressorRecipeSerializer<RECIPE extends MekCompressorRecipe> implements RecipeSerializer<RECIPE> {

    private final IFactory<RECIPE> factory;

    public MekCompressorRecipeSerializer(IFactory<RECIPE> factory) {
        this.factory = factory;
    }

    @Override
    public @NotNull RECIPE fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
        JsonElement itemInput = GsonHelper.isArrayNode(json, JsonConstants.ITEM_INPUT) ? GsonHelper.getAsJsonArray(json, JsonConstants.ITEM_INPUT) :
                GsonHelper.getAsJsonObject(json, JsonConstants.ITEM_INPUT);
        ItemStackIngredient itemIngredient = IngredientCreatorAccess.item().deserialize(itemInput);
        int inputCount = 1000;
        int timeCost = 240;
        JsonElement count = json.get("inputCount");
        JsonElement cost = json.get("timeCost");
        if (!GsonHelper.isNumberValue(count) || !GsonHelper.isNumberValue(cost)) {
            throw new JsonSyntaxException("Expected inputCount not to be a number.");
        }
        inputCount = count.getAsJsonPrimitive().getAsInt();
        timeCost = cost.getAsJsonPrimitive().getAsInt();
        ItemStack output = SerializerHelper.getItemStack(json, JsonConstants.OUTPUT);
        if (output.isEmpty()) {
            throw new JsonSyntaxException("Recipe output must not be empty.");
        }
        return factory.create(recipeId, itemIngredient, output, inputCount, timeCost);
    }

    @Override
    public @Nullable RECIPE fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
        try {
            ItemStackIngredient inputIngredient = IngredientCreatorAccess.item().read(buffer);
            ItemStack output = buffer.readItem();
            int inputCount = buffer.readInt();
            int timeCost = buffer.readInt();
            return this.factory.create(recipeId, inputIngredient, output, inputCount, timeCost);
        } catch (Exception e) {
            Mekanism.logger.error("Error reading Mek-compressor recipe from packet.", e);
            throw e;
        }
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull RECIPE recipe) {
        try {
            recipe.write(buffer);
        } catch (Exception e) {
            Mekanism.logger.error("Error writing Mek-compressor recipe to packet.", e);
            throw e;
        }
    }

    @FunctionalInterface
    public interface IFactory<RECIPE extends MekCompressorRecipe> {
        RECIPE create(ResourceLocation id, ItemStackIngredient input, ItemStack output, int inputCount, int timeCost);
    }
}
