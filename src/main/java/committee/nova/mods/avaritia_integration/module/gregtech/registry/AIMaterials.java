package committee.nova.mods.avaritia_integration.module.gregtech.registry;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialRegistryEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.BlastProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.fluids.FluidBuilder;
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;
import com.gregtechceu.gtceu.common.unification.material.MaterialRegistryManager;
import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.init.registry.AIFluids;
import committee.nova.mods.avaritia_integration.module.gregtech.AIGTRegistrate;
import committee.nova.mods.avaritia_integration.module.gregtech.GregtechModule;
import committee.nova.mods.avaritia_integration.module.tconstruct.registry.TicIntegrationFluids;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.fml.ModList;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.GENERATE_BOLT_SCREW;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.GENERATE_FOIL;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.GENERATE_FRAME;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.GENERATE_ROTOR;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.GENERATE_SMALL_GEAR;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.GENERATE_SPRING;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.GENERATE_SPRING_SMALL;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.block;
import static committee.nova.mods.avaritia.init.registry.ModBlocks.*;
import static committee.nova.mods.avaritia.init.registry.ModItems.*;
import static committee.nova.mods.avaritia.init.registry.ModItems.infinity_nugget;
import static committee.nova.mods.avaritia_integration.init.registry.AIItems.*;
import static committee.nova.mods.avaritia_integration.module.gregtech.GregtechModule.REGISTRATE;


public class AIMaterials {
    static {
        REGISTRATE.creativeModeTab(() -> AICreativeModeTabs.ITEM);
    }


    public static Material Neutron;
    public static Material Infinity;
    public static Material CrystalMatrix;
    public static Material Blaze_Cube;
    public static Material Star_Fuel;

    public static void init(MaterialEvent event) {
        Neutron = new Material.Builder(AvaritiaIntegration.rl("neutron"))
                .ingot()
                .liquid(new FluidBuilder().textures(true, true))
                .blastTemp(21800, BlastProperty.GasTier.HIGHEST)
                .element(AIElements.Neutron)
                .color(0xf4f4f4)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_BOLT_SCREW,
                        GENERATE_FOIL, GENERATE_ROTOR, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_FRAME)
                .buildAndRegister();

