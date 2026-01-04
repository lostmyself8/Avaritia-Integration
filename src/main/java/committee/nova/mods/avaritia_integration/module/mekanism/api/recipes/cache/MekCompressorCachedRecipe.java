package committee.nova.mods.avaritia_integration.module.mekanism.api.recipes.cache;

import committee.nova.mods.avaritia_integration.module.mekanism.api.recipes.MekCompressorRecipe;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.cache.OneInputCachedRecipe;
import mekanism.api.recipes.ingredients.InputIngredient;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.outputs.IOutputHandler;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MekCompressorCachedRecipe<INPUT, OUTPUT, RECIPE extends MekanismRecipe & Predicate<INPUT>> extends OneInputCachedRecipe<INPUT, OUTPUT, RECIPE> {

    /**
     * @param recipe           Recipe.
     * @param recheckAllErrors Returns {@code true} if processing should be continued even if an error is hit in order to gather all the errors. It is recommended to not
     *                         do this every tick or if there is no one viewing recipes.
     * @param inputHandler     Input handler.
     * @param outputHandler    Output handler.
     * @param inputSupplier    Supplier of the recipe's input ingredient.
     * @param outputGetter     Gets the recipe's output when given the corresponding input.
     * @param inputEmptyCheck  Checks if the input is empty.
     * @param outputEmptyCheck Checks if the output is empty (indicating something went horribly wrong).
     */
    protected MekCompressorCachedRecipe(RECIPE recipe, BooleanSupplier recheckAllErrors, IInputHandler<INPUT> inputHandler, IOutputHandler<OUTPUT> outputHandler,
                                        Supplier<? extends InputIngredient<INPUT>> inputSupplier, Function<INPUT, OUTPUT> outputGetter, Predicate<INPUT> inputEmptyCheck,
                                        Predicate<OUTPUT> outputEmptyCheck) {
        super(recipe, recheckAllErrors, inputHandler, outputHandler, inputSupplier, outputGetter, inputEmptyCheck, outputEmptyCheck);
    }

    public static MekCompressorCachedRecipe<@NotNull ItemStack, @NotNull ItemStack, MekCompressorRecipe> compressor(MekCompressorRecipe recipe,
                                                                                                                    BooleanSupplier recheckAllErrors, IInputHandler<@NotNull ItemStack> inputHandler, IOutputHandler<@NotNull ItemStack> outputHandler) {
        return new MekCompressorCachedRecipe<>(recipe, recheckAllErrors, inputHandler, outputHandler, recipe::getInput, recipe::getOutput, ItemStack::isEmpty,
                ItemStack::isEmpty);
    }
}
