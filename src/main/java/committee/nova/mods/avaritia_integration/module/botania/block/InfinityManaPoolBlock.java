package committee.nova.mods.avaritia_integration.module.botania.block;

import committee.nova.mods.avaritia_integration.module.botania.entity.InfinityManaPoolBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.common.block.block_entity.mana.ManaPoolBlockEntity;
import vazkii.botania.common.block.mana.ManaPoolBlock;

import java.util.List;

/**
 * @author cnlimiter
 */
public class InfinityManaPoolBlock extends ManaPoolBlock {
    public InfinityManaPoolBlock(Variant v, Properties builder) {
        super(v, builder);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter world, List<Component> tooltip, TooltipFlag flag) {
        if (this.variant == ManaPoolBlock.Variant.CREATIVE) {
            tooltip.add(Component.translatable("tooltip.avaritia_integration.infinity_pool").withStyle(ChatFormatting.RED));
            tooltip.add(Component.translatable("botaniamisc.creativePool1").withStyle(ChatFormatting.BLUE));
        }
    }

    @Override
    public @NotNull BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new InfinityManaPoolBlockEntity(pos, state);
    }
}
