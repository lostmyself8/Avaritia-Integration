package committee.nova.mods.avaritia_integration.module.create.compat.cca.liquid_burning;

import com.simibubi.create.foundation.fluid.FluidIngredient;
import committee.nova.mods.avaritia_integration.module.create.registry.CreateIntegrationRecipeTypes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class LiquidBurningRecipe implements Recipe<FluidRecipeWrapper> {
    protected final ResourceLocation id;
    protected final FluidIngredient fluidIngredients;
    protected final int burnTime;
    protected boolean starheated;

    public LiquidBurningRecipe(ResourceLocation id, FluidIngredient fluidIngredient, int burnTime, boolean starheated) {
        this.id = id;
        this.fluidIngredients = fluidIngredient;
        this.burnTime = burnTime;
        this.starheated = starheated;
    }

    @Override
    public boolean matches(FluidRecipeWrapper wrapper, Level world) {
        if(fluidIngredients == null)
            return false;
        if(wrapper == null)
            return false;
        if(wrapper.fluid == null)
            return false;
        return fluidIngredients.test(wrapper.fluid);
    }

    @Override
    public ItemStack assemble(FluidRecipeWrapper pContainer, RegistryAccess pRegistryAccess) {
        return new ItemStack(Items.AIR);
    }

    @Override
    public boolean canCraftInDimensions(int w, int h) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return new ItemStack(Items.AIR);
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CreateIntegrationRecipeTypes.LIQUID_BURNING.getSerializer();
    }

    @Override
    public RecipeType<?> getType() {
        return CreateIntegrationRecipeTypes.LIQUID_BURNING.getType();
    }

    public FluidIngredient getFluidIngredient() {
        return fluidIngredients;
    }

    public int getBurnTime() {
        return this.burnTime;
    }

    public boolean isStarheated() {
        return this.starheated;
    }
}
