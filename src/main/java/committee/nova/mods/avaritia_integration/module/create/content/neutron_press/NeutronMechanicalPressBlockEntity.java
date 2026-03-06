package committee.nova.mods.avaritia_integration.module.create.content.neutron_press;

import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.advancement.CreateAdvancement;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.recipe.RecipeApplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;

public class NeutronMechanicalPressBlockEntity extends MechanicalPressBlockEntity {
    public NeutronMechanicalPressBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        this.pressingBehaviour = new NeutronPressingBehaviour(this);
        behaviours.add(this.pressingBehaviour);
        this.registerAwardables(behaviours, new CreateAdvancement[]{AllAdvancements.PRESS, AllAdvancements.COMPACTING, AllAdvancements.TRACK_CRAFTING});
    }

    @Override
    public boolean tryProcessInWorld(ItemEntity itemEntity, boolean simulate) {
        ItemStack item = itemEntity.getItem();
        Optional<PressingRecipe> recipe = getRecipe(item);
        if (!recipe.isPresent())
            return false;
        if (simulate)
            return true;

        pressingBehaviour.particleItems.add(item);
        RecipeApplier.applyRecipeOn(itemEntity, recipe.get(), true);
        ItemStack itemCreated = itemEntity.getItem()
                .copy();

        if (!itemCreated.isEmpty())
            onItemPressed(itemCreated);
        return true;
    }

    @Override
    public boolean tryProcessOnBelt(TransportedItemStack input, List<ItemStack> outputList, boolean simulate) {
        Optional<PressingRecipe> recipe = getRecipe(input.stack);
        if (!recipe.isPresent())
            return false;
        if (simulate)
            return true;
        pressingBehaviour.particleItems.add(input.stack.copy());
        List<ItemStack> outputs = RecipeApplier.applyRecipeOn(level, input.stack, recipe.get(), true);

        for (ItemStack created : outputs) {
            if (!created.isEmpty()) {
                onItemPressed(created);
                break;
            }
        }

        input.stack.setCount(0);
        outputList.addAll(outputs);
        return true;
    }

    @Override
    protected void applyBasinRecipe() {
        if (currentRecipe == null)
            return;

        Optional<BasinBlockEntity> optionalBasin = getBasin();
        if (!optionalBasin.isPresent())
            return;
        BasinBlockEntity basin = optionalBasin.get();
        boolean wasEmpty = basin.canContinueProcessing();
        boolean processedAtLeastOnce = false;
        int safetyNet = 64;
        while (safetyNet > 0 && BasinRecipe.apply(basin, currentRecipe)) {
            processedAtLeastOnce = true;
            safetyNet--;
        }

        if (!processedAtLeastOnce)
            return;

        getProcessedRecipeTrigger().ifPresent(this::award);
        basin.inputTank.sendDataImmediately();

        if (wasEmpty && matchBasinRecipe(currentRecipe)) {
            continueWithPreviousRecipe();
            sendData();
        }

        basin.notifyChangeOfContents();
    }
}
