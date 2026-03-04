package committee.nova.mods.avaritia_integration.init.data.provider;

import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEItems;
import committee.nova.mods.avaritia.init.data.provider.recipe.ModExtremeSmithingRecipeBuilder;
import committee.nova.mods.avaritia.init.data.provider.recipe.ModShapedRecipeBuilder;
import committee.nova.mods.avaritia.init.registry.ModItems;
import committee.nova.mods.avaritia_integration.module.ae2.registry.AE2IntegrationItems;
import committee.nova.mods.avaritia_integration.module.enderio.registry.EnderIOIntegrationItems;
import committee.nova.mods.avaritia_integration.module.industrialforegoing.registry.IndustrialForegoingIntegrationItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AIRecipes extends RecipeProvider implements IConditionBuilder {


    public AIRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {

        //Ender IO

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, EnderIOIntegrationItems.INFINITY_GRINDING_BALL.get())
                .pattern(" a ")
                .pattern("aaa")
                .pattern(" a ")
                .define('a', ModItems.infinity_ingot.get())
                .unlockedBy("has_item", has(ModItems.infinity_ingot.get())).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, EnderIOIntegrationItems.NEUTRON_GRINDING_BALL.get())
                .pattern(" a ")
                .pattern("aaa")
                .pattern(" a ")
                .define('a', ModItems.neutron_ingot.get())
                .unlockedBy("has_item", has(ModItems.neutron_ingot.get())).save(consumer);

        //AE2

        ModShapedRecipeBuilder.shaped(RecipeCategory.MISC, AE2IntegrationItems.INFINITY_ME_STORAGE_COMPONENT.get())
                .pattern("  rrrrr  ")
                .pattern(" vesssev ")
                .pattern("reafcfaer")
                .pattern("rsfxzxfsr")
                .pattern("rsczdzcsr")
                .pattern("rsfxzxfsr")
                .pattern("reafcfaer")
                .pattern(" vesssev ")
                .pattern("  rrrrr  ")
                .define('a', AEItems.ENDER_DUST)
                .define('s', AEItems.SKY_DUST)
                .define('d', AEBlocks.CONTROLLER)
                .define('f', AEItems.CELL_COMPONENT_256K)
                .define('z', AEItems.SPATIAL_128_CELL_COMPONENT)
                .define('x', AEItems.MATTER_BALL)
                .define('c', AEItems.SINGULARITY)
                .define('e', ModItems.infinity_catalyst.get())
                .define('r', ModItems.infinity_ingot.get())
                .define('v', ModItems.neutron_gear.get())

                .unlockedBy("has_item", has(ModItems.infinity_ingot.get())).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AE2IntegrationItems.INFINITY_ME_STORAGE_CELL.get())
                .pattern("aq")
                .pattern("s ")
                .define('a', AE2IntegrationItems.INFINITY_ME_STORAGE_COMPONENT.get())
                .define('q', AEItems.FLUID_CELL_HOUSING)
                .define('s', AEItems.ITEM_CELL_HOUSING)
                .unlockedBy("has_item", has(ModItems.neutron_ingot.get())).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AE2IntegrationItems.INFINITY_ME_STORAGE_CELL_BIG.get())
                .pattern("aq")
                .define('a', AE2IntegrationItems.INFINITY_ME_STORAGE_COMPONENT.get())
                .define('q', AE2IntegrationItems.INFINITY_ME_STORAGE_CELL.get())
                .unlockedBy("has_item", has(ModItems.neutron_ingot.get())).save(consumer);

    }
}
