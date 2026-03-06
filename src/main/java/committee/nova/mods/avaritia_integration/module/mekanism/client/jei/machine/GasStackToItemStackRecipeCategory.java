package committee.nova.mods.avaritia_integration.module.mekanism.client.jei.machine;

import committee.nova.mods.avaritia_integration.module.mekanism.api.recipes.chemicals.GasStackToItemStackRecipe;
import mekanism.api.providers.IItemProvider;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiGasGauge;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.gui.element.progress.GuiProgress;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.jei.BaseRecipeCategory;
import mekanism.client.jei.MekanismJEI;
import mekanism.client.jei.MekanismJEIRecipeType;
import mekanism.common.tile.component.config.DataType;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import org.jetbrains.annotations.NotNull;

public class GasStackToItemStackRecipeCategory extends BaseRecipeCategory<GasStackToItemStackRecipe> {

    private static final String CHEMICAL_INPUT = "chemicalInput";

    protected final GuiProgress progressBar;
    private final GuiSlot output;
    private final GuiGauge<?> input;

    public GasStackToItemStackRecipeCategory(IGuiHelper helper, MekanismJEIRecipeType<GasStackToItemStackRecipe> recipeType, IItemProvider provider) {
        super(helper, recipeType, provider.getTextComponent(), createIcon(helper, provider), 20, 12, 132, 62);
        input = addElement(GuiGasGauge.getDummy(GaugeType.STANDARD.with(DataType.INPUT), this, 26, 13));
        output = addSlot(SlotType.OUTPUT, 131, 36);
        progressBar = addElement(new GuiProgress(getSimpleProgressTimer(), ProgressType.LARGE_RIGHT, this, 64, 40));
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, GasStackToItemStackRecipe recipe, @NotNull IFocusGroup focuses) {
        initChemical(builder, MekanismJEI.TYPE_GAS, RecipeIngredientRole.INPUT, input, recipe.getInput().getRepresentations()).setSlotName(CHEMICAL_INPUT);
        initItem(builder, RecipeIngredientRole.OUTPUT, output, recipe.getOutputDefinition());
    }
}
