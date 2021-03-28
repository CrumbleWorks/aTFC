package org.crumbleworks.forge.aTFC.content.gamelogic.playerdata;

import java.util.function.Supplier;

import org.crumbleworks.forge.aTFC.networking.Message;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class StatCheckMessage implements Message<StatCheckMessage> {

    @Override
    public void encode(StatCheckMessage msg, PacketBuffer buffer) {}

    @Override
    public StatCheckMessage decode(PacketBuffer buffer) {
        return new StatCheckMessage();
    }

    @Override
    public void consumeClientSide(StatCheckMessage msg,
            Supplier<Context> ctx) {
        throw new UnsupportedOperationException(
                "This Message must only ever be sent from Client to Server!");
    }

    @Override
    public void consumeServerSide(StatCheckMessage msg,
            Supplier<Context> ctx) {
        PlayerEntity player = ctx.get().getSender();
        player.addStat(WiringAndEvents.PRESSED_K);
    }
}
