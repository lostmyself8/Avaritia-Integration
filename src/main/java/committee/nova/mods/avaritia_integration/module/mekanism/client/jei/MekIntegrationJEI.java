package committee.nova.mods.avaritia_integration.module.mekanism.client.jei;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.mekanism.client.jei.machine.GasStackToItemStackRecipeCategory;
import committee.nova.mods.avaritia_integration.module.mekanism.common.recipe.MekIntegrationRecipeType;
import committee.nova.mods.avaritia_integration.module.mekanism.common.registry.MekIntegrationBlocks;
import mekanism.client.jei.CatalystRegistryHelper;
import mekanism.client.jei.RecipeRegistryHelper;
import mekanism.client.jei.machine.ItemStackToItemStackRecipeCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class MekIntegrationJEI implements IModPlugin {

    @NotNull
    @Override
    public ResourceLocation getPluginUid() {
        return AvaritiaIntegration.rl("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();

        registry.addRecipeCategories(new GasStackToItemStackRecipeCategory(guiHelper, MekIntegrationJEIRecipeType.NEUTRON_COLLECTOR, MekIntegrationBlocks.NEUTRON_COLLECTOR));
        registry.addRecipeCategories(new ItemStackToItemStackRecipeCategory(guiHelper, MekIntegrationJEIRecipeType.NEUTRON_COMPRESSOR, MekIntegrationBlocks.SINGULARITY_COMPRESSOR));
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registry) {
        RecipeRegistryHelper.register(registry, MekIntegrationJEIRecipeType.NEUTRON_COLLECTOR, MekIntegrationRecipeType.COLLECTING);
        RecipeRegistryHelper.register(registry, MekIntegrationJEIRecipeType.NEUTRON_COMPRESSOR, MekIntegrationRecipeType.MEK_COMPRESSING);
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registry) {
        CatalystRegistryHelper.register(registry, MekIntegrationBlocks.NEUTRON_COLLECTOR);
        CatalystRegistryHelper.register(registry, MekIntegrationBlocks.SINGULARITY_COMPRESSOR);
    }
}
