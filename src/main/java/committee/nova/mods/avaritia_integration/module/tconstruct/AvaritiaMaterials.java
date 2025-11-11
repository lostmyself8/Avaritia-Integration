package committee.nova.mods.avaritia_integration.module.tconstruct;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

public class AvaritiaMaterials {

    public static final MaterialId INFINITY = material("infinity");
    public static final MaterialId NEUTRONIUM = material("neutronium");
    public static final MaterialId CRYSTAL_MATRIX = material("crystal_matrix");
    public static final MaterialId BLAZE_CUBE = material("blaze_cube");

    public static MaterialId material(String name) {
        return new MaterialId(new ResourceLocation(AvaritiaIntegration.MOD_ID, name));
    }

}
