package org.crumbleworks.forge.aTFC.content.gamelogic.playercharacter;

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
public class CharacterCheckMessage implements Message<CharacterCheckMessage> {

    @Override
    public void encode(CharacterCheckMessage msg, PacketBuffer buffer) {}

    @Override
    public CharacterCheckMessage decode(PacketBuffer buffer) {
        return new CharacterCheckMessage();
    }

    @Override
    public void consumeClientSide(CharacterCheckMessage msg,
            Supplier<Context> ctx) {
        throw new UnsupportedOperationException(
                "This Message must only ever be sent from Client to Server!");
    }

    @Override
    public void consumeServerSide(CharacterCheckMessage msg,
            Supplier<Context> ctx) {
        PlayerEntity player = ctx.get().getSender();
        player.addStat(CharacterWiringAndEvents.OPENED_CHARACTER);
    }
}
