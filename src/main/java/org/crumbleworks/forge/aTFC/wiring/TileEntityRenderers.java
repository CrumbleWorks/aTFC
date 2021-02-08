package org.crumbleworks.forge.aTFC.wiring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.crumbleworks.forge.aTFC.content.gamelogic.brickdrying.BrickPlacerTER;
import org.crumbleworks.forge.aTFC.content.gamelogic.nonblockplaceing.NonBlockPlacementTER;

import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.client.registry.ClientRegistry;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class TileEntityRenderers {

    private static final List<Runnable> tileEntityRendererRegisterCallbacks = new ArrayList<>();

    static {
        register(TileEntities.NON_BLOCK_PLACER_TE, NonBlockPlacementTER::new);
        register(TileEntities.BRICK_PLACER_TE, BrickPlacerTER::new);
    }

    public static List<Runnable> getTERRCs() {
        return Collections
                .unmodifiableList(tileEntityRendererRegisterCallbacks);
    }

    private static <T extends TileEntity> void register(
            TileEntityType<T> type,
            Function<? super TileEntityRendererDispatcher, ? extends TileEntityRenderer<T>> renderer) {
        tileEntityRendererRegisterCallbacks.add(
                () -> ClientRegistry.bindTileEntityRenderer(type, renderer));
    }
}
