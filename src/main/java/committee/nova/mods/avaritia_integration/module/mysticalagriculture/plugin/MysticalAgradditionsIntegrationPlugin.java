package committee.nova.mods.avaritia_integration.module.mysticalagriculture.plugin;

import com.blakebr0.mysticalagriculture.api.IMysticalAgriculturePlugin;
import com.blakebr0.mysticalagriculture.api.MysticalAgriculturePlugin;
import com.blakebr0.mysticalagriculture.api.crop.Crop;
import com.blakebr0.mysticalagriculture.api.registry.ICropRegistry;
import committee.nova.mods.avaritia_integration.module.mysticalagriculture.registry.MysticalAgradditionsIntegrationBlocks;
import committee.nova.mods.avaritia_integration.module.mysticalagriculture.registry.MysticalAgradditionsIntegrationCrops;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

@SuppressWarnings("unused")
@MysticalAgriculturePlugin
public class MysticalAgradditionsIntegrationPlugin implements IMysticalAgriculturePlugin {
    private static final boolean DEBUG = !FMLEnvironment.production;
    @Override
    public void onRegisterCrops(ICropRegistry registry) {
        registry.register(withRequiredMods(MysticalAgradditionsIntegrationCrops.BLAZE_CUBE,"mysticalagradditions"));
        registry.register(withRequiredMods(MysticalAgradditionsIntegrationCrops.CRYSTAL_MATRIX,"mysticalagradditions"));
        registry.register(withRequiredMods(MysticalAgradditionsIntegrationCrops.INFINITY,"mysticalagradditions"));
    }

    @Override
    public void onPostRegisterCrops(ICropRegistry registry) {
        MysticalAgradditionsIntegrationCrops.INFINITY.setCruxBlock(MysticalAgradditionsIntegrationBlocks.INFINITY_CRUX);
        MysticalAgradditionsIntegrationCrops.CRYSTAL_MATRIX.setCruxBlock(MysticalAgradditionsIntegrationBlocks.CRYSTAL_MATRIX_CRUX);
    }

    private static Crop withRequiredMods(Crop crop, String... mods) {
        if (DEBUG) {
            return crop;
        } else {
            Stream<String> stream = Arrays.stream(mods);
            ModList list = ModList.get();
            Objects.requireNonNull(list);
            boolean enabled = stream.anyMatch(list::isLoaded);
            return crop.setEnabled(enabled);
        }
    }
}
