package committee.nova.mods.avaritia_integration.module.mekanism.common.item.block.machine;

import committee.nova.mods.avaritia_integration.module.mekanism.common.block.attribute.AttributeMekIntegrationFactoryType;
import committee.nova.mods.avaritia_integration.module.mekanism.common.block.prefab.BlockMekIntegrationFactoryMachine.BlockMekIntegrationFactory;
import mekanism.api.text.EnumColor;
import mekanism.common.MekanismLang;
import mekanism.common.block.attribute.Attribute;
import mekanism.common.item.block.machine.ItemBlockMachine;
import mekanism.common.tier.FactoryTier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemBlockMekIntegrationFactory extends ItemBlockMachine {

    public ItemBlockMekIntegrationFactory(BlockMekIntegrationFactory<?> block) {
        super(block);
    }

    @Override
    public FactoryTier getTier() {
        return Attribute.getTier(getBlock(), FactoryTier.class);
    }

    @Override
    protected void addTypeDetails(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        //Should always be present but validate it just in case
        Attribute.ifPresent(getBlock(), AttributeMekIntegrationFactoryType.class, attribute -> tooltip.add(MekanismLang.FACTORY_TYPE.translateColored(EnumColor.INDIGO, EnumColor.GRAY,
                attribute.getMekIntegrationFactoryType())));
        super.addTypeDetails(stack, world, tooltip, flag);
    }
}
