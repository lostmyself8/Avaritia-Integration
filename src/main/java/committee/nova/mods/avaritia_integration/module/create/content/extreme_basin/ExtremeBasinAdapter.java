package committee.nova.mods.avaritia_integration.module.create.content.extreme_basin;

import com.simibubi.create.AllBlockEntityTypes;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExtremeBasinAdapter extends BasinBlockEntity {
    private final ExtremeBasinBlockEntity extremeBasin;

    private ExtremeBasinAdapter(ExtremeBasinBlockEntity extremeBasin) {
        super(AllBlockEntityTypes.BASIN.get(), extremeBasin.getBlockPos(), extremeBasin.getBlockState());
        this.extremeBasin = extremeBasin;
        this.level = extremeBasin.getLevel();
    }

    public static ExtremeBasinAdapter of(ExtremeBasinBlockEntity extremeBasin) {
        return new ExtremeBasinAdapter(extremeBasin);
    }

    @Override
    public FilteringBehaviour getFilter() {
        return extremeBasin.getFiltering();
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return extremeBasin.getCapability(cap, null);
    }

    @Override
    public <T extends BlockEntityBehaviour> T getBehaviour(BehaviourType<T> type) {
        return extremeBasin.getBehaviour(type);
    }

    @Override
    public Level getLevel() {
        return extremeBasin.getLevel();
    }

    @Override
    public boolean acceptOutputs(List<ItemStack> outputItems, List<FluidStack> outputFluids, boolean simulate) {
        extremeBasin.outputInventory.allowInsertion();
        extremeBasin.outputTank.allowInsertion();
        boolean acceptOutputsInner = extremeBasin.acceptOutputsInner(outputItems, outputFluids, simulate);
        extremeBasin.outputInventory.forbidInsertion();
        extremeBasin.outputTank.forbidInsertion();
        return acceptOutputsInner;
    }
}
