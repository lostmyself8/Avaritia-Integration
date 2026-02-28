package committee.nova.mods.avaritia_integration.module.create.content.extreme_basin;

import com.simibubi.create.foundation.item.SmartInventory;
import committee.nova.mods.avaritia_integration.init.mixins.create.accessor.ItemStackHandlerAccessor;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;

public class ExtremeBasinInventory extends SmartInventory {
    private ExtremeBasinBlockEntity blockEntity;

    public boolean packagerMode;

    public ExtremeBasinInventory(int slots, ExtremeBasinBlockEntity be) {
        super(slots, be, 1024, true);
        this.blockEntity = be;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (packagerMode) // Unique stack insertion only matters for belt setups
            return inv.insertItem(slot, stack, simulate);

        int firstFreeSlot = -1;
        ItemStack existingInSlot = inv.getStackInSlot(slot);

        for (int i = 0; i < getSlots(); i++) {
            ItemStack s = inv.getStackInSlot(i);

            // Only insert if no other slot already has a stack of this item
            if (i != slot && ItemHandlerHelper.canItemStacksStack(stack, s))
                return stack;
            if (inv.getStackInSlot(i)
                    .isEmpty() && firstFreeSlot == -1)
                firstFreeSlot = i;
        }

        if (existingInSlot.isEmpty() && firstFreeSlot != slot) {
            return stack;
        }

        if (!existingInSlot.isEmpty() && !ItemHandlerHelper.canItemStacksStack(stack, existingInSlot)) {
            return stack;
        }

        int limit = getSlotLimit(slot);
        int existingCount = existingInSlot.getCount();
        int canAdd = limit - existingCount;

        if (canAdd <= 0) return stack;

        int toAdd = Math.min(stack.getCount(), canAdd);

        if (!simulate) {
            ItemStack newStack = stack.copy();
            newStack.setCount(existingCount + toAdd);
            this.setStackInSlot(slot, newStack);
        }

        if (toAdd < stack.getCount()) {
            ItemStack remainder = stack.copy();
            remainder.shrink(toAdd);
            return remainder;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public CompoundTag serializeNBT() {
        SyncedStackHandler inv = (SyncedStackHandler) this.inv;
        NonNullList<ItemStack> stacks = ((ItemStackHandlerAccessor) inv).getStacks();

        ListTag nbtTagList = new ListTag();

        for(int i = 0; i < stacks.size(); ++i) {
            if (!stacks.get(i).isEmpty()) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i);
                intItemStackSave(stacks.get(i), itemTag);
                nbtTagList.add(itemTag);
            }
        }

        CompoundTag nbt = new CompoundTag();
        nbt.put("Items", nbtTagList);
        nbt.putInt("Size", stacks.size());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        SyncedStackHandler inv = (SyncedStackHandler) this.inv;
        NonNullList<ItemStack> stacks = ((ItemStackHandlerAccessor) inv).getStacks();

        inv.setSize(nbt.contains("Size", 3) ? nbt.getInt("Size") : stacks.size());
        ListTag tagList = nbt.getList("Items", 10);

        for(int i = 0; i < tagList.size(); ++i) {
            CompoundTag itemTags = tagList.getCompound(i);
            int slot = itemTags.getInt("Slot");
            if (slot >= 0 && slot < stacks.size()) {
                inv.setStackInSlot(slot, intItemStackRead(itemTags));
            }
        }

        ((ItemStackHandlerAccessor) inv).invokeOnLoad();
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack extractItem = super.extractItem(slot, amount, simulate);
        if (!simulate && !extractItem.isEmpty())
            blockEntity.notifyChangeOfContents();
        return extractItem;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1024;
    }

    @Override
    public int getStackLimit(int slot, @NotNull ItemStack stack) {
        return 1024;
    }

    @Override
    public int getMaxStackSize() {
        return 1024;
    }

    private void intItemStackSave(ItemStack stack, CompoundTag tag) {
        CompoundTag itemTag = new CompoundTag();
        stack.save(itemTag);
        if (itemTag.contains("Count")) {
            itemTag.remove("Count");
            itemTag.putInt("Count", stack.getCount());
        }

        tag.merge(itemTag);
    }

    private ItemStack intItemStackRead(CompoundTag pCompoundTag) {
        CompoundTag tempTag = pCompoundTag.copy();
        tempTag.putByte("Count", (byte) 1);
        ItemStack itemStack = ItemStack.of(tempTag);

        if (!itemStack.isEmpty() && pCompoundTag.contains("Count", 3)) {
            itemStack.setCount(pCompoundTag.getInt("Count"));
        }
        return itemStack;
    }

}
