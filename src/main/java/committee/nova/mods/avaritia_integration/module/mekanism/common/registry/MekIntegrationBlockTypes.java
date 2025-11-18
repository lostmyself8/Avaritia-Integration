package committee.nova.mods.avaritia_integration.module.mekanism.common.registry;

import committee.nova.mods.avaritia_integration.module.mekanism.common.content.blocktype.MekIntegrationBlockShapes;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.TileEntityInfinityAdvancedSolarGenerator;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.TileEntityInfinitySolarGenerator;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.TileEntityNeutronAdvancedSolarGenerator;
import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.TileEntityNeutronSolarGenerator;
import mekanism.api.Upgrade;
import mekanism.api.math.FloatingLong;
import mekanism.common.block.attribute.Attributes;
import mekanism.generators.common.GeneratorsLang;
import mekanism.generators.common.content.blocktype.BlockShapes;
import mekanism.generators.common.content.blocktype.Generator;
import mekanism.generators.common.content.blocktype.Generator.GeneratorBuilder;
import mekanism.generators.common.registries.GeneratorsSounds;

import java.util.EnumSet;

public class MekIntegrationBlockTypes {
    private MekIntegrationBlockTypes() {

    }

    // Infinity Solar Generator
    public static final Generator<TileEntityInfinitySolarGenerator> INFINITY_SOLAR_GENERATOR = GeneratorBuilder
            .createGenerator(() -> MekIntegrationTileEntityTypes.INFINITY_SOLAR_GENERATOR, GeneratorsLang.DESCRIPTION_SOLAR_GENERATOR)
            .withGui(() -> MekIntegrationContainerTypes.INFINITY_SOLAR_GENERATOR)
            .withEnergyConfig(() -> FloatingLong.MAX_VALUE)
            .withCustomShape(MekIntegrationBlockShapes.SOLAR_GENERATOR)
            .withSound(GeneratorsSounds.SOLAR_GENERATOR)
            .withSupportedUpgrades(EnumSet.of(Upgrade.MUFFLING))
            .withComputerSupport("solarGenerator")
            .replace(Attributes.ACTIVE)
            .build();

    // Neutron Solar Generator
    public static final Generator<TileEntityNeutronSolarGenerator> NEUTRON_SOLAR_GENERATOR = GeneratorBuilder
            .createGenerator(() -> MekIntegrationTileEntityTypes.NEUTRON_SOLAR_GENERATOR, GeneratorsLang.DESCRIPTION_SOLAR_GENERATOR)
            .withGui(() -> MekIntegrationContainerTypes.NEUTRON_SOLAR_GENERATOR)
            .withEnergyConfig(() -> FloatingLong.MAX_VALUE)
            .withCustomShape(MekIntegrationBlockShapes.SOLAR_GENERATOR)
            .withSound(GeneratorsSounds.SOLAR_GENERATOR)
            .withSupportedUpgrades(EnumSet.of(Upgrade.MUFFLING))
            .withComputerSupport("solarGenerator")
            .replace(Attributes.ACTIVE)
            .build();

    // Infinity Advanced Solar Generator
    public static final Generator<TileEntityInfinityAdvancedSolarGenerator> INFINITY_ADVANCED_SOLAR_GENERATOR = GeneratorBuilder
            .createGenerator(() -> MekIntegrationTileEntityTypes.INFINITY_ADVANCED_SOLAR_GENERATOR, GeneratorsLang.DESCRIPTION_ADVANCED_SOLAR_GENERATOR)
            .withGui(() -> MekIntegrationContainerTypes.INFINITY_ADVANCED_SOLAR_GENERATOR)
            .withEnergyConfig(() -> FloatingLong.MAX_VALUE)
            .withCustomShape(BlockShapes.ADVANCED_SOLAR_GENERATOR)
            .withSound(GeneratorsSounds.SOLAR_GENERATOR)
            .withSupportedUpgrades(EnumSet.of(Upgrade.MUFFLING))
            .withBounding((pos, state, builder) -> {
                builder.add(pos.above());
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        builder.add(pos.offset(x, 2, z));
                    }
                }
            })
            .withComputerSupport("advancedSolarGenerator")
            .replace(Attributes.ACTIVE)
            .build();

    // Neutron Advanced Solar Generator
    public static final Generator<TileEntityNeutronAdvancedSolarGenerator> NEUTRON_ADVANCED_SOLAR_GENERATOR = GeneratorBuilder
            .createGenerator(() -> MekIntegrationTileEntityTypes.NEUTRON_ADVANCED_SOLAR_GENERATOR, GeneratorsLang.DESCRIPTION_ADVANCED_SOLAR_GENERATOR)
            .withGui(() -> MekIntegrationContainerTypes.NEUTRON_ADVANCED_SOLAR_GENERATOR)
            .withEnergyConfig(() -> FloatingLong.MAX_VALUE)
            .withCustomShape(BlockShapes.ADVANCED_SOLAR_GENERATOR)
            .withSound(GeneratorsSounds.SOLAR_GENERATOR)
            .withSupportedUpgrades(EnumSet.of(Upgrade.MUFFLING))
            .withBounding((pos, state, builder) -> {
                builder.add(pos.above());
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        builder.add(pos.offset(x, 2, z));
                    }
                }
            })
            .withComputerSupport("advancedSolarGenerator")
            .replace(Attributes.ACTIVE)
            .build();
}
