package committee.nova.mods.avaritia_integration.module.botania.registry;

import committee.nova.mods.avaritia.init.registry.ModRarities;
import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.botania.block.AsgardDandelionBlock;
import committee.nova.mods.avaritia_integration.module.botania.block.InfinityTinyPotatoBlock;
import committee.nova.mods.avaritia_integration.module.botania.block.SoarleanderBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.block.FloatingSpecialFlowerBlock;
import vazkii.botania.common.block.mana.ManaPoolBlock;

import java.util.function.Function;
import java.util.function.Supplier;

public final class BotaniaIntegrationBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(Registries.BLOCK, AvaritiaIntegration.MOD_ID);

    public static final BlockBehaviour.Properties SOARLEANDER_FLOWER_PROPS = BotaniaBlocks.FLOATING_PROPS.lightLevel(level -> 5);

    public static final Block asgard_dandelion = new AsgardDandelionBlock(MobEffects.HUNGER, 0, BotaniaBlocks.FLOATING_PROPS, () -> BotaniaIntegrationBlockEntities.ASGARD);
    public static final Block asgard_dandelion_floating = new FloatingSpecialFlowerBlock(BotaniaBlocks.FLOATING_PROPS, () -> BotaniaIntegrationBlockEntities.ASGARD);
    public static final Block potted_asgard_dandelion = flowerPot(asgard_dandelion, 15);
    public static final Block soarleander = new SoarleanderBlock(MobEffects.WITHER, 1, SOARLEANDER_FLOWER_PROPS, () -> BotaniaIntegrationBlockEntities.SOARLEANDER);
    public static final Block soarleander_floating = new FloatingSpecialFlowerBlock(SOARLEANDER_FLOWER_PROPS, () -> BotaniaIntegrationBlockEntities.SOARLEANDER);
    public static final Block potted_soarleander = flowerPot(soarleander, 5);

    public static final RegistryObject<Block> ASGARD_DANDELION = register("asgard_dandelion", () -> asgard_dandelion);
    public static final RegistryObject<Block> ASGARD_DANDELION_FLOATING = register("asgard_dandelion_floating", () -> asgard_dandelion_floating);
    public static final RegistryObject<Block> POTTED_ASGARD_DANDELION = register("potted_asgard_dandelion", false, () -> potted_asgard_dandelion);
    public static final RegistryObject<Block> SOARLEANDER = register("soarleander", () -> soarleander);
    public static final RegistryObject<Block> SOARLEANDER_FLOATING = register("soarleander_floating", () -> soarleander_floating);
    public static final RegistryObject<Block> POTTED_SOARLEANDER = register("potted_soarleander", false, () -> potted_soarleander);
    public static final RegistryObject<Block> INFINITY_MANA_POOL = register("infinity_mana_pool", () -> new ManaPoolBlock(ManaPoolBlock.Variant.CREATIVE, BlockBehaviour.Properties.copy(BotaniaBlocks.livingrock)), new Item.Properties().rarity(ModRarities.COSMIC));
    public static final RegistryObject<Block> INFINITY_POTATO = register("infinity_potato", InfinityTinyPotatoBlock::new);

    private static <T extends Block> RegistryObject<T> register(String id, Supplier<T> obj) {
        return register(id, obj, true, b -> new BlockItem(b, new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> register(String id, boolean hasItem, Supplier<T> obj) {
        return register(id, obj, hasItem, b -> new BlockItem(b, new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> register(String id, Supplier<T> obj, Item.Properties properties) {
        return register(id, obj, true, b -> new BlockItem(b, properties));
    }

    private static <T extends Block> RegistryObject<T> register(String id, Supplier<T> obj, boolean hasItem, Item.Properties properties) {
        return register(id, obj, hasItem, b -> new BlockItem(b, properties));
    }

    private static <T extends Block> RegistryObject<T> register(String id, Supplier<T> obj, boolean hasItem, Function<Block, Item> itemBuilder) {
        RegistryObject<T> r = REGISTRY.register(id, obj);
        if (hasItem) BotaniaIntegrationItems.register(id, () -> itemBuilder.apply(r.get()));
        return r;
    }

    static FlowerPotBlock flowerPot(Block block, int lightLevel) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);
        return new FlowerPotBlock(block, lightLevel > 0 ? properties.lightLevel((blockState) -> lightLevel) : properties);
    }

}
