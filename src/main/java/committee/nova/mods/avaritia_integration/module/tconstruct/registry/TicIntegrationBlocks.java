package committee.nova.mods.avaritia_integration.module.tconstruct.registry;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.botania.registry.BotaniaIntegrationItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EndPortalBlock;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.registration.deferred.BlockDeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class TicIntegrationBlocks{
    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(AvaritiaIntegration.MOD_ID);
    public static RegistryObject<Block> fake_bedrock = BLOCKS.registerNoItem("fake_bedrock", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(1000.0F, 3600000.0F).isValidSpawn((state, level, pos, value) -> false)));
    public static RegistryObject<Block> fake_end_portal_frame = BLOCKS.registerNoItem("fake_end_portal_frame", () -> new EndPortalFrameBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_GREEN)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .sound(SoundType.GLASS)
            .lightLevel((blockState) -> 1)
            .strength(400F, 3600000.0F)));
    public static RegistryObject<Block> fake_end_portal = BLOCKS.registerNoItem("fake_end_portal", () -> new EndPortalBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BLACK)
            .noCollission()
            .lightLevel((state) -> 15)
            .strength(400F, 3600000.0F)
            .pushReaction(PushReaction.BLOCK)));
}
