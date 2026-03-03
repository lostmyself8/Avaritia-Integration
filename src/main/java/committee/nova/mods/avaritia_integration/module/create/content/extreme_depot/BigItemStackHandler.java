package committee.nova.mods.avaritia_integration.module.create.content.extreme_depot;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

public class BigItemStackHandler extends ItemStackHandler {
    public BigItemStackHandler(int i) {
        super(i);
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1024;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        validateSlotIndex(slot);
        ItemStack existing = this.stacks.get(slot);

        int limit = getSlotLimit(slot);

        if (!existing.isEmpty()) {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
                return stack;
            limit -= existing.getCount();
        }

        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.getCount() > limit;

        if (!simulate) {
            if (existing.isEmpty()) {
                this.stacks.set(slot, reachedLimit
                        ? ItemHandlerHelper.copyStackWithSize(stack, limit)
                        : stack.copy());
            } else {
                existing.grow(reachedLimit ? limit : stack.getCount());
            }
            onContentsChanged(slot);
        }

        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
    }

    @Override
    public CompoundTag serializeNBT() {
        ListTag nbtTagList = new ListTag();
        for (int i = 0; i < stacks.size(); i++) {
            ItemStack stack = stacks.get(i);
            if (!stack.isEmpty()) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i);

                intItemStackSave(stack, itemTag);

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
        int size = nbt.contains("Size", 3) ? nbt.getInt("Size") : stacks.size();
        this.setSize(size);

        ListTag tagList = nbt.getList("Items", 10);
        for (int i = 0; i < tagList.size(); i++) {
            CompoundTag itemTag = tagList.getCompound(i);
            int slot = itemTag.getInt("Slot");

            if (slot >= 0 && slot < stacks.size()) {
                stacks.set(slot, intItemStackRead(itemTag));
            }
        }
        onContentsChanged(0);
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
