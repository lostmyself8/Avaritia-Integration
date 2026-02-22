package committee.nova.mods.avaritia_integration.module.create.compat.cca;

import com.mrh0.createaddition.index.CARecipes;
import com.mrh0.createaddition.recipe.FluidRecipeWrapper;
import com.mrh0.createaddition.recipe.liquid_burning.LiquidBurningRecipe;
import com.simibubi.create.foundation.fluid.SmartFluidTank;
import committee.nova.mods.avaritia_integration.module.create.content.extreme_burner.ExtremeBlazeBurnerBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

import java.util.Optional;

public class CCALiquidBlazeBurnerCompat implements ICCABurnerCompat {
    private final ExtremeBlazeBurnerBlockEntity be;
    private final SmartFluidTank fluidInventory;
    private Object recipeCache;

    public CCALiquidBlazeBurnerCompat(ExtremeBlazeBurnerBlockEntity be) {
        this.be = be;
        this.fluidInventory = new SmartFluidTank(4000, this::update);
        this.fluidInventory.setValidator(stack -> find(stack, be.getLevel()).isPresent());
    }

    @Override
    public SmartFluidTank getFluidInventory() {
        return fluidInventory;
    }

    private void update(FluidStack stack) {
        if (!be.hasLevel())
            return;
        if(be.getLevel().isClientSide())
            return;
        if(stack.getFluid().isSame(be.lastFluid))
            recipeCache = find(stack, be.getLevel());
        be.lastFluid = stack.getFluid();
    }

    public Optional<LiquidBurningRecipe> find(FluidStack stack, Level level) {
        if(stack == null)
            return Optional.empty();
        if(level == null)
            return Optional.empty();
        var type = CARecipes.LIQUID_BURNING_TYPE.get();
        if (type == null) return Optional.empty();

        return level.getRecipeManager().getRecipeFor(type, new FluidRecipeWrapper(stack), level);
    }
}
