package committee.nova.mods.avaritia_integration.module.industrialforegoing.item;

import com.hrznstudio.titanium.api.augment.AugmentTypes;
import com.hrznstudio.titanium.api.augment.IAugmentType;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;

public class AddonInfo {
    private final int tier;
    private final String materialName;
    private final ChatFormatting color;
    private AddonInfo(int tier, String materialName, ChatFormatting color){
        this.tier = tier;
        this.materialName = materialName;
        this.color = color;
    }

    public static AddonInfo create(int tier, String materialName, ChatFormatting color){
        return new AddonInfo(tier,materialName,color);
    }

    public void registry(HashMap<String, RegistryObject<Item>> map, DeferredRegister<Item> register){
        map.put(getId(AugmentTypes.SPEED),register.register(getId(AugmentTypes.SPEED),() -> new ModSpeedAddonItem(tier, color + getDescription())));
        map.put(getId(AugmentTypes.EFFICIENCY),register.register(getId(AugmentTypes.EFFICIENCY),() -> new ModEfficiencyAddonItem(tier, color + getDescription())));
        map.put(getId(ModProcessingAddonItem.PROCESSING),register.register(getId(ModProcessingAddonItem.PROCESSING),() -> new ModProcessingAddonItem(tier, color + getDescription())));
    }

    public String getId(IAugmentType type){
        return type.getType().toLowerCase() + "_addon_" + materialName.toLowerCase();
    }

    public String getDescription() {
        StringBuilder result = new StringBuilder();
        boolean nextUpperCase = true;

        for (int i = 0; i < materialName.length(); i++) {
            char c = materialName.charAt(i);

            if (c == '_') {
                nextUpperCase = true;
            } else {
                if(nextUpperCase){
                    result.append(" ");
                    result.append(Character.toUpperCase(c));
                }else{
                    result.append(Character.toLowerCase(c));
                }
                nextUpperCase = false;
            }
        }

        return result.toString();
    }
}
