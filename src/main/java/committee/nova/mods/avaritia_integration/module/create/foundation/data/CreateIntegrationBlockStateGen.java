package committee.nova.mods.avaritia_integration.module.create.foundation.data;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.function.Function;

public class CreateIntegrationBlockStateGen {
    public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> horizontalBlockProvider(boolean customItem) {
        return (c, p) -> p.horizontalBlock(c.get(), getBlockModel(customItem, c, p));
    }

    private static <T extends Block> Function<BlockState, ModelFile> getBlockModel(boolean customItem, DataGenContext<Block, T> c, RegistrateBlockstateProvider p) {
        return $ -> customItem ? CreateIntegrationAssetLookup.partialBaseModel(c, p) : CreateIntegrationAssetLookup.standardModel(c, p);
    }
}
