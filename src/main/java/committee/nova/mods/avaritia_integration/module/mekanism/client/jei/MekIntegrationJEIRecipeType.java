package committee.nova.mods.avaritia_integration.module.mekanism.client.jei;

import committee.nova.mods.avaritia_integration.module.mekanism.api.recipes.chemicals.GasStackToItemStackRecipe;
import committee.nova.mods.avaritia_integration.module.mekanism.common.registry.MekIntegrationBlocks;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.client.jei.MekanismJEIRecipeType;

public class MekIntegrationJEIRecipeType {

    public static final MekanismJEIRecipeType<GasStackToItemStackRecipe> NEUTRON_COLLECTOR = new MekanismJEIRecipeType<>(MekIntegrationBlocks.NEUTRON_COLLECTOR, GasStackToItemStackRecipe.class);
    public static final MekanismJEIRecipeType<ItemStackToItemStackRecipe> NEUTRON_COMPRESSOR = new MekanismJEIRecipeType<>(MekIntegrationBlocks.NEUTRON_COMPRESSOR, ItemStackToItemStackRecipe.class);
}
