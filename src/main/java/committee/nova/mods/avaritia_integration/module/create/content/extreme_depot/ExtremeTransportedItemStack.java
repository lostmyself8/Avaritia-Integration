package committee.nova.mods.avaritia_integration.module.create.content.extreme_depot;

import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.kinetics.fan.processing.AllFanProcessingTypes;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ExtremeTransportedItemStack extends TransportedItemStack implements Comparable<TransportedItemStack> {
    public int maxStackSize;

    public ExtremeTransportedItemStack(ItemStack stack, int maxStackSize) {
        super(stack);
        this.maxStackSize = maxStackSize;
    }

    public ExtremeTransportedItemStack(TransportedItemStack stack, int maxStackSize) {
        super(stack.stack.copy());
        this.maxStackSize = maxStackSize;
    }

    @Override
    public int compareTo(TransportedItemStack o) {
        return Float.compare(o.beltPosition, beltPosition);
    }

    public ExtremeTransportedItemStack getSimilar() {
        ExtremeTransportedItemStack copy = new ExtremeTransportedItemStack(stack.copy(), maxStackSize);
        copy.beltPosition = beltPosition;
        copy.insertedAt = insertedAt;
        copy.insertedFrom = insertedFrom;
        copy.prevBeltPosition = prevBeltPosition;
        copy.prevSideOffset = prevSideOffset;
        copy.processedBy = processedBy;
        copy.processingTime = processingTime;
        return copy;
    }

    public ExtremeTransportedItemStack copy() {
        ExtremeTransportedItemStack copy = getSimilar();
        copy.angle = angle;
        copy.sideOffset = sideOffset;
        return copy;
    }

    //TODO 重写序列化
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.put("Item", stack.serializeNBT());
        nbt.putInt("MaxStackSize", maxStackSize);
        nbt.putFloat("Pos", beltPosition);
        nbt.putFloat("PrevPos", prevBeltPosition);
        nbt.putFloat("Offset", sideOffset);
        nbt.putFloat("PrevOffset", prevSideOffset);
        nbt.putInt("InSegment", insertedAt);
        nbt.putInt("Angle", angle);
        nbt.putInt("InDirection", insertedFrom.get3DDataValue());

        if (processedBy != null) {
            ResourceLocation key = CreateBuiltInRegistries.FAN_PROCESSING_TYPE.getKey(processedBy);
            if (key == null)
                throw new IllegalArgumentException("Could not get id for FanProcessingType " + processedBy + "!");

            nbt.putString("FanProcessingType", key.toString());
            nbt.putInt("FanProcessingTime", processingTime);
        }

        if (locked)
            nbt.putBoolean("Locked", locked);
        if (lockedExternally)
            nbt.putBoolean("LockedExternally", lockedExternally);
        return nbt;
    }

    public static ExtremeTransportedItemStack read(CompoundTag nbt) {
        ExtremeTransportedItemStack stack = new ExtremeTransportedItemStack(ItemStack.of(nbt.getCompound("Item")), nbt.getInt("MaxStackSize"));
        stack.beltPosition = nbt.getFloat("Pos");
        stack.prevBeltPosition = nbt.getFloat("PrevPos");
        stack.sideOffset = nbt.getFloat("Offset");
        stack.prevSideOffset = nbt.getFloat("PrevOffset");
        stack.insertedAt = nbt.getInt("InSegment");
        stack.angle = nbt.getInt("Angle");
        stack.insertedFrom = Direction.from3DDataValue(nbt.getInt("InDirection"));
        stack.locked = nbt.getBoolean("Locked");
        stack.lockedExternally = nbt.getBoolean("LockedExternally");

        if (nbt.contains("FanProcessingType")) {
            stack.processedBy = AllFanProcessingTypes.parseLegacy(nbt.getString("FanProcessingType"));
            stack.processingTime = nbt.getInt("FanProcessingTime");
        }

        return stack;
    }
}
