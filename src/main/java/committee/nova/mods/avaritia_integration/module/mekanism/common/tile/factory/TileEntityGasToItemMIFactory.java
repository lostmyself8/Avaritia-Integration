package committee.nova.mods.avaritia_integration.module.mekanism.common.tile.factory;

import committee.nova.mods.avaritia_integration.module.mekanism.common.upgrade.GasToItemUpgradeData;
import mekanism.api.IContentsListener;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasTank;
import mekanism.api.inventory.IInventorySlot;
import mekanism.api.providers.IBlockProvider;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.inputs.InputHelper;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.api.recipes.outputs.OutputHelper;
import mekanism.common.CommonWorldTickHandler;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.inventory.slot.OutputInventorySlot;
import mekanism.common.inventory.warning.WarningTracker.WarningType;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.tile.component.ITileComponent;
import mekanism.common.tile.component.config.ConfigInfo;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.tile.component.config.slot.ChemicalSlotInfo.GasSlotInfo;
import mekanism.common.tile.component.config.slot.InventorySlotInfo;
import mekanism.common.upgrade.IUpgradeData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class TileEntityGasToItemMIFactory<RECIPE extends MekanismRecipe> extends TileEntityMIFactory<RECIPE>{

    private static final long MAX_CHEMICAL = 10_000;

    protected IGasTank[] inputTank;
    protected OutputInventorySlot[] outputSlot;

    protected GasToItemProcessInfo[] processInfoSlots;

    public final List<IGasTank> inputGasTanks;

    public TileEntityGasToItemMIFactory(IBlockProvider blockProvider, BlockPos pos, BlockState state, List<RecipeError> errorTypes, Set<RecipeError> globalErrorTypes) {
        super(blockProvider, pos, state, errorTypes, globalErrorTypes);
        inputGasTanks = new ArrayList<>();

        processInfoSlots = new GasToItemProcessInfo[tier.processes];
        for (int i = 0; i < tier.processes; i++) {
            processInfoSlots[i] = new GasToItemProcessInfo(i, inputTank[i], outputSlot[i]);
        }

        for (GasToItemProcessInfo info : processInfoSlots) {
            inputGasTanks.add(info.inputTank());
            outputItemSlots.add(info.outputSlot());
        }

        configComponent.addSupported(TransmissionType.GAS);

        ConfigInfo itemConfig = configComponent.getConfig(TransmissionType.ITEM);
        if (itemConfig != null) {
            itemConfig.addSlotInfo(DataType.OUTPUT, new InventorySlotInfo(false, true, outputSlot));
            itemConfig.addSlotInfo(DataType.ENERGY, new InventorySlotInfo(true, true, energySlot));
        }

        ConfigInfo gasConfig = configComponent.getConfig(TransmissionType.GAS);
        if (gasConfig != null) {
            gasConfig.addSlotInfo(DataType.INPUT, new GasSlotInfo(true, false, inputGasTanks));
        }
    }

    @Override
    protected void addGasTanks(ChemicalTankHelper<Gas, GasStack, IGasTank> builder, IContentsListener listener, IContentsListener updateSortingListener) {
        inputTank = new IGasTank[tier.processes];
        gasInputHandlers = new IInputHandler[tier.processes];
        processInfoSlots = new GasToItemProcessInfo[tier.processes];
        for (int i = 0; i < tier.processes; i++) {
            int index = i;
            inputTank[i] = ChemicalTankBuilder.GAS.input(MAX_CHEMICAL * tier.processes, gas -> isValidInputChemical(gas.getStack(1)),
                    stack -> isChemicalValidForTank(stack.getStack(1)) && inputProducesOutput(index, stack.getStack(1), outputSlot[index], false),
                    recipeCacheLookupMonitors[index]);
            builder.addTank(inputTank[i]);
            gasInputHandlers[i] = InputHelper.getInputHandler(inputTank[i], CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_INPUT);
        }
    }

    @Override
    protected void addSlots(InventorySlotHelper builder, IContentsListener listener, IContentsListener updateSortingListener) {
        outputSlot = new OutputInventorySlot[tier.processes];
        itemOutputHandlers = new IOutputHandler[tier.processes];
        for (int i = 0; i < tier.processes; i++) {
            outputSlot[i] = OutputInventorySlot.at(recipeCacheLookupMonitors[i], getXPos(i), 70);
            int index = i;
            builder.addSlot(outputSlot[i]).tracksWarnings(slot -> slot.warning(WarningType.NO_SPACE_IN_OUTPUT, getWarningCheck(RecipeError.NOT_ENOUGH_OUTPUT_SPACE, index)));
            itemOutputHandlers[i] = OutputHelper.getOutputHandler(outputSlot[i], RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
        }
    }

    public boolean inputProducesOutput(int process, @NotNull GasStack fallbackInput, @NotNull IInventorySlot outputTank, boolean updateCache) {
        return outputTank.isEmpty() || getRecipeForInput(process, fallbackInput, outputTank, updateCache) != null;
    }

    @Contract("null, _ -> false")
    protected abstract boolean isCachedRecipeValid(@Nullable CachedRecipe<RECIPE> cached, @NotNull GasStack stack);

    @Nullable
    protected RECIPE getRecipeForInput(int process, @NotNull GasStack fallbackInput, @NotNull IInventorySlot outputTank, boolean updateCache) {
        if (!CommonWorldTickHandler.flushTagAndRecipeCaches) {
            // If our recipe caches are valid, grab our cached recipe and see if it is still valid
            CachedRecipe<RECIPE> cached = getCachedRecipe(process);
            if (isCachedRecipeValid(cached, fallbackInput)) {
                // Our input matches the recipe we have cached for this slot
                return cached.getRecipe();
            }
        }
        // If there is no cached item input, or it doesn't match our fallback then it is an out of date cache, so we
        // ignore the fact that we have a cache
        RECIPE foundRecipe = findRecipe(process, fallbackInput, outputTank);
        if (foundRecipe == null) {
            // We could not find any valid recipe for the given item that matches the items in the current output slots
            return null;
        }
        if (updateCache) {
            // If we want to update the cache, then create a new cache with the recipe we found and update the cache
            recipeCacheLookupMonitors[process].updateCachedRecipe(foundRecipe);
        }
        return foundRecipe;
    }

    @Nullable
    protected abstract RECIPE findRecipe(int process, @NotNull GasStack fallbackInput, @NotNull IInventorySlot outputSlots);

    public abstract boolean isChemicalValidForTank(@NotNull GasStack stack);

    /**
     * Like isItemValidForSlot makes no assumptions about current stored types
     */
    public abstract boolean isValidInputChemical(@NotNull GasStack stack);

    protected abstract int getNeededInput(RECIPE recipe, GasStack inputStack);

    @Override
    public void parseUpgradeData(@NotNull IUpgradeData upgradeData) {
        if (upgradeData instanceof GasToItemUpgradeData data) {
            redstone = data.redstone;
            setControlType(data.controlType);
            getEnergyContainer().setEnergy(data.energyContainer.getEnergy());
            sorting = data.sorting;
            energySlot.deserializeNBT(data.energySlot.serializeNBT());
            System.arraycopy(data.progress, 0, progress, 0, data.progress.length);
            for (int i = 0; i < data.outputSlots.size(); i++) {
                // Copy the stack using NBT so that if it is not actually valid due to a reload we don't crash
                outputItemSlots.get(i).deserializeNBT(data.outputSlots.get(i).serializeNBT());
            }
            for (int i = 0; i < data.inputTanks.size(); i++) {
                inputGasTanks.get(i).setStack(data.inputTanks.get(i).getStack());
            }
            for (ITileComponent component : getComponents()) {
                component.read(data.components);
            }
        } else {
            super.parseUpgradeData(upgradeData);
        }
    }

    public record GasToItemProcessInfo(int process, @NotNull IGasTank inputTank, @NotNull IInventorySlot outputSlot) {}
}
