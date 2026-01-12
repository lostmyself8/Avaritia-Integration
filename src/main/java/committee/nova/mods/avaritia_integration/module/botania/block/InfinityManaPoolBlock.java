package committee.nova.mods.avaritia_integration.module.botania.block;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import org.jetbrains.annotations.Nullable;
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
}
