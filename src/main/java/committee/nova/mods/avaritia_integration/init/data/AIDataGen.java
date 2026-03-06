package committee.nova.mods.avaritia_integration.init.data;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.init.data.provider.AIRecipes;
import committee.nova.mods.avaritia_integration.module.industrialforegoing.recipe.IndustrialForegoingIntegrationSerializableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import java.util.concurrent.CompletableFuture;

public class AIDataGen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> future = event.getLookupProvider();
        if (event.includeServer()) {
            generator.addProvider(true, new AIRecipes(output));
            generator.addProvider(true, new IndustrialForegoingIntegrationSerializableProvider(generator, AvaritiaIntegration.MOD_ID));
        }
    }
}
