package committee.nova.mods.avaritia_integration.module.create.registry;

import com.simibubi.create.AllDisplaySources;
import com.simibubi.create.AllTags;
import com.simibubi.create.api.behaviour.display.DisplaySource;
import com.simibubi.create.api.behaviour.interaction.ConductorBlockInteractionBehavior;
import com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour;
import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import com.simibubi.create.api.contraption.storage.item.MountedItemStorageType;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.logistics.depot.MountedDepotInteractionBehaviour;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import com.simibubi.create.content.processing.basin.BasinMovementBehaviour;
import com.simibubi.create.content.processing.burner.BlazeBurnerMovementBehaviour;
import com.simibubi.create.foundation.data.*;
import com.tterrag.registrate.util.entry.BlockEntry;
import committee.nova.mods.avaritia_integration.module.create.CreateModule;
import committee.nova.mods.avaritia_integration.module.create.foundation.data.CreateIntegrationAssetLookup;
import committee.nova.mods.avaritia_integration.module.create.foundation.data.CreateIntegrationBlockStateGen;
import committee.nova.mods.avaritia_integration.module.create.foundation.data.CreateIntegrationBuilderTransformers;
import committee.nova.mods.avaritia_integration.module.create.content.extreme_basin.ExtremeBasinBlock;
import committee.nova.mods.avaritia_integration.module.create.content.extreme_basin.ExtremeBasinGenerator;
import committee.nova.mods.avaritia_integration.module.create.content.extreme_burner.ExtremeBlazeBurnerBlock;
import committee.nova.mods.avaritia_integration.module.create.content.extreme_depot.ExtremeDepotBlock;
import committee.nova.mods.avaritia_integration.module.create.content.matrix_mixer.MatrixMechanicalMixerBlock;
import committee.nova.mods.avaritia_integration.module.create.content.matrix_mixer.MatrixMechanicalMixerBlockItem;
import committee.nova.mods.avaritia_integration.module.create.content.neutron_press.NeutronMechanicalPressBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

public class CreateIntegrationBlocks {
    private static final CreateRegistrate REGISTRATE = CreateModule.REGISTRATE;
    public static final BlockEntry<ExtremeBlazeBurnerBlock> EXTREME_BLAZE_BURNER;
    public static final BlockEntry<CasingBlock> CRYSTAL_MATRIX_CASING;
    public static final BlockEntry<NeutronMechanicalPressBlock> NEUTRON_MECHANICAL_PRESS;
    public static final BlockEntry<ExtremeBasinBlock> EXTREME_BASIN;
    public static final BlockEntry<MatrixMechanicalMixerBlock> MATRIX_MECHANICAL_MIXER;
    public static final BlockEntry<ExtremeDepotBlock> EXTREME_DEPOT;

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

        CRYSTAL_MATRIX_CASING = REGISTRATE.block("crystal_matrix_casing", CasingBlock::new)
                .properties(p -> p.mapColor(MapColor.COLOR_BLACK))
                .transform(CreateIntegrationBuilderTransformers.casing(() -> CreateIntegrationSpriteShifts.CRYSTAL_MATRIX_CASING))
                .register();

        NEUTRON_MECHANICAL_PRESS = REGISTRATE.block("neutron_mechanical_press", NeutronMechanicalPressBlock::new)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.noOcclusion().mapColor(MapColor.COLOR_BLACK))
                .transform(TagGen.axeOrPickaxe())
                .blockstate(CreateIntegrationBlockStateGen.horizontalBlockProvider(true))
                .onRegister(CreateIntegrationStress.setImpact(16.0))
                .item(AssemblyOperatorBlockItem::new)
                .transform(ModelGen.customItemModel("create", "_", "block"))
                .register();

        EXTREME_BASIN = REGISTRATE.block("extreme_basin", ExtremeBasinBlock::new)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.mapColor(MapColor.COLOR_GRAY)
                        .sound(SoundType.NETHERITE_BLOCK))
                .transform(TagGen.pickaxeOnly())
                .blockstate(new ExtremeBasinGenerator()::generate)
                .addLayer(() -> RenderType::cutoutMipped)
                .onRegister(MovementBehaviour.movementBehaviour(new BasinMovementBehaviour()))
                .item()
                .transform(ModelGen.customItemModel("create", "_", "block"))
                .register();

        MATRIX_MECHANICAL_MIXER = REGISTRATE.block("matrix_mechanical_mixer", MatrixMechanicalMixerBlock::new)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.noOcclusion().mapColor(MapColor.STONE))
                .transform(TagGen.axeOrPickaxe())
                .blockstate((c, p) -> p.simpleBlock(c.getEntry(), CreateIntegrationAssetLookup.partialBaseModel(c, p)))
                .addLayer(() -> RenderType::cutoutMipped)
                .onRegister(CreateIntegrationStress.setImpact(16.0))
                .item(MatrixMechanicalMixerBlockItem::new)
                .transform(ModelGen.customItemModel("create", "_", "block"))
                .register();

        EXTREME_DEPOT = REGISTRATE.block("extreme_depot", ExtremeDepotBlock::new)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.mapColor(MapColor.COLOR_GRAY))
                .transform(TagGen.axeOrPickaxe())
                .blockstate((c, p) -> p.simpleBlock(c.getEntry(), CreateIntegrationAssetLookup.partialBaseModel(c, p)))
                .transform(DisplaySource.displaySource(CreateIntegrationDisplaySources.ITEM_NAMES))
                .onRegister(MovingInteractionBehaviour.interactionBehaviour(new MountedDepotInteractionBehaviour()))
                .transform(MountedItemStorageType.mountedItemStorage(CreateIntegrationMountedStorageTypes.EXTREME_DEPOT))
                .item()
                .transform(ModelGen.customItemModel("create", "_", "block"))
                .register();
    }
}
