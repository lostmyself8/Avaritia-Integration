package committee.nova.mods.avaritia_integration.module.gregtech;

import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.gregtech.registry.AIElements;

import static committee.nova.mods.avaritia_integration.module.gregtech.GregtechModule.REGISTRATE;

@GTAddon
public class AIGTAddon implements IGTAddon {

    @Override
    public GTRegistrate getRegistrate() {
        return REGISTRATE;
    }

    @Override
    public void initializeAddon() {

    }

    @Override
    public String addonModId() {
        return AvaritiaIntegration.MOD_ID;
    }

    @Override
    public void registerElements() {
        AIElements.init();
    }
}
