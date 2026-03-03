package committee.nova.mods.avaritia_integration.init.registry;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AIFluids {
    public static final DeferredRegister<Fluid> REGISTRY = DeferredRegister.create(ForgeRegistries.FLUIDS, AvaritiaIntegration.MOD_ID);

    public static final RegistryObject<FlowingFluid> source_molten_blaze = REGISTRY.register("molten_blaze",
            ()->new ForgeFlowingFluid.Source(AIFluids.molten_blaze));
    public static final RegistryObject<FlowingFluid> flowing_molten_blaze = REGISTRY.register("flowing_molten_blaze",
            ()->new ForgeFlowingFluid.Flowing(AIFluids.molten_blaze));
    public static final ForgeFlowingFluid.Properties molten_blaze = new ForgeFlowingFluid.Properties(
            AIFluidTypes.molten_blaze, source_molten_blaze, flowing_molten_blaze).slopeFindDistance(3).levelDecreasePerBlock(3).tickRate(30)
            .block(AIBlocks.molten_blaze_block).bucket(AIItems.MOLTEN_BLAZE_BUCKET);

    public static final RegistryObject<FlowingFluid> source_molten_crystal_matrix = REGISTRY.register("molten_crystal_matrix",
            ()->new ForgeFlowingFluid.Source(AIFluids.molten_crystal_matrix));
    public static final RegistryObject<FlowingFluid> flowing_molten_crystal_matrix = REGISTRY.register("flowing_molten_crystal_matrix",
            ()->new ForgeFlowingFluid.Flowing(AIFluids.molten_crystal_matrix));
    public static final ForgeFlowingFluid.Properties molten_crystal_matrix = new ForgeFlowingFluid.Properties(
            AIFluidTypes.molten_crystal_matrix, source_molten_crystal_matrix, flowing_molten_crystal_matrix).slopeFindDistance(3).levelDecreasePerBlock(3).tickRate(30)
            .block(AIBlocks.molten_crystal_matrix_block).bucket(AIItems.MOLTEN_CRYSTAL_MATRIX_BUCKET);

    public static final RegistryObject<FlowingFluid> source_molten_neutron = REGISTRY.register("molten_neutron",
            ()->new ForgeFlowingFluid.Source(AIFluids.molten_neutron));
    public static final RegistryObject<FlowingFluid> flowing_molten_neutron = REGISTRY.register("flowing_molten_neutron",
            ()->new ForgeFlowingFluid.Flowing(AIFluids.molten_neutron));
    public static final ForgeFlowingFluid.Properties molten_neutron = new ForgeFlowingFluid.Properties(
            AIFluidTypes.molten_neutron, source_molten_neutron, flowing_molten_neutron).slopeFindDistance(3).levelDecreasePerBlock(3).tickRate(30)
            .block(AIBlocks.molten_neutron_block).bucket(AIItems.MOLTEN_NEUTRON_BUCKET);

    public static final RegistryObject<FlowingFluid> source_molten_star = REGISTRY.register("molten_star",
            ()->new ForgeFlowingFluid.Source(AIFluids.molten_star));
    public static final RegistryObject<FlowingFluid> flowing_molten_star = REGISTRY.register("flowing_molten_star",
            ()->new ForgeFlowingFluid.Flowing(AIFluids.molten_star));
    public static final ForgeFlowingFluid.Properties molten_star = new ForgeFlowingFluid.Properties(
            AIFluidTypes.molten_star, source_molten_star, flowing_molten_star).slopeFindDistance(8).levelDecreasePerBlock(8).tickRate(30)
            .block(AIBlocks.molten_star_block).bucket(AIItems.MOLTEN_STAR_BUCKET);

    public static void registers(IEventBus bus) {
        REGISTRY.register(bus);
    }
}
