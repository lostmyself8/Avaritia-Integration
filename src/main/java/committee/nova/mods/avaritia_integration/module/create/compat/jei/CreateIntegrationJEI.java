package committee.nova.mods.avaritia_integration.module.create.compat.jei;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.Create;
import com.simibubi.create.compat.jei.CreateJEI;
import com.simibubi.create.compat.jei.DoubleItemIcon;
import com.simibubi.create.compat.jei.EmptyBackground;
import com.simibubi.create.compat.jei.ItemIcon;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.kinetics.fan.processing.HauntingRecipe;
import com.simibubi.create.content.kinetics.fan.processing.SplashingRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.infrastructure.config.AllConfigs;
import com.simibubi.create.infrastructure.config.CRecipes;
import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.create.compat.CompatInfo;
import committee.nova.mods.avaritia_integration.module.create.compat.cca.liquid_burning.LiquidBurningRecipe;
import committee.nova.mods.avaritia_integration.module.create.compat.jei.category.ExtremeMixingCategory;
import committee.nova.mods.avaritia_integration.module.create.compat.jei.category.LiquidBurningCategory;
import committee.nova.mods.avaritia_integration.module.create.content.recipe.ExtremeBasinRecipe;
import committee.nova.mods.avaritia_integration.module.create.registry.CreateIntegrationBlocks;
import committee.nova.mods.avaritia_integration.module.create.registry.CreateIntegrationRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;
import net.createmod.catnip.config.ConfigBase;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@JeiPlugin
@ParametersAreNonnullByDefault
public class CreateIntegrationJEI implements IModPlugin {
    private static final ResourceLocation ID = AvaritiaIntegration.rl("jei_plugin");

    private final List<CreateRecipeCategory<?>> allCategories = new ArrayList<>();
    private IIngredientManager ingredientManager;

    public static IJeiRuntime runtime;

    private void loadCategories() {
        allCategories.clear();

        CreateRecipeCategory<?>
                extremeMixing = builder(ExtremeBasinRecipe.class)
                .addTypedRecipes(CreateIntegrationRecipeTypes.EXTREME_MIXING)
                .catalyst(CreateIntegrationBlocks.MATRIX_MECHANICAL_MIXER::get)
                .catalyst(CreateIntegrationBlocks.EXTREME_BASIN::get)
                .catalyst(AllBlocks.MECHANICAL_MIXER::get)
                .catalyst(AllBlocks.BASIN::get)
                .catalyst(CreateIntegrationBlocks.EXTREME_BLAZE_BURNER::get)
                .doubleItemIcon(AllBlocks.MECHANICAL_MIXER.get(), CreateIntegrationBlocks.EXTREME_BLAZE_BURNER.get())
                .emptyBackground(177, 103)
                .build("extreme_mixing", ExtremeMixingCategory::standard);

        if (CompatInfo.isCCALoaded()) {
            CreateRecipeCategory<?> liquidBurning = builder(LiquidBurningRecipe.class)
                    .addTypedRecipes(CreateIntegrationRecipeTypes.LIQUID_BURNING)
                    .catalyst(CreateIntegrationBlocks.EXTREME_BLAZE_BURNER::get)
                    .itemIcon(CreateIntegrationBlocks.EXTREME_BLAZE_BURNER.get())
                    .emptyBackground(177, 53)
                    .build("liquid_burning", LiquidBurningCategory::new);
        }
    }

    private <T extends Recipe<?>> CategoryBuilder<T> builder(Class<? extends T> recipeClass) {
        return new CategoryBuilder<>(recipeClass);
    }

    @Override
    @Nonnull
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        loadCategories();
        registration.addRecipeCategories(allCategories.toArray(IRecipeCategory[]::new));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ingredientManager = registration.getIngredientManager();

        allCategories.forEach(c -> c.registerRecipes(registration));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        allCategories.forEach(c -> c.registerCatalysts(registration));

        addRecipeCatalyst(registration, CreateIntegrationBlocks.MATRIX_MECHANICAL_MIXER.asItem(), Create.asResource("mixing"), BasinRecipe.class);
        addRecipeCatalyst(registration, CreateIntegrationBlocks.MATRIX_MECHANICAL_MIXER.asItem(), Create.asResource("automatic_shapeless"), BasinRecipe.class);
        addRecipeCatalyst(registration, CreateIntegrationBlocks.MATRIX_MECHANICAL_MIXER.asItem(), Create.asResource("automatic_brewing"), BasinRecipe.class);

        addRecipeCatalyst(registration, CreateIntegrationBlocks.NEUTRON_MECHANICAL_PRESS.asItem(), Create.asResource("pressing"), PressingRecipe.class);
        addRecipeCatalyst(registration, CreateIntegrationBlocks.NEUTRON_MECHANICAL_PRESS.asItem(), Create.asResource("packing"), BasinRecipe.class);
        addRecipeCatalyst(registration, CreateIntegrationBlocks.NEUTRON_MECHANICAL_PRESS.asItem(), Create.asResource("automatic_packing"), BasinRecipe.class);

