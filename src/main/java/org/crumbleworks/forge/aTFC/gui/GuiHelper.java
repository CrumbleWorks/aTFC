package org.crumbleworks.forge.aTFC.gui;

import org.crumbleworks.forge.aTFC.content.Colors;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.ITextComponent;

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
}
