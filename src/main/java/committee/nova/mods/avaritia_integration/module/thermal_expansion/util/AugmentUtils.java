package committee.nova.mods.avaritia_integration.module.thermal_expansion.util;

import cofh.core.util.helpers.AugmentDataHelper;
import cofh.thermal.lib.common.item.AugmentItem;
import committee.nova.mods.avaritia.init.registry.ModRarities;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.function.Supplier;

import static cofh.lib.util.constants.NBTTags.*;

public class AugmentUtils {
    public static final Supplier<AugmentItem> CREATIVE_INTEGRAL_COMPONENTS = () -> new AugmentItem(new Item.Properties().rarity(ModRarities.EPIC), AugmentDataHelper.builder()
            .type(TAG_AUGMENT_TYPE_UPGRADE)
            .mod(TAG_AUGMENT_RF_CREATIVE,1)
            .mod(TAG_AUGMENT_MACHINE_ENERGY, 0).build()
    );
    public static Supplier<AugmentItem> upgradeItem(Rarity rarity, float scaleFactor){
        return () -> new AugmentItem(new Item.Properties().rarity(rarity),
                AugmentDataHelper.builder().type(TAG_AUGMENT_TYPE_UPGRADE).mod(TAG_AUGMENT_BASE_MOD, scaleFactor).build()
        );
    }
}
