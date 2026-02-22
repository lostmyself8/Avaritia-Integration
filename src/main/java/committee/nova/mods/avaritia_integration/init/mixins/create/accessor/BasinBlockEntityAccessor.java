package committee.nova.mods.avaritia_integration.init.mixins.create.accessor;

import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BasinBlockEntity.class)
public interface BasinBlockEntityAccessor {
    @Accessor("itemCapability")
    LazyOptional<IItemHandlerModifiable> getItemCapability();

}
