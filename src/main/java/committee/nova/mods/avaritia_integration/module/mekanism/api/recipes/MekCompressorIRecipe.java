package committee.nova.mods.avaritia_integration.module.mekanism.api.recipes;

import committee.nova.mods.avaritia_integration.module.mekanism.common.recipe.MekIntegrationRecipeType;
import committee.nova.mods.avaritia_integration.module.mekanism.common.registry.MekIntegrationRecipeSerializers;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

@NothingNullByDefault
public class MekCompressorIRecipe extends ItemStackToItemStackRecipe {

//    public MekCompressorIRecipe(Item item, ItemStackIngredient input, ItemStack output, int inputCount, int timeRequire) {
//        this(Mekanism.rl("compressor/" + RegistryUtils.getName(item).toString().replace(':', '/')), input, output, inputCount, timeRequire);
//    }

    public MekCompressorIRecipe(ResourceLocation id, ItemStackIngredient input, ItemStack output) {
        super(id, input, output);
    }

    @Override
    public RecipeType<ItemStackToItemStackRecipe> getType() {
        return MekIntegrationRecipeType.MEK_COMPRESSING.get();
    }

    @Override
    public RecipeSerializer<ItemStackToItemStackRecipe> getSerializer() {
        return MekIntegrationRecipeSerializers.MEK_COMPRESSOR.get();
    }

    @Override
    public String getGroup() {
        return super.getGroup();
    }

    @Override
    public ItemStack getToastSymbol() {
        return super.getToastSymbol();
    }
}
