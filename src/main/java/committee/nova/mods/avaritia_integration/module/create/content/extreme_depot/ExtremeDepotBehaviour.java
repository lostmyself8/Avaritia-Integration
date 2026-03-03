package committee.nova.mods.avaritia_integration.module.create.content.extreme_depot;

import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.kinetics.belt.BeltHelper;
import com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour;
import com.simibubi.create.content.kinetics.belt.behaviour.DirectBeltInputBehaviour;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.logistics.funnel.AbstractFunnelBlock;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.item.ItemHelper;
import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ExtremeDepotBehaviour extends BlockEntityBehaviour {

    public static final BehaviourType<ExtremeDepotBehaviour> TYPE = new BehaviourType<>();

    TransportedItemStack heldItem;
    List<TransportedItemStack> incoming;
    BigItemStackHandler processingOutputBuffer;
    ExtremeDepotItemHandler itemHandler;
    LazyOptional<ExtremeDepotItemHandler> lazyItemHandler;
    TransportedItemStackHandlerBehaviour transportedHandler;
    Supplier<Integer> maxStackSize;
    Supplier<Boolean> canAcceptItems;
    Predicate<Direction> canFunnelsPullFrom;
    Consumer<ItemStack> onHeldInserted;
    Predicate<ItemStack> acceptedItems;
    boolean allowMerge;

    public ExtremeDepotBehaviour(SmartBlockEntity be) {
        super(be);
        maxStackSize = () -> 1024;
        canAcceptItems = () -> true;
        canFunnelsPullFrom = $ -> true;
        acceptedItems = $ -> true;
        onHeldInserted = $ -> {
        };
        incoming = new ArrayList<>();
        itemHandler = new ExtremeDepotItemHandler(this);
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        processingOutputBuffer = new BigItemStackHandler(8) {
            protected void onContentsChanged(int slot) {
                be.notifyUpdate();
            }
        };
    }

    public void enableMerging() {
        allowMerge = true;
    }

    public ExtremeDepotBehaviour withCallback(Consumer<ItemStack> changeListener) {
        onHeldInserted = changeListener;
        return this;
    }

    public ExtremeDepotBehaviour onlyAccepts(Predicate<ItemStack> filter) {
        acceptedItems = filter;
        return this;
    }

    @Override
    public void tick() {
        super.tick();

        Level world = blockEntity.getLevel();

        for (Iterator<TransportedItemStack> iterator = incoming.iterator(); iterator.hasNext(); ) {
            TransportedItemStack ts = iterator.next();
            if (!tick(ts))
                continue;
            if (world.isClientSide && !blockEntity.isVirtual())
                continue;
            if (heldItem == null) {
                heldItem = ts;
            } else {
                if (!ItemHelper.canItemStackAmountsStack(heldItem.stack, ts.stack)) {
//                    Vec3 vec = VecHelper.getCenterOf(blockEntity.getBlockPos());
//                    Containers.dropItemStack(blockEntity.getLevel(), vec.x, vec.y + .5f, vec.z, ts.stack);
                } else {
                    int newCount = heldItem.stack.getCount() + ts.stack.getCount();
                    heldItem.stack.setCount(newCount);
//                    heldItem.stack.grow(ts.stack.getCount());
                }
            }
            iterator.remove();
            blockEntity.notifyUpdate();
        }

        if (heldItem == null)
            return;
        if (!tick(heldItem))
            return;

        BlockPos pos = blockEntity.getBlockPos();

        if (world.isClientSide)
            return;
        if (handleBeltFunnelOutput())
            return;

        BeltProcessingBehaviour processingBehaviour =
                BlockEntityBehaviour.get(world, pos.above(2), BeltProcessingBehaviour.TYPE);
        if (processingBehaviour == null)
            return;
        if (!heldItem.locked && BeltProcessingBehaviour.isBlocked(world, pos))
            return;

        ItemStack previousItem = heldItem.stack;
        boolean wasLocked = heldItem.locked;
        BeltProcessingBehaviour.ProcessingResult result = wasLocked ? processingBehaviour.handleHeldItem(heldItem, transportedHandler)
                : processingBehaviour.handleReceivedItem(heldItem, transportedHandler);
        if (heldItem == null || result == BeltProcessingBehaviour.ProcessingResult.REMOVE) {
            heldItem = null;
            blockEntity.sendData();
            return;
        }

        heldItem.locked = result == BeltProcessingBehaviour.ProcessingResult.HOLD;
        if (heldItem.locked != wasLocked || !previousItem.equals(heldItem.stack, false))
            blockEntity.sendData();
    }

    protected boolean tick(TransportedItemStack heldItem) {
        heldItem.prevBeltPosition = heldItem.beltPosition;
        heldItem.prevSideOffset = heldItem.sideOffset;
        float diff = .5f - heldItem.beltPosition;
        if (diff > 1 / 512f) {
            if (diff > 1 / 32f && !BeltHelper.isItemUpright(heldItem.stack))
                heldItem.angle += 1;
            heldItem.beltPosition += diff / 4f;
        }
        return diff < 1 / 16f;
    }

    private boolean handleBeltFunnelOutput() {
        BlockState funnel = getWorld().getBlockState(getPos().above());
        Direction funnelFacing = AbstractFunnelBlock.getFunnelFacing(funnel);
        if (funnelFacing == null || !canFunnelsPullFrom.test(funnelFacing.getOpposite()))
            return false;

        for (int slot = 0; slot < processingOutputBuffer.getSlots(); slot++) {
            ItemStack previousItem = processingOutputBuffer.getStackInSlot(slot);
            if (previousItem.isEmpty())
                continue;
            ItemStack afterInsert = blockEntity.getBehaviour(DirectBeltInputBehaviour.TYPE)
                    .tryExportingToBeltFunnel(previousItem, null, false);
            if (afterInsert == null)
                return false;
            if (previousItem.getCount() != afterInsert.getCount()) {
                processingOutputBuffer.setStackInSlot(slot, afterInsert);
                blockEntity.notifyUpdate();
                return true;
            }
        }

        ItemStack previousItem = heldItem.stack;
        ItemStack afterInsert = blockEntity.getBehaviour(DirectBeltInputBehaviour.TYPE)
                .tryExportingToBeltFunnel(previousItem, null, false);
        if (afterInsert == null)
            return false;
        if (previousItem.getCount() != afterInsert.getCount()) {
            if (afterInsert.isEmpty())
                heldItem = null;
            else
                heldItem.stack = afterInsert;
            blockEntity.notifyUpdate();
            return true;
        }

        return false;
    }

    @Override
    public void destroy() {
        super.destroy();
        Level level = getWorld();
        BlockPos pos = getPos();
        ItemHelper.dropContents(level, pos, processingOutputBuffer);
        for (TransportedItemStack transportedItemStack : incoming)
            Block.popResource(level, pos, transportedItemStack.stack);
        if (!getHeldItemStack().isEmpty())
            Block.popResource(level, pos, getHeldItemStack());
    }

    @Override
    public void unload() {
        if (lazyItemHandler != null)
            lazyItemHandler.invalidate();
    }

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        if (heldItem != null)
            compound.put("HeldItem", intItemStackSave(heldItem));
        compound.put("OutputBuffer", processingOutputBuffer.serializeNBT());
        if (canMergeItems() && !incoming.isEmpty())
            compound.put("Incoming", NBTHelper.writeCompoundList(incoming, TransportedItemStack::serializeNBT));
    }

    @Override
    public void read(CompoundTag compound, boolean clientPacket) {
        heldItem = null;
        if (compound.contains("HeldItem"))
            heldItem = intItemStackRead(compound.getCompound("HeldItem"));
        processingOutputBuffer.deserializeNBT(compound.getCompound("OutputBuffer"));
        if (canMergeItems()) {
            ListTag list = compound.getList("Incoming", Tag.TAG_COMPOUND);
            incoming = NBTHelper.readCompoundList(list, TransportedItemStack::read);
        }
    }

    public void addSubBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(new DirectBeltInputBehaviour(blockEntity).allowingBeltFunnels()
                .setInsertionHandler(this::tryInsertingFromSide).considerOccupiedWhen(this::isOccupied));
        transportedHandler = new TransportedItemStackHandlerBehaviour(blockEntity, this::applyToAllItems)
                .withStackPlacement(this::getWorldPositionOf);
        behaviours.add(transportedHandler);
    }

    public ItemStack getHeldItemStack() {
        return heldItem == null ? ItemStack.EMPTY : heldItem.stack;
    }

    public boolean canMergeItems() {
        return allowMerge;
    }

    public int getPresentStackSize() {
        int cumulativeStackSize = 0;
        cumulativeStackSize += getHeldItemStack().getCount();
        for (int slot = 0; slot < processingOutputBuffer.getSlots(); slot++)
            cumulativeStackSize += processingOutputBuffer.getStackInSlot(slot)
                    .getCount();
        return cumulativeStackSize;
    }

    public int getRemainingSpace() {
        int cumulativeStackSize = getPresentStackSize();
        for (TransportedItemStack transportedItemStack : incoming)
            cumulativeStackSize += transportedItemStack.stack.getCount();
        int limit = maxStackSize.get() == 0 ? 64 : maxStackSize.get();
        return limit - cumulativeStackSize;
    }

    public ItemStack insert(TransportedItemStack heldItem, boolean simulate) {
        if (!canAcceptItems.get())
            return heldItem.stack;
        if (!acceptedItems.test(heldItem.stack))
            return heldItem.stack;

        if (canMergeItems()) {
            int remainingSpace = getRemainingSpace();
            ItemStack inserted = heldItem.stack;
            if (remainingSpace <= 0)
                return inserted;
            if (this.heldItem != null && !ItemHelper.canItemStackAmountsStack(this.heldItem.stack, inserted))
                return inserted;

            ItemStack returned = ItemStack.EMPTY;
            if (remainingSpace < inserted.getCount()) {
                returned = ItemHandlerHelper.copyStackWithSize(heldItem.stack, inserted.getCount() - remainingSpace);
                if (!simulate) {
                    TransportedItemStack copy = heldItem.copy();
                    copy.stack.setCount(remainingSpace);
                    if (this.heldItem != null)
                        incoming.add(copy);
                    else
                        this.heldItem = copy;
                }
            } else {
                if (!simulate) {
                    if (this.heldItem != null)
                        incoming.add(heldItem);
                    else
                        this.heldItem = heldItem;
                }
            }
            return returned;
        }

        ItemStack returned = ItemStack.EMPTY;
        int maxCount = maxStackSize.get();
        boolean stackTooLarge = maxCount < heldItem.stack.getCount();
        if (stackTooLarge)
            returned = ItemHandlerHelper.copyStackWithSize(heldItem.stack, heldItem.stack.getCount() - maxCount);

        if (simulate)
            return returned;

        if (this.isEmpty()) {
            if (heldItem.insertedFrom.getAxis().isHorizontal())
                AllSoundEvents.DEPOT_SLIDE.playOnServer(getWorld(), getPos());
            else
                AllSoundEvents.DEPOT_PLOP.playOnServer(getWorld(), getPos());
        }

        if (stackTooLarge) {
            heldItem = heldItem.copy();
            heldItem.stack.setCount(maxCount);
        }

        this.heldItem = heldItem;
        onHeldInserted.accept(heldItem.stack);
        return returned;
    }

    public void setHeldItem(TransportedItemStack heldItem) {
        this.heldItem = heldItem;
    }

    public void addHeldItem(TransportedItemStack incomingItem) {
        if (this.heldItem == null || this.heldItem.stack.isEmpty()) {
            this.heldItem = incomingItem.copy();
            incomingItem.stack.setCount(0);
            return;
        }

        if (ItemStack.isSameItemSameTags(this.heldItem.stack, incomingItem.stack)) {
            int currentCount = this.heldItem.stack.getCount();
            int maxCount = maxStackSize.get();
            int roomLeft = maxCount - currentCount;

            if (roomLeft > 0) {
                int amountToAdd = Math.min(roomLeft, incomingItem.stack.getCount());
                this.heldItem.stack.grow(amountToAdd);
                incomingItem.stack.shrink(amountToAdd);
            }
        }
    }

    public void removeHeldItem() {
        this.heldItem = null;
    }

    public void setCenteredHeldItem(TransportedItemStack heldItem) {
        this.heldItem = heldItem;
        this.heldItem.beltPosition = 0.5f;
        this.heldItem.prevBeltPosition = 0.5f;
    }

    public <T> LazyOptional<T> getItemCapability(Capability<T> cap, Direction side) {
        return lazyItemHandler.cast();
    }

    private boolean isOccupied(Direction side) {
        if (!getHeldItemStack().isEmpty() && !canMergeItems())
            return true;
        if (!isOutputEmpty() && !canMergeItems())
            return true;
        if (!canAcceptItems.get())
            return true;
        return false;
    }

    private ItemStack tryInsertingFromSide(TransportedItemStack transportedStack, Direction side, boolean simulate) {
        ItemStack inserted = transportedStack.stack;

        if (isOccupied(side))
            return inserted;

        int size = transportedStack.stack.getCount();
        transportedStack = transportedStack.copy();
        transportedStack.beltPosition = side.getAxis()
                .isVertical() ? .5f : 0;
        transportedStack.insertedFrom = side;
        transportedStack.prevSideOffset = transportedStack.sideOffset;
        transportedStack.prevBeltPosition = transportedStack.beltPosition;
        ItemStack remainder = insert(transportedStack, simulate);
        if (remainder.getCount() != size)
            blockEntity.notifyUpdate();

        return remainder;
    }

    private void applyToAllItems(float maxDistanceFromCentre, Function<TransportedItemStack, TransportedItemStackHandlerBehaviour.TransportedResult> processFunction) {
        if (heldItem == null)
            return;
        if (.5f - heldItem.beltPosition > maxDistanceFromCentre)
            return;

        boolean dirty = false;
        TransportedItemStack transportedItemStack = heldItem;
        ItemStack stackBefore = transportedItemStack.stack.copy();
        TransportedItemStackHandlerBehaviour.TransportedResult result = processFunction.apply(transportedItemStack);
        if (result == null || result.didntChangeFrom(stackBefore))
            return;

        dirty = true;
        heldItem = null;
        if (result.hasHeldOutput())
            setCenteredHeldItem(result.getHeldOutput());

        for (TransportedItemStack added : result.getOutputs()) {
            if (getHeldItemStack().isEmpty()) {
                setCenteredHeldItem(added);
                continue;
            }
            ItemStack remainder = processingOutputBuffer.insertItem(0, added.stack, false);
            for (int i = 1; i < processingOutputBuffer.getSlots() && !remainder.isEmpty(); i++) {
                remainder = processingOutputBuffer.insertItem(i, remainder, false);
            }

            if (!remainder.isEmpty()) {
                Vec3 vec = VecHelper.getCenterOf(blockEntity.getBlockPos());
                Containers.dropItemStack(blockEntity.getLevel(), vec.x, vec.y + .5f, vec.z, remainder);
            }
        }

        if (dirty)
            blockEntity.notifyUpdate();
    }

    public boolean isEmpty() {
        return heldItem == null && isOutputEmpty();
    }

    public boolean isOutputEmpty() {
        for (int i = 0; i < processingOutputBuffer.getSlots(); i++)
            if (!processingOutputBuffer.getStackInSlot(i)
                    .isEmpty())
                return false;
        return true;
    }

    private Vec3 getWorldPositionOf(TransportedItemStack transported) {
        return VecHelper.getCenterOf(blockEntity.getBlockPos());
    }

    @Override
    public BehaviourType<?> getType() {
        return TYPE;
    }

    public boolean isItemValid(ItemStack stack) {
        return acceptedItems.test(stack);
    }

    private CompoundTag intItemStackSave(TransportedItemStack stack) {
        CompoundTag itemTag = stack.serializeNBT();
        if (itemTag.contains("Item")) {
            CompoundTag itemTag2 = itemTag.getCompound("Item");
            if (itemTag2.contains("Count")) {
                itemTag2.remove("Count");
                itemTag2.putInt("Count", stack.stack.getCount());
            }
        }

        return itemTag;
    }

    private TransportedItemStack intItemStackRead(CompoundTag pCompoundTag) {
        CompoundTag tempTag = pCompoundTag.copy();
        int count = 1;
        if (pCompoundTag.contains("Item")) {
            CompoundTag itemTag = tempTag.getCompound("Item");
            count = itemTag.getInt("Count");
        }

        if (tempTag.contains("Item")) {
            CompoundTag itemTag = tempTag.getCompound("Item");
            itemTag.putByte("Count", (byte) 1);
        }

        TransportedItemStack stack = TransportedItemStack.read(tempTag);

        stack.stack.setCount(count);
        return stack;
    }

}
