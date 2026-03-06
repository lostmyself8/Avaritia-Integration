package committee.nova.mods.avaritia_integration.module.create.foundation.data;

import com.simibubi.create.api.data.recipe.MechanicalCraftingRecipeGen;
import committee.nova.mods.avaritia.init.registry.ModItems;
import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

public class AvaritiaMechanicalCraftingRecipeGen extends MechanicalCraftingRecipeGen {
    public AvaritiaMechanicalCraftingRecipeGen(PackOutput output) {
        super(output, AvaritiaIntegration.MOD_ID);
    }

    GeneratedRecipe ENDEST_PEARL = create(ModItems.endest_pearl::get)
            .recipe(b -> b
                    .key('E', Tags.Items.END_STONES)
                    .key('P', Tags.Items.ENDER_PEARLS)
                    .key('S', Tags.Items.NETHER_STARS)
                    .key('N', ModItems.neutron_ingot.get())
                    .patternLine("   EEE   ")
                    .patternLine(" EEPPPEE ")
                    .patternLine(" EPPPPPE ")
                    .patternLine("EPPPNPPPE")
                    .patternLine("EPPNSNPPE")
                    .patternLine("EPPPNPPPE")
                    .patternLine(" EPPPPPE ")
                    .patternLine(" EEPPPEE ")
                    .patternLine("   EEE   ")
            );

    GeneratedRecipe COSMIC_MEATBALLS = create(ModItems.cosmic_meatballs::get)
            .recipe(b -> b
                    .key('A', Items.PORKCHOP).key('B', Items.BEEF).key('C', Items.MUTTON).key('D', Items.COD)
                    .key('E', Items.SALMON).key('F', Items.TROPICAL_FISH).key('G', Items.PUFFERFISH).key('H', Items.RABBIT)
                    .key('I', Items.CHICKEN).key('J', Items.ROTTEN_FLESH).key('K', Items.SPIDER_EYE).key('L', Tags.Items.EGGS)
                    .key('M', ModItems.neutron_nugget.get())
                    .patternLine("ABCD")
                    .patternLine("EFGH")
                    .patternLine("IJKL")
                    .patternLine("M   ")
            );

    GeneratedRecipe ULTIMATE_STEW = create(ModItems.ultimate_stew::get)
            .recipe(b -> b
                    .key('a', Items.APPLE).key('b', Items.GOLDEN_APPLE).key('c', Items.MELON_SLICE).key('d', Items.GLISTERING_MELON_SLICE)
                    .key('e', Items.SWEET_BERRIES).key('f', Items.CHORUS_FRUIT).key('g', Items.CARROT).key('h', Items.GOLDEN_CARROT)
                    .key('i', Items.POTATO).key('j', Items.POISONOUS_POTATO).key('k', Items.BEETROOT).key('l', Items.KELP)
                    .key('m', Items.NETHER_WART).key('n', Items.COCOA_BEANS).key('o', Items.PITCHER_POD).key('p', Items.HONEY_BOTTLE)
                    .key('q', Items.CACTUS).key('r', Items.BAMBOO).key('s', Items.SUGAR_CANE).key('t', Items.SEA_PICKLE)
                    .key('u', Items.BROWN_MUSHROOM).key('v', Items.RED_MUSHROOM).key('w', Items.CRIMSON_FUNGUS).key('x', Items.WARPED_FUNGUS)
                    .key('y', Items.WHEAT).key('z', Items.PUMPKIN).key('#', ModItems.neutron_nugget.get())
                    .patternLine("abcdef")
                    .patternLine("ghijkl")
                    .patternLine("mnopqr")
                    .patternLine("stuvwx")
                    .patternLine("yz#   ")
            );
}
