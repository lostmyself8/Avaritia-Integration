package committee.nova.mods.avaritia_integration.module.create.compat.jei.category;

import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.item.ItemHelper;
import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.create.content.recipe.ExtremeBasinRecipe;
import committee.nova.mods.avaritia_integration.module.create.content.recipe.ExtremeHeatCondition;
import committee.nova.mods.avaritia_integration.module.create.registry.CreateIntegrationBlocks;
import committee.nova.mods.avaritia_integration.module.create.registry.CreateIntegrationItems;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.createmod.catnip.data.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.mutable.MutableInt;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public class ExtremeBasinCategory extends CreateRecipeCategory<ExtremeBasinRecipe> {

    private final boolean needsHeating;

    public ExtremeBasinCategory(Info<ExtremeBasinRecipe> info, boolean needsHeating) {
        super(info);
        this.needsHeating = needsHeating;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ExtremeBasinRecipe recipe, IFocusGroup focuses) {
        List<Pair<Ingredient, MutableInt>> condensedIngredients = ItemHelper.condenseIngredients(recipe.getIngredients());

        int size = condensedIngredients.size() + recipe.getFluidIngredients().size();
        int xOffset = size < 3 ? (3 - size) * 19 / 2 : 0;
        int i = 0;

        for (Pair<Ingredient, MutableInt> pair : condensedIngredients) {
            List<ItemStack> stacks = new ArrayList<>();
            for (ItemStack itemStack : pair.getFirst().getItems()) {
                ItemStack copy = itemStack.copy();
                copy.setCount(pair.getSecond().getValue());
                stacks.add(copy);
            }

            builder
                    .addSlot(RecipeIngredientRole.INPUT, 17 + xOffset + (i % 3) * 19, 51 - (i / 3) * 19)
                    .setBackground(getRenderedSlot(), -1, -1)
                    .addItemStacks(stacks);
            i++;
        }
        for (FluidIngredient fluidIngredient : recipe.getFluidIngredients()) {
            int x = 17 + xOffset + (i % 3) * 19;
            int y = 51 - (i / 3) * 19;
            addFluidSlot(builder, x, y, fluidIngredient);
            i++;
        }

        size = recipe.getRollableResults().size() + recipe.getFluidResults().size();
        i = 0;

        for (ProcessingOutput result : recipe.getRollableResults()) {
            int xPosition = 142 - (size % 2 != 0 && i == size - 1 ? 0 : i % 2 == 0 ? 10 : -9);
            int yPosition = -19 * (i / 2) + 51;

            builder
                    .addSlot(RecipeIngredientRole.OUTPUT, xPosition, yPosition)
                    .setBackground(getRenderedSlot(result), -1, -1)
                    .addItemStack(result.getStack())
                    .addRichTooltipCallback(addStochasticTooltip(result));
            i++;
        }

        for (FluidStack fluidResult : recipe.getFluidResults()) {
            int xPosition = 142 - (size % 2 != 0 && i == size - 1 ? 0 : i % 2 == 0 ? 10 : -9);
            int yPosition = -19 * (i / 2) + 51;
            addFluidSlot(builder, xPosition, yPosition, fluidResult);
            i++;
        }

        ExtremeHeatCondition requiredHeat = recipe.getRequiredHeat();
        builder
                .addSlot(RecipeIngredientRole.RENDER_ONLY, 134, 81)
                .addItemStack(CreateIntegrationBlocks.EXTREME_BLAZE_BURNER.asStack());
        if (requiredHeat == ExtremeHeatCondition.STAR) {
            builder
                    .addSlot(RecipeIngredientRole.CATALYST, 153, 81)
                    .addItemStack(CreateIntegrationItems.STAR_CAKE.asStack());
        } else if (requiredHeat == ExtremeHeatCondition.BLAZE) {
            builder
                    .addSlot(RecipeIngredientRole.CATALYST, 153, 81)
                    .addItemStack(CreateIntegrationItems.IGNIS_CAKE.asStack());
        }
    }

    @Override
    public void draw(ExtremeBasinRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        ExtremeHeatCondition requiredHeat = recipe.getRequiredHeat();
        int vRows = (1 + recipe.getFluidResults().size() + recipe.getRollableResults().size()) / 2;

        if (vRows <= 2)
            AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 136, -19 * (vRows - 1) + 32);

        AllGuiTextures shadow = AllGuiTextures.JEI_SHADOW;
        shadow.render(graphics, 81, 58 + 10);

        if (!needsHeating)
            return;

        AllGuiTextures heatBar = AllGuiTextures.JEI_NO_HEAT_BAR;
        heatBar.render(graphics, 4, 80);
        graphics.drawString(Minecraft.getInstance().font, Component.translatable(AvaritiaIntegration.MOD_ID + "." + requiredHeat.getTranslationKey()), 9,
                86, requiredHeat.getColor(), false);
    }
}
