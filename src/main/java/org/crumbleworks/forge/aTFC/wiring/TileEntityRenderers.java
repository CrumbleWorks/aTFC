package org.crumbleworks.forge.aTFC.wiring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

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

    private final List<Runnable> tileEntityRendererRegisterCallbacks = new ArrayList<>();

    public List<Runnable> getTERRCs() {
        return Collections
                .unmodifiableList(tileEntityRendererRegisterCallbacks);
    }

    public <T extends TileEntity> void register(
            TileEntityType<T> type,
            Function<? super TileEntityRendererDispatcher, ? extends TileEntityRenderer<T>> renderer) {
        tileEntityRendererRegisterCallbacks.add(
                () -> ClientRegistry.bindTileEntityRenderer(type, renderer));
    }
}
