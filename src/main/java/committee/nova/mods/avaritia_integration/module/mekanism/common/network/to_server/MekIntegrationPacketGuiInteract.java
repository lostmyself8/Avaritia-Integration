package committee.nova.mods.avaritia_integration.module.mekanism.common.network.to_server;

import committee.nova.mods.avaritia_integration.module.mekanism.common.tile.factory.TileEntityMIFactory;
import mekanism.api.functions.TriConsumer;
import mekanism.common.network.IMekanismPacket;
import mekanism.common.tile.base.TileEntityMekanism;
import mekanism.common.util.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

public class MekIntegrationPacketGuiInteract implements IMekanismPacket {

    private final Type interactionType;

    private MekIntegrationGuiInteraction interaction;
    private BlockPos tilePosition;
    private int extra;

    public MekIntegrationPacketGuiInteract(MekIntegrationGuiInteraction interaction, BlockEntity tile) {
        this(interaction, tile.getBlockPos());
    }

    public MekIntegrationPacketGuiInteract(MekIntegrationGuiInteraction interaction, BlockPos tilePosition) {
        this(interaction, tilePosition, 0);
    }

    public MekIntegrationPacketGuiInteract(MekIntegrationGuiInteraction interaction, BlockPos tilePosition, int extra) {
        this.interactionType = Type.INT;
        this.interaction = interaction;
        this.tilePosition = tilePosition;
        this.extra = extra;
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        Player player = context.getSender();
        if (player != null) {
            TileEntityMekanism tile = WorldUtils.getTileEntity(TileEntityMekanism.class, player.level(), tilePosition);
            if (tile != null) {
                if (interactionType == Type.INT) {
                    interaction.consume(tile, player, extra);
                }
            }
        }
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeEnum(interactionType);
        if (interactionType == Type.INT) {
            buffer.writeEnum(interaction);
            buffer.writeBlockPos(tilePosition);
            buffer.writeVarInt(extra);
        }
    }

    public static MekIntegrationPacketGuiInteract decode(FriendlyByteBuf buffer) {
        return switch (buffer.readEnum(Type.class)) {
            case INT -> new MekIntegrationPacketGuiInteract(buffer.readEnum(MekIntegrationGuiInteraction.class), buffer.readBlockPos(), buffer.readVarInt());
        };
    }

    public enum MekIntegrationGuiInteraction {
        AUTO_SORT_BUTTON((tile, player, extra) -> {
            if (tile instanceof TileEntityMIFactory<?> factory) {
                factory.toggleSorting();
            }
        });

        private final TriConsumer<TileEntityMekanism, Player, Integer> consumerForTile;

        MekIntegrationGuiInteraction(TriConsumer<TileEntityMekanism, Player, Integer> consumerForTile) {
            this.consumerForTile = consumerForTile;
        }

        public void consume(TileEntityMekanism tile, Player player, int extra) {
            consumerForTile.accept(tile, player, extra);
        }
    }

    private enum Type {
        INT;
    }
}
