package org.crumbleworks.forge.aTFC.content.gamelogic.nonblockplaceing;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class WorldItemPlacerTER extends TileEntityRenderer<WorldItemPlacerTE> {

    public WorldItemPlacerTER(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(WorldItemPlacerTE arg0, float arg1, MatrixStack arg2, IRenderTypeBuffer arg3, int arg4, int arg5) {
    }
}