        Infinity = new Material.Builder(AvaritiaIntegration.rl("infinity"))
                .ingot()
                .liquid(new FluidBuilder().textures(true, true))
                .plasma(new FluidBuilder().textures(true, true))
                .fluid(FluidStorageKeys.MOLTEN, new FluidBuilder().textures(true, true))
                .blastTemp(21800, BlastProperty.GasTier.HIGHEST)
                .element(AIElements.Infinity)
                .color(0xf4f4f4)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_BOLT_SCREW,
                        GENERATE_FOIL, GENERATE_ROTOR, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_FRAME)
                .buildAndRegister();

        CrystalMatrix = new Material.Builder(AvaritiaIntegration.rl("crystal_matrix"))
                .ingot()
                .liquid(new FluidBuilder().textures(true, true))
                .plasma()
                .blastTemp(21800, BlastProperty.GasTier.HIGHEST)
                .color(0xf4f4f4)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_BOLT_SCREW,
                        GENERATE_FOIL, GENERATE_ROTOR, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_FRAME)
                .formula("Ψ₁₂C₄₈(SbP₈)₆E₈")
                .buildAndRegister();

        Blaze_Cube = new Material.Builder(AvaritiaIntegration.rl("blaze_cube"))
                .ingot()
                .liquid(new FluidBuilder().textures(true, true))
                .plasma()
                .blastTemp(21800, BlastProperty.GasTier.HIGHEST)
                .color(0xf4f4f4)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_BOLT_SCREW,
                        GENERATE_FOIL, GENERATE_ROTOR, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_FRAME)
                .formula("Ca₂WC₂S₄")
                .buildAndRegister();

        Star_Fuel = new Material.Builder(AvaritiaIntegration.rl("star_fuel"))
                .gem()
                .color(0xf4f4f4)
                .formula("C")
                .buildAndRegister();

        excludeAllGems(Star_Fuel, star_fuel::get);
        block.setIgnored(Star_Fuel, star_fuel_block);

        ingot.setIgnored(Neutron, neutron_ingot);
        nugget.setIgnored(Neutron, neutron_nugget);
        block.setIgnored(Neutron, neutron);

        gear.setIgnored(Neutron, neutron_gear);

        ingot.setIgnored(CrystalMatrix, crystal_matrix_ingot);
        block.setIgnored(CrystalMatrix, crystal_matrix);

        ingot.setIgnored(Blaze_Cube, blaze_cube);
        block.setIgnored(Blaze_Cube, blaze_cube_block);

        ingot.setIgnored(Infinity, infinity_ingot);
        nugget.setIgnored(Infinity, infinity_nugget);
        block.setIgnored(Infinity, infinity);

        Neutron.getProperty(PropertyKey.FLUID).getStorage().store(FluidStorageKeys.MOLTEN, AIFluids.source_molten_neutron, null);
        CrystalMatrix.getProperty(PropertyKey.FLUID).getStorage().store(FluidStorageKeys.MOLTEN, AIFluids.source_molten_crystal_matrix, null);
        Blaze_Cube.getProperty(PropertyKey.FLUID).getStorage().store(FluidStorageKeys.MOLTEN, AIFluids.source_molten_blaze, null);

        if(ModList.get().isLoaded("tconstruct")){
            Infinity.getProperty(PropertyKey.FLUID).getStorage().store(FluidStorageKeys.MOLTEN, TicIntegrationFluids.molten_infinity, null);
        }
    }

    public static void registerMaterialRegistry(MaterialRegistryEvent event) {
        MaterialRegistryManager.getInstance().createRegistry(AvaritiaIntegration.MOD_ID);
    }

    private static void excludeAllGems(Material material, ItemLike... items) {
        gem.setIgnored(material, items);
        excludeAllGemsButNormal(material);
    }

    private static void excludeAllGemsButNormal(Material material) {
        gemChipped.setIgnored(material);
        gemFlawed.setIgnored(material);
        gemFlawless.setIgnored(material);
        gemExquisite.setIgnored(material);
        dust.setIgnored(material);
        dustSmall.setIgnored(material);
        dustTiny.setIgnored(material);
    }

    public static void replaceItem() {

        // ===== blaze_cube =====
        BLAZE_CUBE_DENSE_PLATE = () -> ChemicalHelper.get(plateDense, Blaze_Cube).getItem();
        BLAZE_CUBE_DUST        = () -> ChemicalHelper.get(dust, Blaze_Cube).getItem();
        BLAZE_CUBE_GEAR        = () -> ChemicalHelper.get(gear, Blaze_Cube).getItem();
        BLAZE_CUBE_NUGGET      = () -> ChemicalHelper.get(nugget, Blaze_Cube).getItem();
        BLAZE_CUBE_PLATE       = () -> ChemicalHelper.get(plate, Blaze_Cube).getItem();
        BLAZE_CUBE_ROD         = () -> ChemicalHelper.get(rod, Blaze_Cube).getItem();
        BLAZE_CUBE_WIRE        = () -> ChemicalHelper.get(wireFine, Blaze_Cube).getItem();

        // ===== crystal_matrix =====
        CRYSTAL_MATRIX_DENSE_PLATE = () -> ChemicalHelper.get(plateDense, CrystalMatrix).getItem();
        CRYSTAL_MATRIX_DUST        = () -> ChemicalHelper.get(dust, CrystalMatrix).getItem();
        CRYSTAL_MATRIX_GEAR        = () -> ChemicalHelper.get(gear, CrystalMatrix).getItem();
        CRYSTAL_MATRIX_NUGGET      = () -> ChemicalHelper.get(nugget, CrystalMatrix).getItem();
        CRYSTAL_MATRIX_PLATE       = () -> ChemicalHelper.get(plate, CrystalMatrix).getItem();
        CRYSTAL_MATRIX_ROD         = () -> ChemicalHelper.get(rod, CrystalMatrix).getItem();
        CRYSTAL_MATRIX_WIRE        = () -> ChemicalHelper.get(wireFine, CrystalMatrix).getItem();

        // ===== infinity =====
        INFINITY_DENSE_PLATE = () -> ChemicalHelper.get(plateDense, Infinity).getItem();
        INFINITY_DUST        = () -> ChemicalHelper.get(dust, Infinity).getItem();
        INFINITY_GEAR        = () -> ChemicalHelper.get(gear, Infinity).getItem();
        INFINITY_PLATE       = () -> ChemicalHelper.get(plate, Infinity).getItem();
        INFINITY_ROD         = () -> ChemicalHelper.get(rod, Infinity).getItem();
        INFINITY_WIRE        = () -> ChemicalHelper.get(wireFine, Infinity).getItem();

        // ===== neutron =====
        NEUTRON_DENSE_PLATE = () -> ChemicalHelper.get(plateDense, Neutron).getItem();
        NEUTRON_DUST        = () -> ChemicalHelper.get(dust, Neutron).getItem();
        NEUTRON_PLATE       = () -> ChemicalHelper.get(plate, Neutron).getItem();
        NEUTRON_ROD         = () -> ChemicalHelper.get(rod, Neutron).getItem();
        NEUTRON_WIRE        = () -> ChemicalHelper.get(wireFine, Neutron).getItem();
    }
}
