package org.crumbleworks.forge.aTFC.networking;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

/**
 * !Provide an empty constructor for message processing logic!
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public interface Message<T> {

    void encode(T msg, PacketBuffer buffer);
    T decode(PacketBuffer buffer);
    void consumeClientSide(T msg, Supplier<NetworkEvent.Context> ctx);
    void consumeServerSide(T msg, Supplier<NetworkEvent.Context> ctx);
}
