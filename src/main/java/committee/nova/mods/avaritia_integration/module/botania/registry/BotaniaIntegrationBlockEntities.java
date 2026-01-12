package committee.nova.mods.avaritia_integration.module.botania.registry;

import committee.nova.mods.avaritia_integration.AvaritiaIntegration;
import committee.nova.mods.avaritia_integration.module.botania.entity.AsgardDandelionBlockEntity;
import committee.nova.mods.avaritia_integration.module.botania.entity.InfinityManaPoolBlockEntity;
import committee.nova.mods.avaritia_integration.module.botania.entity.InfinityTinyPotatoBlockEntity;
import committee.nova.mods.avaritia_integration.module.botania.entity.SoarleanderBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import vazkii.botania.xplat.XplatAbstractions;

import java.util.function.Supplier;

public final class BotaniaIntegrationBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, AvaritiaIntegration.MOD_ID);

    public static final BlockEntityType<AsgardDandelionBlockEntity> ASGARD_DANDELION = XplatAbstractions.INSTANCE.createBlockEntityType(AsgardDandelionBlockEntity::new, BotaniaIntegrationBlocks.asgard_dandelion, BotaniaIntegrationBlocks.asgard_dandelion_floating);
    public static final BlockEntityType<SoarleanderBlockEntity> SOARLEANDER = XplatAbstractions.INSTANCE.createBlockEntityType(SoarleanderBlockEntity::new, BotaniaIntegrationBlocks.soarleander, BotaniaIntegrationBlocks.soarleander_floating);
    public static final BlockEntityType<InfinityManaPoolBlockEntity> INFINITY_MANA_POOL = XplatAbstractions.INSTANCE.createBlockEntityType(InfinityManaPoolBlockEntity::new, BotaniaIntegrationBlocks.infinity_mana_pool);
    public static final BlockEntityType<InfinityTinyPotatoBlockEntity> INFINITY_TINY_POTATO = XplatAbstractions.INSTANCE.createBlockEntityType(InfinityTinyPotatoBlockEntity::new, BotaniaIntegrationBlocks.infinity_potato);

    public static final RegistryObject<BlockEntityType<AsgardDandelionBlockEntity>> ASGARD_DANDELION_BE = register(
            "asgard_dandelion",
            () -> ASGARD_DANDELION
    );
    public static final RegistryObject<BlockEntityType<SoarleanderBlockEntity>> SOARLEANDER_BE = register(
            "soarleander",
            () -> SOARLEANDER
    );
    public static final RegistryObject<BlockEntityType<InfinityManaPoolBlockEntity>> INFINITY_MANA_POOL_BE = register(
            "infinity_mana_pool",
            () -> INFINITY_MANA_POOL
    );
    public static final RegistryObject<BlockEntityType<InfinityTinyPotatoBlockEntity>> INFINITY_TINY_POTATO_BE = register(
            "infinity_tiny_potato",
            () -> INFINITY_TINY_POTATO
    );

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String id, Supplier<BlockEntityType<T>> obj) {
        return REGISTRY.register(id, obj);
    }
}
