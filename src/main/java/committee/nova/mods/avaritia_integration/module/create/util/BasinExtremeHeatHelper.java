package committee.nova.mods.avaritia_integration.module.create.util;

import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import committee.nova.mods.avaritia_integration.module.create.content.extreme_burner.ExtremeBlazeBurnerBlock;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BasinExtremeHeatHelper {
    @Nullable
    public static ExtremeBlazeBurnerBlock.ExtremeHeatLevel getHeat(BasinBlockEntity basin) {
        Level level = basin.getLevel();
        if (level == null) return null;

        BlockState stateBelow = level.getBlockState(basin.getBlockPos().below(1));

        if (stateBelow.hasProperty(ExtremeBlazeBurnerBlock.EXTREME_HEAT_LEVEL)) {
            return stateBelow.getValue(ExtremeBlazeBurnerBlock.EXTREME_HEAT_LEVEL);
        }
        return null;
    }
}
