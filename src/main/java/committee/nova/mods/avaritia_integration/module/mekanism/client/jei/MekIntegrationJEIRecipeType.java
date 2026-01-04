package committee.nova.mods.avaritia_integration.module.mekanism.client.jei;

import committee.nova.mods.avaritia_integration.module.mekanism.api.recipes.MekCompressorRecipe;
import committee.nova.mods.avaritia_integration.module.mekanism.api.recipes.chemicals.GasStackToItemStackRecipe;
import committee.nova.mods.avaritia_integration.module.mekanism.common.registry.MekIntegrationBlocks;
import mekanism.client.jei.MekanismJEIRecipeType;

public class MekIntegrationJEIRecipeType {

    public static final MekanismJEIRecipeType<GasStackToItemStackRecipe> NEUTRON_COLLECTOR = new MekanismJEIRecipeType<>(MekIntegrationBlocks.NEUTRON_COLLECTOR, GasStackToItemStackRecipe.class);
    public static final MekanismJEIRecipeType<MekCompressorRecipe> NEUTRON_COMPRESSOR = new MekanismJEIRecipeType<>(MekIntegrationBlocks.NEUTRON_COMPRESSOR, MekCompressorRecipe.class);
}
