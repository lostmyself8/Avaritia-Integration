package committee.nova.mods.avaritia_integration.module.create.compat.cca;

import com.simibubi.create.foundation.fluid.SmartFluidTank;
import committee.nova.mods.avaritia_integration.module.create.compat.cca.liquid_burning.FluidRecipeWrapper;
import committee.nova.mods.avaritia_integration.module.create.compat.cca.liquid_burning.LiquidBurningRecipe;
import committee.nova.mods.avaritia_integration.module.create.content.extreme_burner.ExtremeBlazeBurnerBlockEntity;
import committee.nova.mods.avaritia_integration.module.create.registry.CreateIntegrationRecipeTypes;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

import java.util.Optional;

public class CCALiquidBlazeBurnerCompat implements ICCABurnerCompat {
    private final ExtremeBlazeBurnerBlockEntity be;
    private final SmartFluidTank fluidInventory;
    private Optional<LiquidBurningRecipe> recipeCache = Optional.empty();

    public CCALiquidBlazeBurnerCompat(ExtremeBlazeBurnerBlockEntity be) {
        this.be = be;
        this.fluidInventory = new SmartFluidTank(4000, this::update);
        this.fluidInventory.setValidator(stack -> find(stack, be.getLevel()).isPresent());
    }

    @Override
    public SmartFluidTank getFluidInventory() {
        return fluidInventory;
    }

    public void update(FluidStack stack) {
        if (!be.hasLevel())
            return;
        if (be.getLevel().isClientSide())
            return;
        if (!stack.getFluid().isSame(be.lastFluid))
            recipeCache = find(stack, be.getLevel());
        be.lastFluid = stack.getFluid();
    }

    public Optional<LiquidBurningRecipe> find(FluidStack stack, Level level) {
        if (stack == null)
            return Optional.empty();
        if (level == null)
            return Optional.empty();
        var type = CreateIntegrationRecipeTypes.LIQUID_BURNING.getType();
        if (type == null) return Optional.empty();

        return CreateIntegrationRecipeTypes.LIQUID_BURNING.find(new FluidRecipeWrapper(stack), level);
    }

    public Optional<LiquidBurningRecipe> getRecipeCache() {
        return recipeCache;
    }
}