        addRecipeCatalyst(registration, CreateIntegrationBlocks.EXTREME_BASIN.asItem(), Create.asResource("mixing"), BasinRecipe.class);
        addRecipeCatalyst(registration, CreateIntegrationBlocks.EXTREME_BASIN.asItem(), Create.asResource("automatic_shapeless"), BasinRecipe.class);
        addRecipeCatalyst(registration, CreateIntegrationBlocks.EXTREME_BASIN.asItem(), Create.asResource("automatic_brewing"), BasinRecipe.class);
        addRecipeCatalyst(registration, CreateIntegrationBlocks.EXTREME_BASIN.asItem(), Create.asResource("packing"), BasinRecipe.class);
        addRecipeCatalyst(registration, CreateIntegrationBlocks.EXTREME_BASIN.asItem(), Create.asResource("automatic_packing"), BasinRecipe.class);

        addRecipeCatalyst(registration, CreateIntegrationBlocks.EXTREME_ENCASED_FAN.asItem(), Create.asResource("fan_washing"), SplashingRecipe.class);
        addRecipeCatalyst(registration, CreateIntegrationBlocks.EXTREME_ENCASED_FAN.asItem(), Create.asResource("fan_haunting"), HauntingRecipe.class);
        addRecipeCatalyst(registration, CreateIntegrationBlocks.EXTREME_ENCASED_FAN.asItem(), Create.asResource("fan_smoking"), SmokingRecipe.class);
        addRecipeCatalyst(registration, CreateIntegrationBlocks.EXTREME_ENCASED_FAN.asItem(), Create.asResource("fan_blasting"), AbstractCookingRecipe.class);

