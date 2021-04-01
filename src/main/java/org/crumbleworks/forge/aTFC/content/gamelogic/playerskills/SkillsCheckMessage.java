package org.crumbleworks.forge.aTFC.content.gamelogic.playerskills;

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
public class SkillsCheckMessage implements Message<SkillsCheckMessage> {

    @Override
    public void encode(SkillsCheckMessage msg, PacketBuffer buffer) {}

    @Override
    public SkillsCheckMessage decode(PacketBuffer buffer) {
        return new SkillsCheckMessage();
    }

    @Override
    public void consumeClientSide(SkillsCheckMessage msg,
            Supplier<Context> ctx) {
        throw new UnsupportedOperationException(
                "This Message must only ever be sent from Client to Server!");
    }

    @Override
    public void consumeServerSide(SkillsCheckMessage msg,
            Supplier<Context> ctx) {
        PlayerEntity player = ctx.get().getSender();
        player.addStat(WiringAndEvents.OPENED_SKILLS);
    }
}
