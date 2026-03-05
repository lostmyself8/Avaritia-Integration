package committee.nova.mods.avaritia_integration.module.mysticalagriculture;

import committee.nova.mods.avaritia_integration.module.ModMeta;
import committee.nova.mods.avaritia_integration.module.Module;
import committee.nova.mods.avaritia_integration.module.ModuleEntry;
import committee.nova.mods.avaritia_integration.module.mysticalagriculture.registry.MysticalAgradditionsIntegrationBlocks;
import committee.nova.mods.avaritia_integration.module.mysticalagriculture.registry.MysticalAgradditionsIntegrationCrops;
import committee.nova.mods.avaritia_integration.module.mysticalagriculture.registry.MysticalAgradditionsIntegrationItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;

@SuppressWarnings("unused")
@ModuleEntry(id = MysticalAgradditionsModule.MOD_ID, target = @ModMeta(MysticalAgradditionsModule.MOD_ID))
public class MysticalAgradditionsModule implements Module {
    public static final String MOD_ID = "mysticalagradditions";

    @Override
    public void init(IEventBus registryBus) {
        MysticalAgradditionsIntegrationBlocks.BLOCKS.register(registryBus);
        MysticalAgradditionsIntegrationItems.ITEMS.register(registryBus);
    }

    @Override
    public void collectCreativeTabItems(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
        output.accept(MysticalAgradditionsIntegrationCrops.BLAZE_CUBE.getSeedsItem());
        output.accept(MysticalAgradditionsIntegrationCrops.BLAZE_CUBE.getEssenceItem());
        output.accept(MysticalAgradditionsIntegrationCrops.CRYSTAL_MATRIX.getSeedsItem());
        output.accept(MysticalAgradditionsIntegrationCrops.CRYSTAL_MATRIX.getEssenceItem());
        output.accept(MysticalAgradditionsIntegrationCrops.INFINITY.getSeedsItem());
        output.accept(MysticalAgradditionsIntegrationCrops.INFINITY.getEssenceItem());
        output.accept(MysticalAgradditionsIntegrationItems.INFINITY_CRUX.get());
        output.accept(MysticalAgradditionsIntegrationItems.CRYSTAL_MATRIX_CRUX.get());
    }
}
