package committee.nova.mods.avaritia_integration.module.botania.entity;


import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.mana.ManaBlockType;
import vazkii.botania.api.mana.ManaNetworkAction;
import vazkii.botania.common.block.block_entity.mana.ManaPoolBlockEntity;
import vazkii.botania.common.block.mana.ManaPoolBlock;
import vazkii.botania.common.handler.ManaNetworkHandler;

public class InfinityManaPoolBlockEntity extends ManaPoolBlockEntity {
    public InfinityManaPoolBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public void initManaCapAndNetwork() {
        if (getMaxMana() == -1) {
            this.manaCap = Integer.MAX_VALUE;
        }
        if (!ManaNetworkHandler.instance.isPoolIn(level, this) && !isRemoved()) {
            BotaniaAPI.instance().getManaNetworkInstance().fireManaNetworkEvent(this, ManaBlockType.POOL, ManaNetworkAction.ADD);
        }
    }

    @Override
    public int getCurrentMana() {
        if (getBlockState().getBlock() instanceof ManaPoolBlock) {
            return this.mana;
        }
        return 0;
    }
}
