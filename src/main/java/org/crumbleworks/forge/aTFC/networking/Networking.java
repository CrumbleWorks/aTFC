package org.crumbleworks.forge.aTFC.networking;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.crumbleworks.forge.aTFC.Main;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class Networking {

    private static final String PROTOCOL_VERSION = Main.MOD_ID + ":" + "1.0";
    private static final SimpleChannel CHANNEL = NetworkRegistry
            .newSimpleChannel(
                    new ResourceLocation(Main.MOD_ID, "default_channel"),
                    () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals,
                    PROTOCOL_VERSION::equals);
    private static final AtomicInteger runningId = new AtomicInteger(0);
    private static final Map<Class<? extends Message>, Message> messageImpls = new HashMap<>();

    public static final <T extends Message> void registerMessageType(Class<T> messageType) {
        if(!messageImpls.containsKey(messageType)) {
            try {
                messageImpls.put(messageType, messageType.newInstance());
            } catch(InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("Cannot process Messagetype!");
            }
        }
        
        final Message<T> msg = messageImpls.get(messageType);
        
        CHANNEL.registerMessage(runningId.getAndIncrement(), messageType, msg::encode, msg::decode, (_msg, ctx) -> {
            NetworkDirection dir = ctx.get().getDirection();
            switch(dir) {
                case PLAY_TO_CLIENT:
                    msg.consumeClientSide(_msg, ctx);
                    break;
                case PLAY_TO_SERVER:
                    msg.consumeServerSide(_msg, ctx);
                    break;
                default:
                    throw new RuntimeException("Only able to handle gameplay messages. Don't use for Login related stuff!");
            }
            
            ctx.get().setPacketHandled(true);
        });
    }
    
    public static final void sendToServer(Message msg) {
        CHANNEL.send(PacketDistributor.SERVER.noArg(), msg);
    }
}
