package committee.nova.mods.avaritia_integration.init.mixins.mekanism;

import committee.nova.mods.avaritia_integration.module.mekanism.common.network.to_server.MekIntegrationPacketGuiInteract;
import mekanism.common.network.BasePacketHandler;
import mekanism.common.network.PacketHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PacketHandler.class, remap = false)
public abstract class MixinPacketHandler extends BasePacketHandler {

    @Inject(method = "initialize", at = @At(value = "HEAD"))
    public void mixinInitialize(CallbackInfo ci) {
        registerClientToServer(MekIntegrationPacketGuiInteract.class, MekIntegrationPacketGuiInteract::decode);
    }
}
