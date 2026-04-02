package committee.nova.mods.avaritia_integration.module.gregtech.registry;

import com.gregtechceu.gtceu.api.data.chemical.Element;

public class AIElements {
    public static void init() {}

    public static final Element Neutron = new Element(0, 999, -1, null, "neutron", "Nu", false);
    public static final Element Infinity = new Element(999, 999, -1, null, "infinity", "∞", false);

}
