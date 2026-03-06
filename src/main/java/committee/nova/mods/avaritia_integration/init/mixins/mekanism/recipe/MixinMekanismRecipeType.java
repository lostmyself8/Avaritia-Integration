package committee.nova.mods.avaritia_integration.init.mixins.mekanism.recipe;

import committee.nova.mods.avaritia_integration.module.mekanism.api.recipes.chemicals.GasStackToItemStackRecipe;
import committee.nova.mods.avaritia_integration.module.mekanism.common.recipe.MekIntegrationRecipeType;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleChemical;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleItem;
import mekanism.common.registration.impl.RecipeTypeRegistryObject;
import net.minecraft.world.item.crafting.RecipeType;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.function.Function;

@Mixin(value = MekanismRecipeType.class, remap = false)
public abstract class MixinMekanismRecipeType<RECIPE extends MekanismRecipe, INPUT_CACHE extends IInputRecipeCache> implements RecipeType<RECIPE>,
        IMekanismRecipeTypeProvider<RECIPE, INPUT_CACHE> {

//    @Final
//    @Shadow
//    public static RecipeTypeRegistryObject<ItemStackToItemStackRecipe, SingleItem<ItemStackToItemStackRecipe>> SMELTING;
//    @Shadow
//    private List<RECIPE> cachedRecipes;
//
//    @Unique
//    List<RECIPE> avaritia_Integration$recipes;
//
//    @Unique
//    RecipeManager avaritia_Integration$recipeManager;

    @Shadow
    private static <RECIPE extends MekanismRecipe, INPUT_CACHE extends IInputRecipeCache> RecipeTypeRegistryObject<RECIPE, INPUT_CACHE> register(String name,
                                                                                                                                                 Function<MekanismRecipeType<RECIPE, INPUT_CACHE>, INPUT_CACHE> inputCacheCreator) {
        return null;
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void mekmm$initRecipe(CallbackInfo ci) {
        MekIntegrationRecipeType.COLLECTING = register("collecting", recipeType -> new SingleChemical<>(recipeType, GasStackToItemStackRecipe::getInput));
        MekIntegrationRecipeType.MEK_COMPRESSING = register("mek_compressing", recipeType -> new SingleItem<>(recipeType, ItemStackToItemStackRecipe::getInput));
    }

//    @Inject(method = "getRecipes", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/crafting/RecipeManager;getAllRecipesFor(Lnet/minecraft/world/item/crafting/RecipeType;)Ljava/util/List;", shift = At.Shift.BY, ordinal = 0), locals = LocalCapture.CAPTURE_FAILSOFT)
//    public void getRecipes(@Nullable Level world, CallbackInfoReturnable<List<RECIPE>> cir, RecipeManager recipeManager) {
//        avaritia_Integration$recipes = recipeManager.getAllRecipesFor(this);
//        avaritia_Integration$recipeManager = recipeManager;
//    }
//
//    @Inject(method = "getRecipes", at = @At(value = "INVOKE", target = "Lmekanism/common/registration/impl/RecipeTypeRegistryObject;get()Ljava/lang/Object;"), cancellable = true)
//    public void mixinGetRecipes(@Nullable Level world, CallbackInfoReturnable<List<RECIPE>> cir) {
//        if ((Object) this == MekIntegrationRecipeType.MEK_COMPRESSING.get()) {
//            avaritia_Integration$recipes = new ArrayList<>(avaritia_Integration$recipes);
//            for (ICompressorRecipe compressorRecipe : avaritia_Integration$recipeManager.getAllRecipesFor(ModRecipeTypes.COMPRESSOR_RECIPE.get())) {
//                ItemStack recipeOutput = compressorRecipe.getResultItem(world.registryAccess());
//                if (!compressorRecipe.isSpecial() && !compressorRecipe.isIncomplete() && !recipeOutput.isEmpty()) {
//                    NonNullList<Ingredient> ingredients = compressorRecipe.getIngredients();
//                    int inputCount;
//                    int timeCost;
//                    ItemStackIngredient input;
//                    if (ingredients.isEmpty()) {
//                        continue;
//                    } else {
//                        IItemStackIngredientCreator ingredientCreator = IngredientCreatorAccess.item();
//                        input = ingredientCreator.from(ingredients.stream().map(ingredientCreator::from));
//                        inputCount = compressorRecipe.getInputCount();
//                        timeCost = compressorRecipe.getTimeCost();
//                    }
//                    avaritia_Integration$recipes.add((RECIPE) new MekCompressorIRecipe(compressorRecipe.getId(), input, recipeOutput, 1, 1));
//                }
//            }
//        }
//        cir.setReturnValue(cachedRecipes);
//    }

//    @Inject(method = "getRecipesUncached", at = @At("HEAD"), cancellable = true)
//    private void mixinGetRecipesUncached(RecipeManager recipeManager, RegistryAccess registryAccess, CallbackInfoReturnable<List<RECIPE>> cir) {
//        if (DatagenModLoader.isRunningDataGen()) {
//            return;
//        }
//
//        MekanismRecipeType<?, ?> type = (MekanismRecipeType<?, ?>) (Object) this;
//        List<RECIPE> recipes = new ArrayList<>();
//
//        if (type == MekIntegrationRecipeType.MEK_COMPRESSING.get()) {
//            for (ICompressorRecipe compressorRecipe : recipeManager.getAllRecipesFor(ModRecipeTypes.COMPRESSOR_RECIPE.get())) {
//                ItemStack recipeOutput = compressorRecipe.getResultItem(registryAccess);
//                if (!compressorRecipe.isSpecial() && !compressorRecipe.isIncomplete() && !recipeOutput.isEmpty()) {
//                    NonNullList<Ingredient> ingredients = compressorRecipe.getIngredients();
//                    int inputCount;
//                    int timeCost;
//                    ItemStackIngredient input;
//                    if (ingredients.isEmpty()) {
//                        continue;
//                    } else {
//                        IItemStackIngredientCreator ingredientCreator = IngredientCreatorAccess.item();
//                        input = ingredientCreator.from(ingredients.stream().map(ingredientCreator::from));
//                        inputCount = compressorRecipe.getInputCount();
//                        timeCost = compressorRecipe.getTimeCost();
//                    }
//                    recipes.add((RECIPE) new MekCompressorIRecipe(compressorRecipe.getId(), input, recipeOutput, 1, 1));
//                }
//            }
//        }
//        cir.setReturnValue(recipes);
//    }

//    @Overwrite
//    public @NotNull List<RECIPE> getRecipes(@Nullable Level world) {
//        if (world == null) {
//            //Try to get a fallback world if we are in a context that may not have one
//            //If we are on the client get the client's world, if we are on the server get the current server's world
//            if (FMLEnvironment.dist.isClient()) {
//                world = MekanismClient.tryGetClientWorld();
//            } else {
//                world = ServerLifecycleHooks.getCurrentServer().overworld();
//            }
//            if (world == null) {
//                //If we failed, then return no recipes
//                return Collections.emptyList();
//            }
//        }
//        if (cachedRecipes.isEmpty()) {
//            RecipeManager recipeManager = world.getRecipeManager();
//            //Note: This is a fresh mutable list that gets returned
//            List<RECIPE> recipes = recipeManager.getAllRecipesFor(this);
//            if ((Object) this == SMELTING.get()) {
//                //Ensure the recipes can be modified
//                recipes = new ArrayList<>(recipes);
//                for (SmeltingRecipe smeltingRecipe : recipeManager.getAllRecipesFor(RecipeType.SMELTING)) {
//                    ItemStack recipeOutput = smeltingRecipe.getResultItem(world.registryAccess());
//                    if (!smeltingRecipe.isSpecial() && !smeltingRecipe.isIncomplete() && !recipeOutput.isEmpty()) {
//                        NonNullList<Ingredient> ingredients = smeltingRecipe.getIngredients();
//                        ItemStackIngredient input;
//                        if (ingredients.isEmpty()) {
//                            //Something went wrong
//                            continue;
//                        } else {
//                            IItemStackIngredientCreator ingredientCreator = IngredientCreatorAccess.item();
//                            input = ingredientCreator.from(ingredients.stream().map(ingredientCreator::from));
//                        }
//                        recipes.add((RECIPE) new SmeltingIRecipe(smeltingRecipe.getId(), input, recipeOutput));
//                    }
//                }
//            } else if ((Object) this == MekIntegrationRecipeType.MEK_COMPRESSING.get()) {
//                for (ICompressorRecipe compressorRecipe : recipeManager.getAllRecipesFor(ModRecipeTypes.COMPRESSOR_RECIPE.get())) {
//                    ItemStack recipeOutput = compressorRecipe.getResultItem(world.registryAccess());
//                    if (!compressorRecipe.isSpecial() && !compressorRecipe.isIncomplete() && !recipeOutput.isEmpty()) {
//                        NonNullList<Ingredient> ingredients = compressorRecipe.getIngredients();
//                        int inputCount;
//                        int timeCost;
//                        ItemStackIngredient input;
//                        if (ingredients.isEmpty()) {
//                            continue;
//                        } else {
//                            IItemStackIngredientCreator ingredientCreator = IngredientCreatorAccess.item();
////                            input = ingredientCreator.from(ingredients.stream().map(ingredientCreator::from));
//                            inputCount = compressorRecipe.getInputCount();
//                            input = ingredientCreator.from(ingredients.stream().map(ingredient -> ingredientCreator.from(ingredient, inputCount)));
////                            inputCount = compressorRecipe.getInputCount();
//                            timeCost = compressorRecipe.getTimeCost();
//                        }
//                        recipes.add((RECIPE) new MekCompressorIRecipe(compressorRecipe.getId(), input, recipeOutput, inputCount, timeCost));
//                    }
//                }
//            }
//            //Make the list of cached recipes immutable and filter out any incomplete recipes
//            // as there is no reason to potentially look the partial complete piece up if
//            // the other portion of the recipe is incomplete
//            cachedRecipes = recipes.stream()
//                    .filter(recipe -> !recipe.isIncomplete())
//                    .toList();
//        }
//        return cachedRecipes;
//    }
}
