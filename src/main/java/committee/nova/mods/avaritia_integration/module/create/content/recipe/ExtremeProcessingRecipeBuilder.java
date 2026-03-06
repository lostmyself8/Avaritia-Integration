package committee.nova.mods.avaritia_integration.module.create.content.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.simibubi.create.api.data.recipe.DatagenMod;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.data.SimpleDatagenIngredient;
import com.simibubi.create.foundation.fluid.FluidHelper;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.tterrag.registrate.util.DataIngredient;
import net.createmod.catnip.data.Pair;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ExtremeProcessingRecipeBuilder<T extends ExtremeProcessingRecipe<?>> {
    protected ResourceLocation recipeId;
    protected ProcessingRecipeFactory<T> factory;
    protected ProcessingRecipeParams params;
    protected List<ICondition> recipeConditions;

    public ExtremeProcessingRecipeBuilder(ProcessingRecipeFactory<T> factory, ResourceLocation recipeId) {
        this.recipeId = recipeId;
        params = new ProcessingRecipeParams(recipeId);
        recipeConditions = new ArrayList<>();
        this.factory = factory;
    }

    public ExtremeProcessingRecipeBuilder<T> withItemIngredients(Ingredient... ingredients) {
        return withItemIngredients(NonNullList.of(Ingredient.EMPTY, ingredients));
    }

    public ExtremeProcessingRecipeBuilder<T> withItemIngredients(NonNullList<Ingredient> ingredients) {
        params.ingredients = ingredients;
        return this;
    }

    public ExtremeProcessingRecipeBuilder<T> withSingleItemOutput(ItemStack output) {
        return withItemOutputs(new ProcessingOutput(output, 1));
    }

    public ExtremeProcessingRecipeBuilder<T> withItemOutputs(ProcessingOutput... outputs) {
        return withItemOutputs(NonNullList.of(ProcessingOutput.EMPTY, outputs));
    }

    public ExtremeProcessingRecipeBuilder<T> withItemOutputs(NonNullList<ProcessingOutput> outputs) {
        params.results = outputs;
        return this;
    }

    public ExtremeProcessingRecipeBuilder<T> withFluidIngredients(FluidIngredient... ingredients) {
        return withFluidIngredients(NonNullList.of(FluidIngredient.EMPTY, ingredients));
    }

    public ExtremeProcessingRecipeBuilder<T> withFluidIngredients(NonNullList<FluidIngredient> ingredients) {
        params.fluidIngredients = ingredients;
        return this;
    }

    public ExtremeProcessingRecipeBuilder<T> withFluidOutputs(FluidStack... outputs) {
        return withFluidOutputs(NonNullList.of(FluidStack.EMPTY, outputs));
    }

    public ExtremeProcessingRecipeBuilder<T> withFluidOutputs(NonNullList<FluidStack> outputs) {
        params.fluidResults = outputs;
        return this;
    }

    public ExtremeProcessingRecipeBuilder<T> duration(int ticks) {
        params.processingDuration = ticks;
        return this;
    }

    public ExtremeProcessingRecipeBuilder<T> averageProcessingDuration() {
        return duration(100);
    }

    public ExtremeProcessingRecipeBuilder<T> requiresHeat(ExtremeHeatCondition condition) {
        params.requiredHeat = condition;
        return this;
    }

    public T build() {
        return factory.create(params);
    }

    public void build(Consumer<FinishedRecipe> consumer) {
        consumer.accept(new DataGenResult<>(build(), recipeConditions));
    }

    // Datagen shortcuts

    public ExtremeProcessingRecipeBuilder<T> require(TagKey<Item> tag) {
        return require(Ingredient.of(tag));
    }

    public ExtremeProcessingRecipeBuilder<T> require(ItemLike item) {
        return require(Ingredient.of(item));
    }

    public ExtremeProcessingRecipeBuilder<T> require(Ingredient ingredient) {
        params.ingredients.add(ingredient);
        return this;
    }

    public ExtremeProcessingRecipeBuilder<T> require(ItemLike item, int amount) {
        return require(Ingredient.of(item), amount);
    }

    public ExtremeProcessingRecipeBuilder<T> require(Ingredient ingredient, int amount) {
        for (int i = 0; i < amount; i++) {
            params.ingredients.add(ingredient);
        }
        return this;
    }

    public ExtremeProcessingRecipeBuilder<T> require(DatagenMod mod, String id) {
        params.ingredients.add(new SimpleDatagenIngredient(mod, id));
        return this;
    }

    public ExtremeProcessingRecipeBuilder<T> require(ResourceLocation ingredient) {
        params.ingredients.add(DataIngredient.ingredient(null, ingredient));
        return this;
    }

    public ExtremeProcessingRecipeBuilder<T> require(Fluid fluid, int amount) {
        return require(FluidIngredient.fromFluid(fluid, amount));
    }

    public ExtremeProcessingRecipeBuilder<T> require(TagKey<Fluid> fluidTag, int amount) {
        return require(FluidIngredient.fromTag(fluidTag, amount));
    }

    public ExtremeProcessingRecipeBuilder<T> require(FluidIngredient ingredient) {
        params.fluidIngredients.add(ingredient);
        return this;
    }

    public ExtremeProcessingRecipeBuilder<T> output(ItemLike item) {
        return output(item, 1);
    }

    public ExtremeProcessingRecipeBuilder<T> output(float chance, ItemLike item) {
        return output(chance, item, 1);
    }

    public ExtremeProcessingRecipeBuilder<T> output(ItemLike item, int amount) {
        return output(1, item, amount);
    }

    public ExtremeProcessingRecipeBuilder<T> output(float chance, ItemLike item, int amount) {
        return output(chance, new ItemStack(item, amount));
    }

    public ExtremeProcessingRecipeBuilder<T> output(ItemStack output) {
        return output(1, output);
    }

    public ExtremeProcessingRecipeBuilder<T> output(float chance, ItemStack output) {
        return output(new ProcessingOutput(output, chance));
    }

    public ExtremeProcessingRecipeBuilder<T> output(float chance, DatagenMod mod, String id, int amount) {
        return output(new ProcessingOutput(Pair.of(mod.asResource(id), amount), chance));
    }

    public ExtremeProcessingRecipeBuilder<T> output(ResourceLocation id) {
        return output(1, id, 1);
    }

    public ExtremeProcessingRecipeBuilder<T> output(DatagenMod mod, String id) {
        return output(1, mod.asResource(id), 1);
    }

    public ExtremeProcessingRecipeBuilder<T> output(float chance, ResourceLocation registryName, int amount) {
        return output(new ProcessingOutput(Pair.of(registryName, amount), chance));
    }

    public ExtremeProcessingRecipeBuilder<T> output(ProcessingOutput output) {
        params.results.add(output);
        return this;
    }

    public ExtremeProcessingRecipeBuilder<T> output(Fluid fluid, int amount) {
        fluid = FluidHelper.convertToStill(fluid);
        return output(new FluidStack(fluid, amount));
    }

    public ExtremeProcessingRecipeBuilder<T> output(FluidStack fluidStack) {
        params.fluidResults.add(fluidStack);
        return this;
    }

    public ExtremeProcessingRecipeBuilder<T> toolNotConsumed() {
        params.keepHeldItem = true;
        return this;
    }

    //

    public ExtremeProcessingRecipeBuilder<T> whenModLoaded(String modid) {
        return withCondition(new ModLoadedCondition(modid));
    }

    public ExtremeProcessingRecipeBuilder<T> whenModMissing(String modid) {
        return withCondition(new NotCondition(new ModLoadedCondition(modid)));
    }

    public ExtremeProcessingRecipeBuilder<T> withCondition(ICondition condition) {
        recipeConditions.add(condition);
        return this;
    }

    @FunctionalInterface
    public interface ProcessingRecipeFactory<T extends ExtremeProcessingRecipe<?>> {
        T create(ProcessingRecipeParams params);
    }

    public static class ProcessingRecipeParams {
        protected ResourceLocation id;
        protected NonNullList<Ingredient> ingredients;
        protected NonNullList<ProcessingOutput> results;
        protected NonNullList<FluidIngredient> fluidIngredients;
        protected NonNullList<FluidStack> fluidResults;
        protected int processingDuration;
        protected ExtremeHeatCondition requiredHeat;

        public boolean keepHeldItem;

        protected ProcessingRecipeParams(ResourceLocation id) {
            this.id = id;
            ingredients = NonNullList.create();
            results = NonNullList.create();
            fluidIngredients = NonNullList.create();
            fluidResults = NonNullList.create();
            processingDuration = 0;
            requiredHeat = ExtremeHeatCondition.NORMAL;
            keepHeldItem = false;
        }
    }
    public static class DataGenResult<S extends ExtremeProcessingRecipe<?>> implements FinishedRecipe {

        private List<ICondition> recipeConditions;
        private ExtremeProcessingRecipeSerializer<S> serializer;
        private ResourceLocation id;
        private S recipe;

        @SuppressWarnings("unchecked")
        public DataGenResult(S recipe, List<ICondition> recipeConditions) {
            this.recipe = recipe;
            this.recipeConditions = recipeConditions;
            IRecipeTypeInfo recipeType = this.recipe.getTypeInfo();
            ResourceLocation typeId = recipeType.getId();

            if (!(recipeType.getSerializer() instanceof ExtremeProcessingRecipeSerializer))
                throw new IllegalStateException("Cannot datagen ProcessingRecipe of type: " + typeId);

            this.id = new ResourceLocation(recipe.getId().getNamespace(),
                    typeId.getPath() + "/" + recipe.getId().getPath());
            this.serializer = (ExtremeProcessingRecipeSerializer<S>) recipe.getSerializer();
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            serializer.write(json, recipe);
            if (recipeConditions.isEmpty())
                return;

            JsonArray conds = new JsonArray();
            recipeConditions.forEach(c -> conds.add(CraftingHelper.serialize(c)));
            json.add("conditions", conds);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return serializer;
        }

        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }

    }
}
