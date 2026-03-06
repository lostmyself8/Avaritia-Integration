package committee.nova.mods.avaritia_integration.module.mekanism.common.tile.factory;

import committee.nova.mods.avaritia_integration.module.mekanism.api.recipes.cache.ChemicalToItemCachedRecipe;
import committee.nova.mods.avaritia_integration.module.mekanism.api.recipes.chemicals.GasStackToItemStackRecipe;
import committee.nova.mods.avaritia_integration.module.mekanism.common.recipe.MekIntegrationRecipeType;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.inventory.IInventorySlot;
import mekanism.api.math.MathUtils;
import mekanism.api.providers.IBlockProvider;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.lookup.ISingleRecipeLookupHandler.ChemicalRecipeLookupHandler;
import mekanism.common.recipe.lookup.cache.InputRecipeCache;
import mekanism.common.util.InventoryUtils;
import mekanism.common.util.MekanismUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class TileEntityCollectingFactory extends TileEntityGasToItemMIFactory<GasStackToItemStackRecipe> implements ChemicalRecipeLookupHandler<Gas, GasStack, GasStackToItemStackRecipe> {

    private static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            RecipeError.NOT_ENOUGH_ENERGY,
            RecipeError.NOT_ENOUGH_INPUT,
            RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT
    );
    private static final Set<RecipeError> GLOBAL_ERROR_TYPES = Set.of(RecipeError.NOT_ENOUGH_ENERGY);

    public TileEntityCollectingFactory(IBlockProvider blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state, TRACKED_ERROR_TYPES, GLOBAL_ERROR_TYPES);
    }

    @Override
    protected boolean isCachedRecipeValid(@Nullable CachedRecipe<GasStackToItemStackRecipe> cached, @NotNull GasStack stack) {
        return cached != null && cached.getRecipe().getInput().testType(stack);
    }

    @Override
    protected @Nullable GasStackToItemStackRecipe findRecipe(int process, @NotNull GasStack fallbackInput, @NotNull IInventorySlot outputSlots) {
        ItemStack output = outputSlots.getStack();
        return getRecipeType().getInputCache().findTypeBasedRecipe(level, fallbackInput, recipe -> InventoryUtils.areItemsStackable(recipe.getOutput(fallbackInput), output));
    }

    @Override
    public boolean isChemicalValidForTank(@NotNull GasStack stack) {
        return containsRecipe(stack);
    }

    @Override
    public boolean isValidInputChemical(@NotNull GasStack stack) {
        return containsRecipe(stack);
    }

    @Override
    protected int getNeededInput(GasStackToItemStackRecipe recipe, GasStack inputStack) {
        return MathUtils.clampToInt(recipe.getInput().getNeededAmount(inputStack));
    }

    @Override
    public @NotNull IMekanismRecipeTypeProvider<GasStackToItemStackRecipe, InputRecipeCache.SingleChemical<Gas, GasStack, GasStackToItemStackRecipe>> getRecipeType() {
        return MekIntegrationRecipeType.COLLECTING;
    }

    @Override
    public @Nullable GasStackToItemStackRecipe getRecipe(int cacheIndex) {
        return findFirstRecipe(gasInputHandlers[cacheIndex]);
    }

    @Override
    public @NotNull CachedRecipe<GasStackToItemStackRecipe> createNewCachedRecipe(@NotNull GasStackToItemStackRecipe recipe, int cacheIndex) {
        return ChemicalToItemCachedRecipe.gasToItem(recipe, recheckAllRecipeErrors[cacheIndex], gasInputHandlers[cacheIndex], itemOutputHandlers[cacheIndex])
                .setErrorsChanged(errors -> errorTracker.onErrorsChanged(errors, cacheIndex))
                .setCanHolderFunction(() -> MekanismUtils.canFunction(this))
                .setActive(active -> setActiveState(active, cacheIndex))
                .setEnergyRequirements(energyContainer::getEnergyPerTick, energyContainer)
                .setRequiredTicks(this::getTicksRequired)
                .setOnFinish(this::markForSave)
                .setOperatingTicksChanged(operatingTicks -> progress[cacheIndex] = operatingTicks);
    }
}
