package committee.nova.mods.avaritia_integration.module.industrialforegoing.fluid;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.*;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class IFBaseFluidInstance {
    private final DeferredRegister<FluidType> fluidType;
    private final DeferredRegister<Fluid> flowingFluid;
    private final DeferredRegister<Fluid> sourceFluid;
    private final DeferredRegister<Item> bucketFluid;
    private final DeferredRegister<Block> blockFluid;
    private final String fluid;

    public IFBaseFluidInstance(DeferredRegister<Item> itemDeferredRegister, DeferredRegister<Block> blockDeferredRegister, DeferredRegister<Fluid> fluidDeferredRegister, DeferredRegister<FluidType> fluidTypeDeferredRegister, String fluid, FluidType.Properties fluidTypeProperties, IClientFluidTypeExtensions renderProperties) {
        this.fluid = fluid;
        this.sourceFluid = fluidDeferredRegister;
        this.flowingFluid = fluidDeferredRegister;
        this.fluidType = fluidTypeDeferredRegister;
        this.bucketFluid = itemDeferredRegister.register(fluid + "_bucket", () -> {
            return new BucketItem(this.sourceFluid, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1));
        });
        this.blockFluid = blockDeferredRegister.register(fluid, () -> {
            return new LiquidBlock(() -> {
                return (FlowingFluid)this.sourceFluid.get();
            }, BlockBehaviour.Properties.of().mapColor(MapColor.WATER).replaceable().noCollission().strength(100.0F).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY));
        });
    }


    public DeferredRegister<FluidType> getFluidType() {
        return this.fluidType;
    }

    public DeferredRegister<Fluid> getFlowingFluid() {
        return this.flowingFluid;
    }

    public Supplier<Fluid> getSourceFluid() {
        return this.sourceFluid;
    }

    public DeferredRegister<Item> getBucketFluid() {
        return this.bucketFluid;
    }

    public DeferredRegister<Block> getBlockFluid() {
        return this.blockFluid;
    }

    public String getFluid() {
        return this.fluid;
    }

    public static class Source<T extends IFBaseFluidInstance> extends IFBaseFluid {
        public Source(T instance) {
            super(instance);
        }

        public int getAmount(@Nonnull FluidState state) {
            return 8;
        }

        public boolean isSource(@Nonnull FluidState state) {
            return true;
        }
    }

    public static class Flowing<T extends IFBaseFluidInstance> extends IFBaseFluid {
        public Flowing(T instance) {
            super(instance);
            this.registerDefaultState(this.getStateDefinition().any().setValue(LEVEL, 7));
        }

        protected void createFluidStateDefinition(StateDefinition.@NotNull Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        public int getAmount(@Nonnull FluidState fluidState) {
            return fluidState.getValue(LEVEL);
        }
    }
}
