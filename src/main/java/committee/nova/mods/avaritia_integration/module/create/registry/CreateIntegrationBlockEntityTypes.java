package committee.nova.mods.avaritia_integration.module.create.registry;

import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import committee.nova.mods.avaritia_integration.module.create.CreateModule;
import committee.nova.mods.avaritia_integration.module.create.content.extreme_basin.ExtremeBasinBlockEntity;
import committee.nova.mods.avaritia_integration.module.create.content.extreme_basin.ExtremeBasinRenderer;
import committee.nova.mods.avaritia_integration.module.create.content.extreme_burner.ExtremeBlazeBurnerBlockEntity;
import committee.nova.mods.avaritia_integration.module.create.content.extreme_burner.ExtremeBlazeBurnerRenderer;
import committee.nova.mods.avaritia_integration.module.create.content.extreme_burner.ExtremeBlazeBurnerVisual;
import committee.nova.mods.avaritia_integration.module.create.content.extreme_crusher.ExtremeCrushingWheelBlockEntity;
import committee.nova.mods.avaritia_integration.module.create.content.extreme_crusher.ExtremeCrushingWheelControllerBlockEntity;
import committee.nova.mods.avaritia_integration.module.create.content.extreme_depot.ExtremeDepotBlockEntity;
import committee.nova.mods.avaritia_integration.module.create.content.extreme_depot.ExtremeDepotRenderer;
import committee.nova.mods.avaritia_integration.module.create.content.extreme_fan.ExtremeEncasedFanBlockEntity;
import committee.nova.mods.avaritia_integration.module.create.content.extreme_fan.ExtremeEncasedFanRenderer;
import committee.nova.mods.avaritia_integration.module.create.content.extreme_fan.ExtremeFanVisual;
import committee.nova.mods.avaritia_integration.module.create.content.matrix_mixer.MatrixMechanicalMixerBlockEntity;
import committee.nova.mods.avaritia_integration.module.create.content.matrix_mixer.MatrixMechanicalMixerBlockRenderer;
import committee.nova.mods.avaritia_integration.module.create.content.matrix_mixer.MatrixMechanicalMixerVisual;
import committee.nova.mods.avaritia_integration.module.create.content.neutron_press.NeutronMechanicalPressBlockEntity;
import committee.nova.mods.avaritia_integration.module.create.content.neutron_press.NeutronMechanicalPressRenderer;
import committee.nova.mods.avaritia_integration.module.create.content.neutron_press.NeutronMechanicalPressVisual;

public class CreateIntegrationBlockEntityTypes {
    private static final CreateRegistrate REGISTRATE = CreateModule.REGISTRATE;
    public static final BlockEntityEntry<ExtremeBlazeBurnerBlockEntity> EXTREME_HEATER;
    public static final BlockEntityEntry<NeutronMechanicalPressBlockEntity> NEUTRON_MECHANICAL_PRESS;
    public static final BlockEntityEntry<ExtremeBasinBlockEntity> EXTREME_BASIN;
    public static final BlockEntityEntry<MatrixMechanicalMixerBlockEntity> MATRIX_MECHANICAL_MIXER;
    public static final BlockEntityEntry<ExtremeDepotBlockEntity> EXTREME_DEPOT;
    public static final BlockEntityEntry<ExtremeEncasedFanBlockEntity> EXTREME_ENCASED_FAN;
    public static final BlockEntityEntry<ExtremeCrushingWheelBlockEntity> EXTREME_CRUSHING_WHEEL;
    public static final BlockEntityEntry<ExtremeCrushingWheelControllerBlockEntity> EXTREME_CRUSHING_WHEEL_CONTROLLER;

    public static void register() {
    }

    static {
        EXTREME_HEATER = REGISTRATE.blockEntity("extreme_blaze_heater", ExtremeBlazeBurnerBlockEntity::new)
                .visual(() -> ExtremeBlazeBurnerVisual::new, false)
                .validBlocks(CreateIntegrationBlocks.EXTREME_BLAZE_BURNER)
                .renderer(() -> ExtremeBlazeBurnerRenderer::new)
                .register();

        NEUTRON_MECHANICAL_PRESS = REGISTRATE.blockEntity("neutron_mechanical_press", NeutronMechanicalPressBlockEntity::new)
                .visual(() -> NeutronMechanicalPressVisual::new)
                .validBlocks(CreateIntegrationBlocks.NEUTRON_MECHANICAL_PRESS)
                .renderer(() -> NeutronMechanicalPressRenderer::new)
                .register();
        EXTREME_BASIN = REGISTRATE.blockEntity("extreme_basin", ExtremeBasinBlockEntity::new)
                .validBlocks(CreateIntegrationBlocks.EXTREME_BASIN)
                .renderer(() -> ExtremeBasinRenderer::new)
                .register();
        MATRIX_MECHANICAL_MIXER = REGISTRATE.blockEntity("matrix_mechanical_mixer", MatrixMechanicalMixerBlockEntity::new)
                .visual(() -> MatrixMechanicalMixerVisual::new)
                .validBlocks(CreateIntegrationBlocks.MATRIX_MECHANICAL_MIXER)
                .renderer(() -> MatrixMechanicalMixerBlockRenderer::new)
                .register();
        EXTREME_DEPOT = REGISTRATE.blockEntity("extreme_depot", ExtremeDepotBlockEntity::new)
                .validBlocks(CreateIntegrationBlocks.EXTREME_DEPOT)
                .renderer(() -> ExtremeDepotRenderer::new)
                .register();

        EXTREME_ENCASED_FAN = REGISTRATE.blockEntity("extreme_encased_fan", ExtremeEncasedFanBlockEntity::new)
                .visual(() -> ExtremeFanVisual::new, false)
                .validBlocks(CreateIntegrationBlocks.EXTREME_ENCASED_FAN)
                .renderer(() -> ExtremeEncasedFanRenderer::new)
                .register();

        EXTREME_CRUSHING_WHEEL = REGISTRATE.blockEntity("extreme_crushing_wheel", ExtremeCrushingWheelBlockEntity::new)
                .visual(() -> SingleAxisRotatingVisual.of(CreateIntegrationPartialModels.EXTREME_CRUSHING_WHEEL), false)
                .validBlocks(CreateIntegrationBlocks.EXTREME_CRUSHING_WHEEL)
                .renderer(() -> KineticBlockEntityRenderer::new)
                .register();

        EXTREME_CRUSHING_WHEEL_CONTROLLER = REGISTRATE.blockEntity("extreme_crushing_wheel_controller", ExtremeCrushingWheelControllerBlockEntity::new)
                .validBlocks(CreateIntegrationBlocks.EXTREME_CRUSHING_WHEEL_CONTROLLER)
                .register();
    }
}
