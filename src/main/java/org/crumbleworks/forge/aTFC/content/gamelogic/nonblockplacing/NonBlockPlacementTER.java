package org.crumbleworks.forge.aTFC.content.gamelogic.nonblockplacing;

import java.util.OptionalDouble;

import org.crumbleworks.forge.aTFC.utilities.Colors;
import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class NonBlockPlacementTER extends TileEntityRenderer<NonBlockPlacementTE> {

    public NonBlockPlacementTER(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    final static RenderType WIREFRAME = RenderType.makeType("mbe_line_1_depth_writing_on",
            DefaultVertexFormats.POSITION_COLOR, GL11.GL_LINES, 128,
            RenderType.State.getBuilder()
            .line(new RenderState.LineState(OptionalDouble.of(1)))
            .layer(ObfuscationReflectionHelper.getPrivateValue(RenderState.class, null, "field_239235_M_"))
            .transparency(ObfuscationReflectionHelper.getPrivateValue(RenderState.class, null, "field_228510_b_"))
            .target(ObfuscationReflectionHelper.getPrivateValue(RenderState.class, null, "field_241712_U_"))
            .writeMask(new RenderState.WriteMaskState(true, true))
            .build(false));
    
    @Override
    public void render(NonBlockPlacementTE tileEntityIn, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLightIn, int combinedOverlayIn) {
        //FIXME call renderer for each item stored in tileEntityIn, offset to the 4 corners of the block
        
        matrixStack.push(); // push the current transformation matrix + normals matrix

        //TODO impl: https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/mapping-and-modding-tutorials/1571543-forge-rendering-an-item-on-your-block
        ItemEntity entItem = null;
        
        //TODO remove below old render...
        int wireframeColor = Colors.TEXT_PURPLE;
        drawTetrahedronWireframe(matrixStack, buffer, wireframeColor);
        matrixStack.pop(); // restore the original transformation matrix + normals matrix
    }
    
    private static void drawTetrahedronWireframe(MatrixStack matrixStack, IRenderTypeBuffer renderBuffers, int color) {

        final Vector3d[] BASE_VERTICES = {
                new Vector3d(0, 1, 0),
                new Vector3d(1, 1, 0),
                new Vector3d(1, 1, 1),
                new Vector3d(0, 1, 1),
        };
        final Vector3d APEX_VERTEX = new Vector3d(0.5, 0, 0.5);

      IVertexBuilder vertexBuilderLines = renderBuffers.getBuffer(WIREFRAME);

      Matrix4f matrixPos = matrixStack.getLast().getMatrix();  //retrieves the current transformation matrix
      // draw the base
      for (int i = 1; i < BASE_VERTICES.length; ++i) {
        drawLine(matrixPos, vertexBuilderLines, color, BASE_VERTICES[i-1], BASE_VERTICES[i]);
      }
      drawLine(matrixPos, vertexBuilderLines, color, BASE_VERTICES[BASE_VERTICES.length - 1], BASE_VERTICES[0]);

      // draw the sides (from the corners of the base to the apex)
      for (Vector3d baseVertex : BASE_VERTICES) {
        drawLine(matrixPos, vertexBuilderLines, color, APEX_VERTEX, baseVertex);
      }
    }

    private static void drawLine(Matrix4f matrixPos, IVertexBuilder renderBuffer, int color, Vector3d startVertex, Vector3d endVertex) {
        float alpha = (float) (color >> 24 & 255) / 255.0F;
        float red = (float) (color >> 16 & 255) / 255.0F;
        float green = (float) (color >> 8 & 255) / 255.0F;
        float blue = (float) (color & 255) / 255.0F;
        
      renderBuffer.pos(matrixPos, (float) startVertex.getX(), (float) startVertex.getY(), (float) startVertex.getZ())
              .color(red, green, blue, alpha)   // there is also a version for floats (0 -> 1)
              .endVertex();
      renderBuffer.pos(matrixPos, (float) endVertex.getX(), (float) endVertex.getY(), (float) endVertex.getZ())
              .color(red, green, blue, alpha)   // there is also a version for floats (0 -> 1)
              .endVertex();
    }
}
