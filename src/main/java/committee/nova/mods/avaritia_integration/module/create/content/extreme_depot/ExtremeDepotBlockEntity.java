package committee.nova.mods.avaritia_integration.module.create.content.extreme_depot;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;

public class ExtremeDepotBlockEntity extends SmartBlockEntity {
    ExtremeDepotBehaviour depotBehaviour;

    public ExtremeDepotBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(depotBehaviour = new ExtremeDepotBehaviour(this));
        depotBehaviour.addSubBehaviours(behaviours);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER)
            return depotBehaviour.getItemCapability(cap, side);
        return super.getCapability(cap, side);
    }

    public ItemStack getHeldItem() {
        return depotBehaviour.getHeldItemStack();
    }

    public void setHeldItem(ItemStack item) {
        ExtremeTransportedItemStack newStack = new ExtremeTransportedItemStack(item, 1024);
        if (depotBehaviour.heldItem != null)
            newStack.angle = depotBehaviour.heldItem.angle;
        depotBehaviour.setHeldItem(newStack);
    }

}
