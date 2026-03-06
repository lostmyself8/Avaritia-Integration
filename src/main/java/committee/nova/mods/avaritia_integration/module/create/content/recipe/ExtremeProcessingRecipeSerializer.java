package committee.nova.mods.avaritia_integration.module.create.content.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.fluid.FluidHelper;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.LinkedHashMap;
import java.util.Map;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ExtremeProcessingRecipeSerializer<T extends ExtremeProcessingRecipe<?>> implements RecipeSerializer<T> {
    private final ExtremeProcessingRecipeBuilder.ProcessingRecipeFactory<T> factory;

    public ExtremeProcessingRecipeSerializer(ExtremeProcessingRecipeBuilder.ProcessingRecipeFactory<T> factory) {
        this.factory = factory;
    }

    protected void writeToJson(JsonObject json, T recipe) {
        JsonArray jsonIngredients = new JsonArray();
        JsonArray jsonOutputs = new JsonArray();

        //TODO 写个sizedIngredient
//        recipe.ingredients.forEach(i -> jsonIngredients.add(i.toJson()));
        Map<JsonElement, Integer> itemCounts = new LinkedHashMap<>();
        for (Ingredient i : recipe.ingredients) {
            JsonElement je = i.toJson();
            itemCounts.put(je, itemCounts.getOrDefault(je, 0) + 1);
        }

        itemCounts.forEach((je, count) -> {
            if (je.isJsonObject() && count > 1) {
                je.getAsJsonObject().addProperty("count", count);
            } else if (count > 1) {
                JsonObject obj = new JsonObject();
                obj.add("item", je);
                obj.addProperty("count", count);
                je = obj;
            }
            jsonIngredients.add(je);
        });
        //
        recipe.fluidIngredients.forEach(i -> jsonIngredients.add(i.serialize()));

        recipe.results.forEach(o -> jsonOutputs.add(o.serialize()));
        recipe.fluidResults.forEach(o -> jsonOutputs.add(FluidHelper.serializeFluidStack(o)));

        json.add("ingredients", jsonIngredients);
        json.add("results", jsonOutputs);

        int processingDuration = recipe.getProcessingDuration();
        if (processingDuration > 0)
            json.addProperty("processingTime", processingDuration);

        ExtremeHeatCondition requiredHeat = recipe.getRequiredHeat();
        json.addProperty("heatRequirement", requiredHeat.serialize());

        recipe.writeAdditional(json);
    }

    protected T readFromJson(ResourceLocation recipeId, JsonObject json) {
        ExtremeProcessingRecipeBuilder<T> builder = new ExtremeProcessingRecipeBuilder<>(factory, recipeId);
        NonNullList<Ingredient> ingredients = NonNullList.create();
        NonNullList<FluidIngredient> fluidIngredients = NonNullList.create();
        NonNullList<ProcessingOutput> results = NonNullList.create();
        NonNullList<FluidStack> fluidResults = NonNullList.create();

        for (JsonElement je : GsonHelper.getAsJsonArray(json, "ingredients")) {
            if (FluidIngredient.isFluidIngredient(je))
                fluidIngredients.add(FluidIngredient.deserialize(je));
            //
            else if (je.isJsonObject() && je.getAsJsonObject().has("count")) {
                int count = GsonHelper.getAsInt(je.getAsJsonObject(), "count");
                Ingredient ing = Ingredient.fromJson(je);
                for (int i = 0; i < count; i++) {
                    ingredients.add(ing);
                }
            }
            //
            else {
                ingredients.add(Ingredient.fromJson(je));
            }
        }

        for (JsonElement je : GsonHelper.getAsJsonArray(json, "results")) {
            JsonObject jsonObject = je.getAsJsonObject();
            if (GsonHelper.isValidNode(jsonObject, "fluid"))
                fluidResults.add(FluidHelper.deserializeFluidStack(jsonObject));
            else
                results.add(ProcessingOutput.deserialize(je));
        }

        builder.withItemIngredients(ingredients)
                .withItemOutputs(results)
                .withFluidIngredients(fluidIngredients)
                .withFluidOutputs(fluidResults);

        if (GsonHelper.isValidNode(json, "processingTime"))
            builder.duration(GsonHelper.getAsInt(json, "processingTime"));
        if (GsonHelper.isValidNode(json, "heatRequirement"))
            builder.requiresHeat(ExtremeHeatCondition.deserialize(GsonHelper.getAsString(json, "heatRequirement")));

        T recipe = builder.build();
        recipe.readAdditional(json);
        return recipe;
    }

    protected void writeToBuffer(FriendlyByteBuf buffer, T recipe) {
        NonNullList<Ingredient> ingredients = recipe.ingredients;
        NonNullList<FluidIngredient> fluidIngredients = recipe.fluidIngredients;
        NonNullList<ProcessingOutput> outputs = recipe.results;
        NonNullList<FluidStack> fluidOutputs = recipe.fluidResults;

        buffer.writeVarInt(ingredients.size());
        ingredients.forEach(i -> i.toNetwork(buffer));
        buffer.writeVarInt(fluidIngredients.size());
        fluidIngredients.forEach(i -> i.write(buffer));

        buffer.writeVarInt(outputs.size());
        outputs.forEach(o -> o.write(buffer));
        buffer.writeVarInt(fluidOutputs.size());
        fluidOutputs.forEach(o -> o.writeToPacket(buffer));

        buffer.writeVarInt(recipe.getProcessingDuration());
        buffer.writeVarInt(recipe.getRequiredHeat()
                .ordinal());

        recipe.writeAdditional(buffer);
    }

    protected T readFromBuffer(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        NonNullList<FluidIngredient> fluidIngredients = NonNullList.create();
        NonNullList<ProcessingOutput> results = NonNullList.create();
        NonNullList<FluidStack> fluidResults = NonNullList.create();

        int size = buffer.readVarInt();
        for (int i = 0; i < size; i++)
            ingredients.add(Ingredient.fromNetwork(buffer));

        size = buffer.readVarInt();
        for (int i = 0; i < size; i++)
            fluidIngredients.add(FluidIngredient.read(buffer));

        size = buffer.readVarInt();
        for (int i = 0; i < size; i++)
            results.add(ProcessingOutput.read(buffer));

        size = buffer.readVarInt();
        for (int i = 0; i < size; i++)
            fluidResults.add(FluidStack.readFromPacket(buffer));

        T recipe = new ExtremeProcessingRecipeBuilder<>(factory, recipeId).withItemIngredients(ingredients)
                .withItemOutputs(results)
                .withFluidIngredients(fluidIngredients)
                .withFluidOutputs(fluidResults)
                .duration(buffer.readVarInt())
                .requiresHeat(ExtremeHeatCondition.values()[buffer.readVarInt()])
                .build();
        recipe.readAdditional(buffer);
        return recipe;
    }

    public final void write(JsonObject json, T recipe) {
        writeToJson(json, recipe);
    }

    @Override
    public final T fromJson(ResourceLocation id, JsonObject json) {
        return readFromJson(id, json);
    }

    @Override
    public final void toNetwork(FriendlyByteBuf buffer, T recipe) {
        writeToBuffer(buffer, recipe);
    }

    @Override
    public final T fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
        return readFromBuffer(id, buffer);
    }

    public ExtremeProcessingRecipeBuilder.ProcessingRecipeFactory<T> getFactory() {
        return factory;
    }

}