        addRecipeCatalyst(registration, CreateIntegrationBlocks.EXTREME_CRUSHING_WHEEL.asItem(), AllRecipeTypes.CRUSHING.getId());
        addRecipeCatalyst(registration, CreateIntegrationBlocks.EXTREME_DEPOT.asItem(), AllRecipeTypes.DEPLOYING.getId());
    }

    private void addRecipeCatalyst(IRecipeCatalystRegistration registration, ItemLike item, ResourceLocation... recipeId) {
        for (ResourceLocation id : recipeId) {
            registration.getJeiHelpers().getRecipeType(id).ifPresent(type -> {
                registration.addRecipeCatalyst(
                        item,type
                );
            });
        }
    }

    private <T extends Recipe<?>> void addRecipeCatalyst(IRecipeCatalystRegistration registration, ItemLike item, ResourceLocation recipeId, Class<? extends T> recipeClass) {
        registration.getJeiHelpers().getRecipeType(recipeId, recipeClass).ifPresent(type -> {
            registration.addRecipeCatalyst(
                    item,type
            );
        });
    }

    private class CategoryBuilder<T extends Recipe<?>> {
        private final Class<? extends T> recipeClass;
        private Predicate<CRecipes> predicate = cRecipes -> true;

        private IDrawable background;
        private IDrawable icon;

        private final List<Consumer<List<T>>> recipeListConsumers = new ArrayList<>();
        private final List<Supplier<? extends ItemStack>> catalysts = new ArrayList<>();

        public CategoryBuilder(Class<? extends T> recipeClass) {
            this.recipeClass = recipeClass;
        }

        public CategoryBuilder<T> enableIf(Predicate<CRecipes> predicate) {
            this.predicate = predicate;
            return this;
        }

        public CategoryBuilder<T> enableWhen(Function<CRecipes, ConfigBase.ConfigBool> configValue) {
            predicate = c -> configValue.apply(c).get();
            return this;
        }

        public CategoryBuilder<T> addRecipeListConsumer(Consumer<List<T>> consumer) {
            recipeListConsumers.add(consumer);
            return this;
        }

        public CategoryBuilder<T> addRecipes(Supplier<Collection<? extends T>> collection) {
            return addRecipeListConsumer(recipes -> recipes.addAll(collection.get()));
        }

        @SuppressWarnings("unchecked")
        public CategoryBuilder<T> addAllRecipesIf(Predicate<Recipe<?>> pred) {
            return addRecipeListConsumer(recipes -> CreateJEI.consumeAllRecipes(recipe -> {
                if (pred.test(recipe))
                    recipes.add((T) recipe);
            }));
        }

        public CategoryBuilder<T> addAllRecipesIf(Predicate<Recipe<?>> pred, Function<Recipe<?>, T> converter) {
            return addRecipeListConsumer(recipes -> CreateJEI.consumeAllRecipes(recipe -> {
                if (pred.test(recipe)) {
                    recipes.add(converter.apply(recipe));
                }
            }));
        }

        public CategoryBuilder<T> addTypedRecipes(IRecipeTypeInfo recipeTypeEntry) {
            return addTypedRecipes(recipeTypeEntry::getType);
        }

        public CategoryBuilder<T> addTypedRecipes(Supplier<RecipeType<? extends T>> recipeType) {
            return addRecipeListConsumer(recipes -> CreateJEI.<T>consumeTypedRecipes(recipes::add, recipeType.get()));
        }

        public CategoryBuilder<T> addTypedRecipes(Supplier<RecipeType<? extends T>> recipeType, Function<Recipe<?>, T> converter) {
            return addRecipeListConsumer(recipes -> CreateJEI.<T>consumeTypedRecipes(recipe -> recipes.add(converter.apply(recipe)), recipeType.get()));
        }

        public CategoryBuilder<T> addTypedRecipesIf(Supplier<RecipeType<? extends T>> recipeType, Predicate<Recipe<?>> pred) {
            return addRecipeListConsumer(recipes -> CreateJEI.<T>consumeTypedRecipes(recipe -> {
                if (pred.test(recipe)) {
                    recipes.add(recipe);
                }
            }, recipeType.get()));
        }

        public CategoryBuilder<T> addTypedRecipesExcluding(Supplier<RecipeType<? extends T>> recipeType,
                                                           Supplier<RecipeType<? extends T>> excluded) {
            return addRecipeListConsumer(recipes -> {
                List<Recipe<?>> excludedRecipes = CreateJEI.getTypedRecipes(excluded.get());
                CreateJEI.<T>consumeTypedRecipes(recipe -> {
                    for (Recipe<?> excludedRecipe : excludedRecipes) {
                        if (CreateJEI.doInputsMatch(recipe, excludedRecipe)) {
                            return;
                        }
                    }
                    recipes.add(recipe);
                }, recipeType.get());
            });
        }

        public CategoryBuilder<T> removeRecipes(Supplier<RecipeType<? extends T>> recipeType) {
            return addRecipeListConsumer(recipes -> {
                List<Recipe<?>> excludedRecipes = CreateJEI.getTypedRecipes(recipeType.get());
                recipes.removeIf(recipe -> {
                    for (Recipe<?> excludedRecipe : excludedRecipes)
                        if (CreateJEI.doInputsMatch(recipe, excludedRecipe) && CreateJEI.doOutputsMatch(recipe, excludedRecipe))
                            return true;
                    return false;
                });
            });
        }

        public CategoryBuilder<T> removeNonAutomation() {
            return addRecipeListConsumer(recipes -> recipes.removeIf(AllRecipeTypes.CAN_BE_AUTOMATED.negate()));
        }

        public CategoryBuilder<T> catalystStack(Supplier<ItemStack> supplier) {
            catalysts.add(supplier);
            return this;
        }

        public CategoryBuilder<T> catalyst(Supplier<ItemLike> supplier) {
            return catalystStack(() -> new ItemStack(supplier.get()
                    .asItem()));
        }

        public CategoryBuilder<T> icon(IDrawable icon) {
            this.icon = icon;
            return this;
        }

        public CategoryBuilder<T> itemIcon(ItemLike item) {
            icon(new ItemIcon(() -> new ItemStack(item)));
            return this;
        }

        public CategoryBuilder<T> doubleItemIcon(ItemLike item1, ItemLike item2) {
            icon(new DoubleItemIcon(() -> new ItemStack(item1), () -> new ItemStack(item2)));
            return this;
        }

        public CategoryBuilder<T> background(IDrawable background) {
            this.background = background;
            return this;
        }

        public CategoryBuilder<T> emptyBackground(int width, int height) {
            background(new EmptyBackground(width, height));
            return this;
        }

        public CreateRecipeCategory<T> build(String name, CreateRecipeCategory.Factory<T> factory) {
            Supplier<List<T>> recipesSupplier;
            if (predicate.test(AllConfigs.server().recipes)) {
                recipesSupplier = () -> {
                    List<T> recipes = new ArrayList<>();
                    for (Consumer<List<T>> consumer : recipeListConsumers)
                        consumer.accept(recipes);
                    return recipes;
                };
            } else {
                recipesSupplier = () -> Collections.emptyList();
            }

            CreateRecipeCategory.Info<T> info = new CreateRecipeCategory.Info<>(
                    new mezz.jei.api.recipe.RecipeType<>(AvaritiaIntegration.rl(name), recipeClass),
                    Component.translatable(AvaritiaIntegration.MOD_ID + ".recipe." + name), background, icon, recipesSupplier, catalysts);
            CreateRecipeCategory<T> category = factory.create(info);
            allCategories.add(category);
            return category;
        }
    }
}
