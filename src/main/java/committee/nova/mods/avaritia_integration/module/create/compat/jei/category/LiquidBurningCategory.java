package committee.nova.mods.avaritia_integration.module.create.compat.jei.category;

import com.mrh0.createaddition.index.CAItems;
import com.mrh0.createaddition.util.ClientMinecraftWrapper;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.create.compat.cca.liquid_burning.LiquidBurningRecipe;
import committee.nova.mods.avaritia_integration.module.create.compat.jei.category.animations.AnimatedExtremeBlazeBurner;
import committee.nova.mods.avaritia_integration.module.create.content.recipe.ExtremeHeatCondition;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class LiquidBurningCategory extends CreateRecipeCategory<LiquidBurningRecipe> {
    private final AnimatedExtremeBlazeBurner heater = new AnimatedExtremeBlazeBurner();

    public LiquidBurningCategory(Info<LiquidBurningRecipe> info) {
        super(info);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, LiquidBurningRecipe recipe, IFocusGroup focuses) {
        List<ItemStack> buckets = recipe.getFluidIngredient().getMatchingFluidStacks().stream()
                .filter(e -> e != null)
                .map((e) -> new ItemStack(e.getFluid().getBucket()))
                .toList();
        builder
                .addSlot(RecipeIngredientRole.INPUT, getBackground().getWidth() / 2 -56, 3)
                .setBackground(getRenderedSlot(), -1, -1)
                .addItemStack(new ItemStack(CAItems.STRAW.get()));
        builder
                .addSlot(RecipeIngredientRole.INPUT, getBackground().getWidth() / 2 -36, 3)
                .setBackground(getRenderedSlot(), -1, -1)
                .addItemStacks(buckets);
        addFluidSlot(builder, getBackground().getWidth() / 2 -16, 3, recipe.getFluidIngredient());
		/*builder
			.addSlot(RecipeIngredientRole.INPUT, getBackground().getWidth() / 2 -16, 3)
			.setBackground(getRenderedSlot(), -1, -1)
			.addIngredients(ForgeTypes.FLUID_STACK, withImprovedVisibility(recipe.getFluidIngredient().getMatchingFluidStacks()))
			.addRichTooltipCallback(addStochasticTooltip(recipe.));*/
    }

    @Override
    public void draw(LiquidBurningRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics gg, double mouseX, double mouseY) {

        gg.drawString(ClientMinecraftWrapper.getFont(), formatTime(recipe.getBurnTime()), getBackground().getWidth() / 2 + 48, 86 - 50, 4210752);

        ExtremeHeatCondition requiredHeat = recipe.isStarheated() ? ExtremeHeatCondition.STAR : ExtremeHeatCondition.BLAZE;

        AllGuiTextures.JEI_LIGHT.render(gg, 81, 58 + 30 - 50);

        AllGuiTextures.JEI_HEAT_BAR.render(gg, 4, 80 - 50);
        gg.drawString(ClientMinecraftWrapper.getFont(), Component.translatable(AvaritiaIntegration.MOD_ID + "." + requiredHeat.getTranslationKey()), 9,
                86 - 50, requiredHeat.getColor());

        heater.withHeat(requiredHeat.visualizeAsBlazeBurner())
                .draw(gg, getBackground().getWidth() / 2 + 3, 55 - 50);

        AllGuiTextures.JEI_DOWN_ARROW.render(gg, getBackground().getWidth() / 2 + 3, 8);
    }

    public static String formatTime(int ticks) {
        if (ticks > 20*60) return (ticks/(20*60)) + " min";
        if (ticks > 20) return (ticks/20) + " sec";
        return (ticks) + " ticks";
    }
}
