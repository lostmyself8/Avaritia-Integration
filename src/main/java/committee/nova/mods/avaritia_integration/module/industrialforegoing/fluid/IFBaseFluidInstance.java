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

    private final Supplier<FluidType> fluidType;
    private final Supplier<Fluid> flowingFluid;
    private final Supplier<Fluid> sourceFluid;
    private final Supplier<Item> bucketFluid;
    private final Supplier<LiquidBlock> blockFluid;

    private final String fluidName;

    public IFBaseFluidInstance(
            DeferredRegister<Item> itemRegister,
            DeferredRegister<Block> blockRegister,
            DeferredRegister<Fluid> fluidRegister,
            DeferredRegister<FluidType> fluidTypeRegister,
            String name,
            FluidType.Properties fluidTypeProperties,
            IClientFluidTypeExtensions renderProperties
    ) {
        this.fluidName = name;

        this.fluidType = fluidTypeRegister.register(name, () ->
                new FluidType(fluidTypeProperties) {
                    @Override
                    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                        consumer.accept(renderProperties);
                    }
                });

        this.sourceFluid = fluidRegister.register(name + "_source",
                () -> new Source(this));

        this.flowingFluid = fluidRegister.register(name + "_flowing",
                () -> new Flowing(this));

        this.blockFluid = blockRegister.register(name,
                () -> new LiquidBlock(
                        (FlowingFluid) sourceFluid.get(),
                        BlockBehaviour.Properties.of()
                                .noCollission()
                                .strength(100f)
                                .noLootTable()
                ));

        this.bucketFluid = itemRegister.register(name + "_bucket",
                () -> new BucketItem(
                        sourceFluid.get(),
                        new Item.Properties().stacksTo(1)
                ));

    }

    public FluidType getFluidType() {
        return fluidType.get();
    }

    public Fluid getFlowingFluid() {
        return flowingFluid.get();
    }

    public Supplier<Fluid> getSourceFluid() {
        return sourceFluid;
    }


    public Item getBucketFluid() {
        return bucketFluid.get();
    }

    public LiquidBlock getBlockFluid() {
        return blockFluid.get();
    }

    public String getFluidName() {
        return fluidName;
    }


    public static class Source extends IFBaseFluid {
        public Source(IFBaseFluidInstance instance) {
            super(instance);
        }

        @Override
        public int getAmount(FluidState state) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState state) {
            return true;
        }
    }


    public static class Flowing extends IFBaseFluid {

        public Flowing(IFBaseFluidInstance instance) {
            super(instance);
            this.registerDefaultState(
                    this.getStateDefinition().any().setValue(LEVEL, 7)
            );
        }

        @Override
        protected void createFluidStateDefinition(
                StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        @Override
        public boolean isSource(FluidState state) {
            return false;
        }
    }
}
