package committee.nova.mods.avaritia_integration.init.mixins.create.accessor;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ItemStackHandler.class)
public interface ItemStackHandlerAccessor {
    @Accessor("stacks")
    NonNullList<ItemStack> getStacks();

    @Invoker("onLoad")
    void invokeOnLoad();
}
