package committee.nova.mods.avaritia_integration.module.create.registry;

import com.simibubi.create.Create;
import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.render.SpriteShiftEntry;
import net.createmod.catnip.render.SpriteShifter;
import net.minecraft.resources.ResourceLocation;

public class CreateIntegrationPartialModels {
    public static final PartialModel EXTREME_BLAZE_INERT = block("extreme_blaze_burner/blaze/inert");
    public static final PartialModel BLAZE_STAR = block("extreme_blaze_burner/blaze/star");
    public static final PartialModel BLAZE_STAR_ACTIVE = block("extreme_blaze_burner/blaze/star_active");
    public static final PartialModel BLAZE_BLAZE = block("extreme_blaze_burner/blaze/blaze");
    public static final PartialModel BLAZE_BLAZE_ACTIVE = block("extreme_blaze_burner/blaze/blaze_active");
    public static final PartialModel BLAZE_BURNER_STAR_RODS = block("extreme_blaze_burner/starheated_rods_small");
    public static final PartialModel BLAZE_BURNER_STAR_RODS_2 = block("extreme_blaze_burner/starheated_rods_large");
    public static final PartialModel BLAZE_BURNER_BLAZE_RODS = block("extreme_blaze_burner/blazeheated_rods_small");
    public static final PartialModel BLAZE_BURNER_BLAZE_RODS_2 = block("extreme_blaze_burner/blazeheated_rods_large");

    public static final SpriteShiftEntry STAR_BURNER_FLAME = SpriteShifter.get(Create.asResource("block/blaze_burner_flame"), new ResourceLocation(AvaritiaIntegration.MOD_ID, "block/create/blaze_burner_flame_starheated_scroll"));
    public static final SpriteShiftEntry BLAZE_BURNER_FLAME = SpriteShifter.get(Create.asResource("block/blaze_burner_flame"), new ResourceLocation(AvaritiaIntegration.MOD_ID, "block/create/blaze_burner_flame_blazeheated_scroll"));

    private static PartialModel block(String path) {
        return PartialModel.of(new ResourceLocation(AvaritiaIntegration.MOD_ID, "block/create/" + path));
    }

    public static void init() {
    }
}
