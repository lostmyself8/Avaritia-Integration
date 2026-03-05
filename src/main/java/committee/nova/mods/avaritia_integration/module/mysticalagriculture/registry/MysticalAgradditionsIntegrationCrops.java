package committee.nova.mods.avaritia_integration.module.mysticalagriculture.registry;

import com.blakebr0.mysticalagradditions.init.ModCropTiers;
import com.blakebr0.mysticalagriculture.api.crop.Crop;
import com.blakebr0.mysticalagriculture.api.crop.CropTier;
import com.blakebr0.mysticalagriculture.api.crop.CropType;
import com.blakebr0.mysticalagriculture.api.lib.LazyIngredient;
import committee.nova.mods.avaritia_integration.AvaritiaIntegration;

public class MysticalAgradditionsIntegrationCrops {
    public static final Crop BLAZE_CUBE = new Crop(AvaritiaIntegration.rl("blaze_cube"), CropTier.FIVE, CropType.RESOURCE, LazyIngredient.item("avaritia:blaze_cube"));
    public static final Crop CRYSTAL_MATRIX = new Crop(AvaritiaIntegration.rl("crystal_matrix"), ModCropTiers.SIX, CropType.RESOURCE, LazyIngredient.tag("forge:storage_blocks/crystal_matrix"));
    public static final Crop INFINITY = new Crop(AvaritiaIntegration.rl("infinity"), ModCropTiers.SIX, CropType.RESOURCE, LazyIngredient.tag("forge:ingots/infinity"));
}
