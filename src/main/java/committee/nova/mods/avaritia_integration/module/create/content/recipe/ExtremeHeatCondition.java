package committee.nova.mods.avaritia_integration.module.create.content.recipe;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.create.content.extreme_burner.ExtremeBlazeBurnerBlock;
import net.createmod.catnip.lang.Lang;

public enum ExtremeHeatCondition {
    NORMAL(0xffffff), BLAZE(0xE88300), STAR(0x5C93E8);

    private int color;

    private ExtremeHeatCondition(int color) {
        this.color = color;
    }

    public boolean testBlazeBurner(ExtremeBlazeBurnerBlock.ExtremeHeatLevel level) {
        if (this == STAR) return level == ExtremeBlazeBurnerBlock.ExtremeHeatLevel.STAR;
        if (this == BLAZE) return level != ExtremeBlazeBurnerBlock.ExtremeHeatLevel.SMOULDERING;

        return true;
    }

    public ExtremeBlazeBurnerBlock.ExtremeHeatLevel visualizeAsBlazeBurner() {
        if (this == STAR) return ExtremeBlazeBurnerBlock.ExtremeHeatLevel.STAR;
        if (this == BLAZE) return ExtremeBlazeBurnerBlock.ExtremeHeatLevel.BLAZE;

        return ExtremeBlazeBurnerBlock.ExtremeHeatLevel.SMOULDERING;
    }

    public String serialize() {
        return Lang.asId(name());
    }

    public String getTranslationKey() {
        return "recipe.extreme_heat_requirement." + serialize();
    }

    public static ExtremeHeatCondition deserialize(String name) {
        for (ExtremeHeatCondition condition : values()) {
            if (condition.serialize().equals(name)) return condition;
        }

        AvaritiaIntegration.LOGGER.warn("Tried to deserialize invalid heat condition: \"" + name + "\"");
        return NORMAL;
    }

    public int getColor() {
        return color;
    }
}
