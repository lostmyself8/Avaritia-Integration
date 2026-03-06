package committee.nova.mods.avaritia_integration.module.mekanism.common.upgrade;

import mekanism.api.chemical.gas.IGasTank;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.inventory.IInventorySlot;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.tile.component.ITileComponent;
import mekanism.common.tile.interfaces.IRedstoneControl;
import mekanism.common.upgrade.IUpgradeData;
import net.minecraft.nbt.CompoundTag;

import java.util.Collections;
import java.util.List;

public class GasToItemUpgradeData implements IUpgradeData {

    public final boolean redstone;
    public final IRedstoneControl.RedstoneControl controlType;
    public final IEnergyContainer energyContainer;
    public final int[] progress;
    public final boolean sorting;
    public final EnergyInventorySlot energySlot;
    public final List<IInventorySlot> outputSlots;
    public final List<IGasTank> inputTanks;
    public final CompoundTag components;

    public GasToItemUpgradeData(boolean redstone, IRedstoneControl.RedstoneControl controlType,
                                   IEnergyContainer energyContainer, int operatingTicks, EnergyInventorySlot energySlot,
                                   IGasTank inputTank, IInventorySlot outputSlot, List<ITileComponent> components) {
        this(redstone, controlType, energyContainer, new int[] { operatingTicks }, energySlot, Collections.singletonList(inputTank),
                Collections.singletonList(outputSlot), false, components);
    }

    public GasToItemUpgradeData(boolean redstone, IRedstoneControl.RedstoneControl controlType,
                                   IEnergyContainer energyContainer, int[] progress, EnergyInventorySlot energySlot,
                                   List<IGasTank> inputTanks, List<IInventorySlot> outputSlots, boolean sorting, List<ITileComponent> components) {
        this.redstone = redstone;
        this.controlType = controlType;
        this.energyContainer = energyContainer;
        this.progress = progress;
        this.energySlot = energySlot;
        this.outputSlots = outputSlots;
        this.inputTanks = inputTanks;
        this.sorting = sorting;
        this.components = new CompoundTag();
        for (ITileComponent component : components) {
            component.write(this.components);
        }
    }
}
