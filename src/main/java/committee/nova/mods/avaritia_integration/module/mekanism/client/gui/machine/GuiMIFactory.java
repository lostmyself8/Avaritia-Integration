package committee.nova.mods.avaritia_integration.module.mekanism.client.gui.machine;

import committee.nova.mods.avaritia_integration.module.mekanism.client.gui.element.tab.GuiMISortingTab;
import committee.nova.mods.avaritia_integration.module.mekanism.client.jei.MekIntegrationJEIRecipeType;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.factory.TileEntityGasToItemMIFactory;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.factory.TileEntityItemToItemMIFactory;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.factory.TileEntityMIFactory;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiGasGauge;
import mekanism.client.gui.element.progress.GuiProgress;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.client.jei.MekanismJEIRecipeType;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.inventory.warning.WarningTracker.WarningType;
import mekanism.common.tier.FactoryTier;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class GuiMIFactory extends GuiConfigurableTile<TileEntityMIFactory<?>, MekanismTileContainer<TileEntityMIFactory<?>>> {

    public GuiMIFactory(MekanismTileContainer<TileEntityMIFactory<?>> container, Inventory inv, Component title) {
        super(container, inv, title);
        if (tile instanceof TileEntityGasToItemMIFactory<?>) imageHeight += 13;
        inventoryLabelY = 75;
        if (tile.tier == FactoryTier.ULTIMATE) {
            imageWidth += 34;
            inventoryLabelX = 26;
        }
        titleLabelY = 4;
        dynamicSlots = true;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        if (tile instanceof TileEntityItemToItemMIFactory<?>) {
            addRenderableWidget(new GuiMISortingTab(this, tile));
        }
        addRenderableWidget(new GuiVerticalPowerBar(this, tile.getEnergyContainer(), imageWidth - 12, 16, tile instanceof TileEntityGasToItemMIFactory ? 65 : 52))
                .warning(WarningType.NOT_ENOUGH_ENERGY, tile.getWarningCheck(CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_ENERGY, 0));
        addRenderableWidget(new GuiEnergyTab(this, tile.getEnergyContainer(), tile::getLastUsage));

        if (tile instanceof TileEntityGasToItemMIFactory<?> factory) {
            for (int i = 0; i < tile.tier.processes; i++) {
                int index = i;
                addRenderableWidget(new GuiGasGauge(() -> factory.inputGasTanks.get(index), () -> factory.getGasTanks(null), GaugeType.SMALL, this, factory.getXPos(index) - 1, 13))
                        .warning(WarningType.NO_MATCHING_RECIPE, factory.getWarningCheck(CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_INPUT, index));
            }
        }

        for (int i = 0; i < tile.tier.processes; i++) {
            int cacheIndex = i;
            addProgress(new GuiProgress(() -> tile.getScaledProgress(1, cacheIndex), ProgressType.DOWN, this, 4 + tile.getXPos(i), getProgressYPos()))
                    // Only can happen if recipes change because inputs are sanitized in the factory based on the output
                    .warning(WarningType.INPUT_DOESNT_PRODUCE_OUTPUT, tile.getWarningCheck(CachedRecipe.OperationTracker.RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT, cacheIndex));
        }
    }

    private int getProgressYPos() {
        if (tile instanceof TileEntityGasToItemMIFactory<?>) {
            return 46;
        } else {
            return 33;
        }
    }

    private GuiProgress addProgress(GuiProgress progressBar) {
        MekanismJEIRecipeType<?> jeiType = switch (tile.getFactoryType()) {
            case NEUTRON_COLLECTING -> MekIntegrationJEIRecipeType.NEUTRON_COLLECTOR;
            case SINGULARITY_COMPRESSING -> MekIntegrationJEIRecipeType.NEUTRON_COMPRESSOR;
        };
        return addRenderableWidget(progressBar.jeiCategories(jeiType));
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        drawString(guiGraphics, playerInventoryTitle, inventoryLabelX, inventoryLabelY, titleTextColor());
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }
}
