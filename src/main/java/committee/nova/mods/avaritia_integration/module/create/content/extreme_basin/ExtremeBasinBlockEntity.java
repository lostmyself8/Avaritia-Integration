package committee.nova.mods.avaritia_integration.module.create.content.extreme_basin;

import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import committee.nova.mods.avaritia_integration.module.create.content.extreme_burner.ExtremeBlazeBurnerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ExtremeBasinBlockEntity extends BasinBlockEntity{
//    private boolean areFluidsMoving;
//    LerpedFloat ingredientRotationSpeed;
//    LerpedFloat ingredientRotation;
//
//    public BasinInventory inputInventory;
//    public SmartFluidTankBehaviour inputTank;
//    protected SmartInventory outputInventory;
//    protected SmartFluidTankBehaviour outputTank;
//    private FilteringBehaviour filtering;
//    private boolean contentsChanged;
//
//    private Couple<SmartInventory> invs;
//    private Couple<SmartFluidTankBehaviour> tanks;
//
//    protected LazyOptional<IItemHandlerModifiable> itemCapability;
//    protected LazyOptional<IFluidHandler> fluidCapability;
//
//    List<Direction> disabledSpoutputs;
//    Direction preferredSpoutput;
//    protected List<ItemStack> spoutputBuffer;
//    protected List<FluidStack> spoutputFluidBuffer;
//    int recipeBackupCheck;
//
//    public static final int OUTPUT_ANIMATION_TIME = 10;
//    List<IntAttached<ItemStack>> visualizedOutputItems;
//    List<IntAttached<FluidStack>> visualizedOutputFluids;
//
//    private @Nullable BlazeBurnerBlock.HeatLevel cachedHeatLevel;
    private @Nullable ExtremeBlazeBurnerBlock.ExtremeHeatLevel cachedExtremeHeatLevel;

    public ExtremeBasinBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

//    public ExtremeBasinBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
//        super(type, pos, state);
//        inputInventory = new BasinInventory(9, this);
//        inputInventory.whenContentsChanged($ -> contentsChanged = true);
//        outputInventory = new BasinInventory(9, this).forbidInsertion()
//                .withMaxStackSize(64);
//        areFluidsMoving = false;
//        itemCapability = LazyOptional.of(() -> new CombinedInvWrapper(inputInventory, outputInventory));
//        contentsChanged = true;
//        ingredientRotation = LerpedFloat.angular()
//                .startWithValue(0);
//        ingredientRotationSpeed = LerpedFloat.linear()
//                .startWithValue(0);
//
//        invs = Couple.create(inputInventory, outputInventory);
//        tanks = Couple.create(inputTank, outputTank);
//        visualizedOutputItems = Collections.synchronizedList(new ArrayList<>());
//        visualizedOutputFluids = Collections.synchronizedList(new ArrayList<>());
//        disabledSpoutputs = new ArrayList<>();
//        preferredSpoutput = null;
//        spoutputBuffer = new ArrayList<>();
//        spoutputFluidBuffer = new ArrayList<>();
//        recipeBackupCheck = 20;
//    }
//
//    @Override
//    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
//        behaviours.add(new DirectBeltInputBehaviour(this));
//        filtering = new FilteringBehaviour(this, new BasinValueBox()).withCallback(newFilter -> contentsChanged = true)
//                .forRecipes();
//        behaviours.add(filtering);
//
//        inputTank = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.INPUT, this, 2, 1000, true)
//                .whenFluidUpdates(() -> contentsChanged = true);
//        outputTank = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.OUTPUT, this, 2, 1000, true)
//                .whenFluidUpdates(() -> contentsChanged = true)
//                .forbidInsertion();
//        behaviours.add(inputTank);
//        behaviours.add(outputTank);
//
//        fluidCapability = LazyOptional.of(() -> {
//            LazyOptional<? extends IFluidHandler> inputCap = inputTank.getCapability();
//            LazyOptional<? extends IFluidHandler> outputCap = outputTank.getCapability();
//            return new CombinedTankWrapper(outputCap.orElse(null), inputCap.orElse(null));
//        });
//    }
//
//    @Override
//    protected void read(CompoundTag compound, boolean clientPacket) {
//        super.read(compound, clientPacket);
//        inputInventory.deserializeNBT(compound.getCompound("InputItems"));
//        outputInventory.deserializeNBT(compound.getCompound("OutputItems"));
//
//        preferredSpoutput = null;
//        if (compound.contains("PreferredSpoutput"))
//            preferredSpoutput = NBTHelper.readEnum(compound, "PreferredSpoutput", Direction.class);
//        disabledSpoutputs.clear();
//        ListTag disabledList = compound.getList("DisabledSpoutput", Tag.TAG_STRING);
//        disabledList.forEach(d -> disabledSpoutputs.add(Direction.valueOf(((StringTag) d).getAsString())));
//        spoutputBuffer = NBTHelper.readItemList(compound.getList("Overflow", Tag.TAG_COMPOUND));
//        spoutputFluidBuffer = NBTHelper.readCompoundList(compound.getList("FluidOverflow", Tag.TAG_COMPOUND),
//                FluidStack::loadFluidStackFromNBT);
//
//        if (!clientPacket)
//            return;
//
//        NBTHelper.iterateCompoundList(compound.getList("VisualizedItems", Tag.TAG_COMPOUND),
//                c -> visualizedOutputItems.add(IntAttached.with(OUTPUT_ANIMATION_TIME, ItemStack.of(c))));
//        NBTHelper.iterateCompoundList(compound.getList("VisualizedFluids", Tag.TAG_COMPOUND),
//                c -> visualizedOutputFluids
//                        .add(IntAttached.with(OUTPUT_ANIMATION_TIME, FluidStack.loadFluidStackFromNBT(c))));
//    }
//
//    @Override
//    public void write(CompoundTag compound, boolean clientPacket) {
//        super.write(compound, clientPacket);
//        compound.put("InputItems", inputInventory.serializeNBT());
//        compound.put("OutputItems", outputInventory.serializeNBT());
//
//        if (preferredSpoutput != null)
//            NBTHelper.writeEnum(compound, "PreferredSpoutput", preferredSpoutput);
//        ListTag disabledList = new ListTag();
//        disabledSpoutputs.forEach(d -> disabledList.add(StringTag.valueOf(d.name())));
//        compound.put("DisabledSpoutput", disabledList);
//        compound.put("Overflow", NBTHelper.writeItemList(spoutputBuffer));
//        compound.put("FluidOverflow",
//                NBTHelper.writeCompoundList(spoutputFluidBuffer, fs -> fs.writeToNBT(new CompoundTag())));
//
//        if (!clientPacket)
//            return;
//
//        compound.put("VisualizedItems", NBTHelper.writeCompoundList(visualizedOutputItems, ia -> ia.getValue()
//                .serializeNBT()));
//        compound.put("VisualizedFluids", NBTHelper.writeCompoundList(visualizedOutputFluids, ia -> ia.getValue()
//                .writeToNBT(new CompoundTag())));
//        visualizedOutputItems.clear();
//        visualizedOutputFluids.clear();
//    }
//
//    @Override
//    public void destroy() {
//        super.destroy();
//        ItemHelper.dropContents(level, worldPosition, inputInventory);
//        ItemHelper.dropContents(level, worldPosition, outputInventory);
//        spoutputBuffer.forEach(is -> Block.popResource(level, worldPosition, is));
//    }
//
//    @Override
//    public void remove() {
//        super.remove();
//        onEmptied();
//    }
//
//    public void onEmptied() {
//        getOperator().ifPresent(be -> be.basinRemoved = true);
//    }
//
//    @Override
//    public void invalidate() {
//        super.invalidate();
//        itemCapability.invalidate();
//        fluidCapability.invalidate();
//    }
//
//    @Nonnull
//    @Override
//    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
//        if (cap == ForgeCapabilities.ITEM_HANDLER)
//            return itemCapability.cast();
//        if (cap == ForgeCapabilities.FLUID_HANDLER)
//            return fluidCapability.cast();
//        return super.getCapability(cap, side);
//    }
//
//    @Override
//    public void notifyUpdate() {
//        super.notifyUpdate();
//    }
//
//    @Override
//    public void lazyTick() {
//        super.lazyTick();
//
//        if (!level.isClientSide) {
//            updateSpoutput();
//            if (recipeBackupCheck-- > 0)
//                return;
//            recipeBackupCheck = 20;
//            if (isEmpty())
//                return;
//            notifyChangeOfContents();
//            return;
//        }
//
//        BlockEntity blockEntity = level.getBlockEntity(worldPosition.above(2));
//        if (!(blockEntity instanceof MechanicalMixerBlockEntity mixer)) {
//            setAreFluidsMoving(false);
//            return;
//        }
//
//        setAreFluidsMoving(mixer.running && mixer.runningTicks <= 20);
//    }
//
//    public boolean isEmpty() {
//        return inputInventory.isEmpty() && outputInventory.isEmpty() && inputTank.isEmpty() && outputTank.isEmpty();
//    }
//
//    public void onWrenched(Direction face) {
//        BlockState blockState = getBlockState();
//        Direction currentFacing = blockState.getValue(ExtremeBasinBlock.FACING);
//
//        disabledSpoutputs.remove(face);
//        if (currentFacing == face) {
//            if (preferredSpoutput == face)
//                preferredSpoutput = null;
//            disabledSpoutputs.add(face);
//        } else
//            preferredSpoutput = face;
//
//        updateSpoutput();
//    }
//
//    private void updateSpoutput() {
//        BlockState blockState = getBlockState();
//        Direction currentFacing = blockState.getValue(ExtremeBasinBlock.FACING);
//        Direction newFacing = Direction.DOWN;
//        for (Direction test : Iterate.horizontalDirections) {
//            boolean canOutputTo = ExtremeBasinBlock.canOutputTo(level, worldPosition, test);
//            if (canOutputTo && !disabledSpoutputs.contains(test))
//                newFacing = test;
//        }
//
//        if (preferredSpoutput != null && ExtremeBasinBlock.canOutputTo(level, worldPosition, preferredSpoutput)
//                && preferredSpoutput != Direction.UP)
//            newFacing = preferredSpoutput;
//
//        if (newFacing == currentFacing)
//            return;
//
//        level.setBlockAndUpdate(worldPosition, blockState.setValue(ExtremeBasinBlock.FACING, newFacing));
//
//        if (newFacing.getAxis()
//                .isVertical())
//            return;
//
//        for (int slot = 0; slot < outputInventory.getSlots(); slot++) {
//            ItemStack extractItem = outputInventory.extractItem(slot, 64, true);
//            if (extractItem.isEmpty())
//                continue;
//            if (acceptOutputs(ImmutableList.of(extractItem), Collections.emptyList(), true))
//                acceptOutputs(ImmutableList.of(outputInventory.extractItem(slot, 64, false)), Collections.emptyList(),
//                        false);
//        }
//
//        IFluidHandler handler = outputTank.getCapability()
//                .orElse(null);
//        for (int slot = 0; slot < handler.getTanks(); slot++) {
//            FluidStack fs = handler.getFluidInTank(slot)
//                    .copy();
//            if (fs.isEmpty())
//                continue;
//            if (acceptOutputs(Collections.emptyList(), ImmutableList.of(fs), true)) {
//                handler.drain(fs, IFluidHandler.FluidAction.EXECUTE);
//                acceptOutputs(Collections.emptyList(), ImmutableList.of(fs), false);
//            }
//        }
//
//        notifyChangeOfContents();
//        notifyUpdate();
//    }
//
//    @Override
//    public void tick() {
//        cachedHeatLevel = null;
//
//        super.tick();
//        if (level.isClientSide) {
//            createFluidParticles();
//            tickVisualizedOutputs();
//            ingredientRotationSpeed.tickChaser();
//            ingredientRotation.setValue(ingredientRotation.getValue() + ingredientRotationSpeed.getValue());
//        }
//
//        if ((!spoutputBuffer.isEmpty() || !spoutputFluidBuffer.isEmpty()) && !level.isClientSide)
//            tryClearingSpoutputOverflow();
//        if (!contentsChanged)
//            return;
//
//        contentsChanged = false;
//        getOperator().ifPresent(be -> be.basinChecker.scheduleUpdate());
//
//        for (Direction offset : Iterate.horizontalDirections) {
//            BlockPos toUpdate = worldPosition.above()
//                    .relative(offset);
//            BlockState stateToUpdate = level.getBlockState(toUpdate);
//            if (stateToUpdate.getBlock() instanceof BasinBlock
//                    && stateToUpdate.getValue(BasinBlock.FACING) == offset.getOpposite()) {
//                BlockEntity be = level.getBlockEntity(toUpdate);
//                if (be instanceof BasinBlockEntity)
//                    ((BasinBlockEntity) be).contentsChanged = true;
//            }
//            if (stateToUpdate.getBlock() instanceof ExtremeBasinBlock
//                    && stateToUpdate.getValue(ExtremeBasinBlock.FACING) == offset.getOpposite()) {
//                BlockEntity be = level.getBlockEntity(toUpdate);
//                if (be instanceof ExtremeBasinBlockEntity)
//                    ((ExtremeBasinBlockEntity) be).contentsChanged = true;
//            }
//        }
//    }
}
