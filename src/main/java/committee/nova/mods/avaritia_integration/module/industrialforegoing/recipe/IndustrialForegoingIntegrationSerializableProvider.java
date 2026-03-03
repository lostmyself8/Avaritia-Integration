package committee.nova.mods.avaritia_integration.module.industrialforegoing.recipe;

import com.hrznstudio.titanium.recipe.generator.IJSONGenerator;
import com.hrznstudio.titanium.recipe.generator.IJsonFile;
import com.hrznstudio.titanium.recipe.generator.TitaniumSerializableProvider;
import committee.nova.mods.avaritia_integration.module.industrialforegoing.item.AddonInfo;
import net.minecraft.data.DataGenerator;

import java.util.Map;

public class IndustrialForegoingIntegrationSerializableProvider extends TitaniumSerializableProvider {
    public IndustrialForegoingIntegrationSerializableProvider(DataGenerator generatorIn, String modid) {
        super(generatorIn, modid);
    }

    @Override
    public void add(Map<IJsonFile, IJSONGenerator> map) {
        AddonInfo.dissolutionChamberRecipes.forEach(dissolutionChamberRecipe -> {
            map.put(dissolutionChamberRecipe, dissolutionChamberRecipe);
        });

        AddonInfo.initLaserDrillFluidRecipe();
        AddonInfo.laserDrillFluidRecipes.forEach(laserDrillFluidRecipe -> {
            map.put(laserDrillFluidRecipe, laserDrillFluidRecipe);
        });
    }
}
