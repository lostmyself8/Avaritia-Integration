package committee.nova.mods.avaritia_integration.module.tconstruct.registry;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import slimeknights.mantle.fluid.InvertedFluid;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.tconstruct.common.registration.FluidDeferredRegisterExtension;

import static committee.nova.mods.avaritia_integration.init.registry.AICreativeTabs.CREATIVE_TAB;
import static slimeknights.tconstruct.fluids.block.BurningLiquidBlock.createBurning;

public class TicIntegrationFluids{
    public static final FluidDeferredRegisterExtension FLUIDS = new FluidDeferredRegisterExtension(AvaritiaIntegration.MOD_ID);
    public static final FlowingFluidObject<InvertedFluid> molten_infinity  = FLUIDS.registerMetal("molten_infinity").invertedType(gas("molten_infinity").temperature(12300).lightLevel(15)).burningBlock(MapColor.COLOR_LIGHT_GRAY,15, 10, 45f).bucket().invertedFlowing();
    private static FluidType.Properties base(String name) {
        return FluidType.Properties.create().viscosity(10000).density(-2000)
                .descriptionId("fluid"+ "." + AvaritiaIntegration.MOD_ID + "." + name)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                .canSwim(false).canDrown(false)
                .pathType(BlockPathTypes.LAVA).adjacentPathType(null)
                .motionScale(0.0023333333333333335D);
    }
}

