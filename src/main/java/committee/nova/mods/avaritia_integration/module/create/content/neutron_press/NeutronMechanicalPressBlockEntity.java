package committee.nova.mods.avaritia_integration.module.create.content.neutron_press;

import com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class NeutronMechanicalPressBlockEntity extends MechanicalPressBlockEntity {

    public NeutronMechanicalPressBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}
