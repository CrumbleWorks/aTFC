package org.crumbleworks.forge.aTFC.gui;

import org.crumbleworks.forge.aTFC.content.Colors;
import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class GuiHelper {

    public static final void sunkenHLine(MatrixStack matrixStack, int minX,
            int maxX, int y) {
        hLine(matrixStack, minX, maxX - 1, y, Colors.GUI_DARK);
        hLine(matrixStack, minX + 1, maxX, y + 1, Colors.WHITE);
    }

    public static final void hLine(MatrixStack matrixStack, int minX,
            int maxX, int y, int color) {
        if(maxX < minX) {
            int i = minX;
            minX = maxX;
            maxX = i;
        }

        AbstractGui.fill(matrixStack, minX, y, maxX + 1, y + 1, color);
    }

    public static final void vLine(MatrixStack matrixStack, int x, int minY,
            int maxY, int color) {
        if(maxY < minY) {
            int i = minY;
            minY = maxY;
            maxY = i;
        }

        AbstractGui.fill(matrixStack, x, minY + 1, x + 1, maxY, color);
    }

    public static final void writeText(MatrixStack matrixStack,
            FontRenderer font, ITextComponent text, int x, int y,
            int color) {
        font.func_243248_b(matrixStack, text, x, y, color);
    }

    public static final void writeTextSmall(MatrixStack matrixStack,
            FontRenderer font, ITextComponent text, int x, int y,
            int color) {
        RenderSystem.pushMatrix();
        RenderSystem.translatef(x, y, 0.0f);
        RenderSystem.scalef(0.5f, 0.5f, 0.0f);
        font.func_243248_b(matrixStack, text, 0.0f, 0.0f, color);
        RenderSystem.popMatrix();
    }

    public static final void fillGradientTopToBottom(MatrixStack matrixStack,
            int minX, int minY, int maxX, int maxY, int startColor,
            int endColor) {
        GuiUtils.drawGradientRect(matrixStack.getLast().getMatrix(), 0,
                minX, minY, maxX, maxY, startColor, endColor);
    }

    public static final void fillGradientLeftToRight(MatrixStack matrixStack,
            int minX, int minY, int maxX, int maxY, int startColor,
            int endColor) {
        Matrix4f mat = matrixStack.getLast().getMatrix();
        int zLevel = 0;

        float startAlpha = (float) (startColor >> 24 & 255) / 255.0F;
        float startRed = (float) (startColor >> 16 & 255) / 255.0F;
        float startGreen = (float) (startColor >> 8 & 255) / 255.0F;
        float startBlue = (float) (startColor & 255) / 255.0F;
        float endAlpha = (float) (endColor >> 24 & 255) / 255.0F;
        float endRed = (float) (endColor >> 16 & 255) / 255.0F;
        float endGreen = (float) (endColor >> 8 & 255) / 255.0F;
        float endBlue = (float) (endColor & 255) / 255.0F;

        RenderSystem.enableDepthTest();
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(GL11.GL_SMOOTH);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(mat, maxX, minY, zLevel)
                .color(endRed, endGreen, endBlue, endAlpha).endVertex();
        buffer.pos(mat, minX, minY, zLevel)
                .color(startRed, startGreen, startBlue, startAlpha)
                .endVertex();
        buffer.pos(mat, minX, maxY, zLevel)
                .color(startRed, startGreen, startBlue, startAlpha)
                .endVertex();
        buffer.pos(mat, maxX, maxY, zLevel)
                .color(endRed, endGreen, endBlue, endAlpha).endVertex();
        tessellator.draw();

        RenderSystem.shadeModel(GL11.GL_FLAT);
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
    }
}
