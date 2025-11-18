package committee.nova.mods.avaritia_integration.module.mekanism.common.tile;

import committee.nova.mods.avaritia_integration.module.mekanism.common.registry.MekIntegrationBlocks;
import mekanism.api.math.FloatingLong;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityInfinityAdvancedSolarGenerator extends TileEntityAvaritiaAdvancedSolarGenerator {

    public TileEntityInfinityAdvancedSolarGenerator(BlockPos pos, BlockState state) {
        super(MekIntegrationBlocks.INFINITY_ADVANCED_SOLAR_GENERATOR, pos, state, () -> FloatingLong.MAX_VALUE);
    }

    @Override
    protected FloatingLong getConfiguredMax() {
        return FloatingLong.MAX_VALUE;
    }
}
