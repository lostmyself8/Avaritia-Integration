package committee.nova.mods.avaritia_integration.module.mekanism.common;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.text.ILangEntry;
import net.minecraft.Util;

@NothingNullByDefault
public enum MekIntegrationLang implements ILangEntry {
    NEUTRON_COLLECTING("factory", "neutron_collecting"),
    DESCRIPTION_NEUTRON_COLLECTING("description", "neutron_collecting"),
    NEUTRON_COMPRESSING("factory", "singularity_compressing"),
    DESCRIPTION_SINGULARITY_COMPRESSING("description", "singularity_compressing");

    private final String key;

    MekIntegrationLang(String type, String path) {
        this(Util.makeDescriptionId(type, AvaritiaIntegration.rl(path)));
    }

    MekIntegrationLang(String key) {
        this.key = key;
    }

    @Override
    public String getTranslationKey() {
        return key;
    }
}
