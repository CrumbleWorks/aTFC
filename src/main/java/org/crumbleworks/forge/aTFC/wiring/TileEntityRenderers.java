package org.crumbleworks.forge.aTFC.wiring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.crumbleworks.forge.aTFC.content.gamelogic.nonblockplaceing.NonBlockPlacementTER;

import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class TileEntityRenderers {

    private static final List<TERHolder<? extends TileEntity>> tileEntityRenderers = new ArrayList<>();

    static {
        register(TileEntities.NON_BLOCK_PLACER_TE, NonBlockPlacementTER::new);
    }

    public static List<TERHolder<? extends TileEntity>> getTERs() {
        return Collections.unmodifiableList(tileEntityRenderers);
    }

    private static <T extends TileEntity> void register(
            TileEntityType<T> type,
            Function<? super TileEntityRendererDispatcher, ? extends TileEntityRenderer<T>> renderer) {
        tileEntityRenderers.add(new TERHolder<T>(type, renderer));
    }

    public static class TERHolder<T extends TileEntity> {

        private final TileEntityType<T> type;
        private final Function<? super TileEntityRendererDispatcher, ? extends TileEntityRenderer<T>> renderer;

        public TERHolder(TileEntityType<T> type,
                Function<? super TileEntityRendererDispatcher, ? extends TileEntityRenderer<T>> renderer) {
            this.type = type;
            this.renderer = renderer;
        }

        public TileEntityType<T> getType() {
            return type;
        }

        public Function<? super TileEntityRendererDispatcher, ? extends TileEntityRenderer<T>> getRenderer() {
            return renderer;
        }
    }
}
