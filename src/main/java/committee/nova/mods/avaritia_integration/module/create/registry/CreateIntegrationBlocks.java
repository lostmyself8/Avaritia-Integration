package committee.nova.mods.avaritia_integration.module.create.registry;

import com.simibubi.create.AllTags;
import com.simibubi.create.api.behaviour.interaction.ConductorBlockInteractionBehavior;
import com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour;
import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import com.simibubi.create.content.processing.burner.BlazeBurnerMovementBehaviour;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import committee.nova.mods.avaritia_integration.module.create.CreateModule;
import committee.nova.mods.avaritia_integration.module.create.content.extreme_burner.ExtremeBlazeBurnerBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.MapColor;

public class CreateIntegrationBlocks {
    private static final CreateRegistrate REGISTRATE = CreateModule.REGISTRATE;
    public static final BlockEntry<ExtremeBlazeBurnerBlock> EXTREME_BLAZE_BURNER;

    public static void register() {
    }

    static {
        EXTREME_BLAZE_BURNER = REGISTRATE.block("extreme_blaze_burner", ExtremeBlazeBurnerBlock::new)
                .initialProperties(SharedProperties::softMetal)
                .properties(p -> p.mapColor(MapColor.COLOR_GRAY).lightLevel(ExtremeBlazeBurnerBlock::getLight))
                .transform(TagGen.pickaxeOnly())
                .addLayer(() -> RenderType::cutoutMipped)
                .tag(AllTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_BLASTING.tag, AllTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_SMOKING.tag,
                        AllTags.AllBlockTags.FAN_TRANSPARENT.tag, AllTags.AllBlockTags.PASSIVE_BOILER_HEATERS.tag)
                .blockstate(ExtremeBlazeBurnerBlock::blockStateDataGen)
                .onRegister(MovementBehaviour.movementBehaviour(new BlazeBurnerMovementBehaviour()))
                .onRegister(MovingInteractionBehaviour.interactionBehaviour(new ConductorBlockInteractionBehavior.BlazeBurner()))
                .item()
                .model(AssetLookup.customBlockItemModel("create", "extreme_blaze_burner", "block_with_blaze"))
                .build()
                .register();
    }
}
