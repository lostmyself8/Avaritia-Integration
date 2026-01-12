package committee.nova.mods.avaritia_integration.module.tconstruct.registry;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.common.Mod;
import slimeknights.mantle.fluid.InvertedFluid;
import slimeknights.mantle.registration.object.FlowingFluidObject;

import static committee.nova.mods.avaritia_integration.init.registry.AICreativeTabs.CREATIVE_TAB;
import static slimeknights.tconstruct.fluids.block.BurningLiquidBlock.createBurning;

@Mod.EventBusSubscriber(modid = AvaritiaIntegration.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TicIntegrationFluids extends TicRegistry{
    public static final FlowingFluidObject<ForgeFlowingFluid> molten_blaze  = FLUIDS.registerGem("molten_blaze").type(common("molten_blaze").temperature(3300).lightLevel(15)).block(createBurning(MapColor.COLOR_RED,15, 10, 15f)).bucket().flowing();
    public static final FlowingFluidObject<ForgeFlowingFluid> molten_crystal_matrix  = FLUIDS.registerMetal("molten_crystal_matrix").type(common("molten_crystal_matrix").temperature(2800).lightLevel(15)).block(createBurning(MapColor.COLOR_LIGHT_BLUE,15, 10, 10f)).bucket().flowing();
    public static final FlowingFluidObject<ForgeFlowingFluid> molten_neutron  = FLUIDS.registerMetal("molten_neutron").type(common("molten_neutron").temperature(8300).lightLevel(15)).block(createBurning(MapColor.COLOR_BLACK,3, 10, 27f)).bucket().flowing();
    public static final FlowingFluidObject<ForgeFlowingFluid> molten_star  = FLUIDS.registerGem("molten_star").type(common("molten_star").temperature(12300).lightLevel(15)).block(createBurning(MapColor.COLOR_BLUE,15, 10, 45f)).bucket().flowing();
    public static final FlowingFluidObject<InvertedFluid> molten_infinity  = FLUIDS.registerMetal("molten_infinity").invertedType(gas("molten_infinity").temperature(12300).lightLevel(15)).burningBlock(MapColor.COLOR_LIGHT_GRAY,15, 10, 45f).bucket().invertedFlowing();
    private static FluidType.Properties common(String name) {
        return base(name).density(2000);
    }
    private static FluidType.Properties gas(String name) {
        return base(name).density(-2000);
    }
    private static FluidType.Properties base(String name) {
        return FluidType.Properties.create().viscosity(10000)
                .descriptionId("fluid"+ "." + AvaritiaIntegration.MOD_ID + "." + name)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                .canSwim(false).canDrown(false)
                .pathType(BlockPathTypes.LAVA).adjacentPathType(null)
                .motionScale(0.0023333333333333335D);
    }
    @SubscribeEvent
    public static void addToVanillaTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == CREATIVE_TAB.get()) {
            event.accept(molten_blaze);
            event.accept(molten_crystal_matrix);
            event.accept(molten_neutron);
            event.accept(molten_star);
            event.accept(molten_infinity);
        }
    }
}

