package committee.nova.mods.avaritia_integration.module.create.compat;

import net.minecraftforge.fml.ModList;

public class CompatInfo {
    public static final String CCA = "createaddition";

    public static boolean isCCALoaded() {
        return ModList.get().isLoaded(CCA);
    }
}
