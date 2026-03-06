package committee.nova.mods.avaritia_integration.module.create.compat.cca.liquid_burning;

import com.google.gson.JsonObject;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class LiquidBurningRecipeSerializer implements RecipeSerializer<LiquidBurningRecipe> {
    public LiquidBurningRecipeSerializer() {}

    @Override
    public LiquidBurningRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        boolean starheated = buffer.readBoolean();
        int burnTime = buffer.readInt();
        FluidIngredient fluid = FluidIngredient.read(buffer);
        return new LiquidBurningRecipe(recipeId, fluid, burnTime, starheated);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, LiquidBurningRecipe recipe) {
        buffer.writeBoolean(recipe.starheated);
        buffer.writeInt(recipe.burnTime);
        recipe.fluidIngredients.write(buffer);
    }

//    @Override
//    public ItemStack getIcon() {
//        return CreateIntegrationBlocks.EXTREME_BLAZE_BURNER.asStack();
//    }

    @Override
    public LiquidBurningRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        int burnTime = GsonHelper.getAsInt(json, "burnTime");
        FluidIngredient fluid = FluidIngredient.deserialize(json.get("input"));
        boolean starheated = GsonHelper.getAsBoolean(json, "starheated", false);

        return new LiquidBurningRecipe(recipeId, fluid, burnTime, starheated);
    }
}
