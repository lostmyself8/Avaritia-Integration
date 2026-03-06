package committee.nova.mods.avaritia_integration.module.create.compat.cca;

import com.mrh0.createaddition.network.ObservePacket;
import net.minecraft.core.BlockPos;

public class CCACompatHelper {
    public static void sendObservePacket(BlockPos pos, int node) {
        ObservePacket.send(pos, node);
    }
}
