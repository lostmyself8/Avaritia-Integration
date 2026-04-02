package committee.nova.mods.avaritia_integration.module.gregtech;

import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import committee.nova.mods.avaritia_integration.AvaritiaIntegration;

public class AIGTRegistrate extends GTRegistrate {
    protected AIGTRegistrate() {
        super(AvaritiaIntegration.MOD_ID);
    }

    public static AIGTRegistrate create() {
        return new AIGTRegistrate();
    }
}
