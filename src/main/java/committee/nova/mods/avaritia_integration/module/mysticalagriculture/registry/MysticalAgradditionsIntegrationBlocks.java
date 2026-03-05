package committee.nova.mods.avaritia_integration.module.mysticalagriculture.registry;

import com.blakebr0.cucumber.block.BaseBlock;
import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class MysticalAgradditionsIntegrationBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, AvaritiaIntegration.MOD_ID);
    public static final RegistryObject<Block> INFINITY_CRUX = BLOCKS.register("infinity_crux", () -> new BaseBlock(SoundType.STONE, 5.0F, 10.0F));
    public static final RegistryObject<Block> CRYSTAL_MATRIX_CRUX = BLOCKS.register("crystal_matrix_crux", () -> new BaseBlock(SoundType.STONE, 5.0F, 10.0F));
}
