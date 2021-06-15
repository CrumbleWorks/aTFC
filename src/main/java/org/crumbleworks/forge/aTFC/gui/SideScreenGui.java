package org.crumbleworks.forge.aTFC.gui;

import org.crumbleworks.forge.aTFC.Main;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public abstract class SideScreenGui extends AbstractGui {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(
            Main.MOD_ID, "textures/gui/side_gui.png");
    protected static final int xSize = 147;
    protected static final int ySize = 166;

    private int xOffset;
    private int width;
    private int height;

    protected Minecraft mc;

    public void init(int widthIn, int heightIn, Minecraft minecraftIn,
            boolean widthTooNarrowIn) {
        this.mc = minecraftIn;
        this.width = widthIn;
        this.height = heightIn;

        this.xOffset = widthTooNarrowIn ? 0 : 86;
    }

    public int updateScreenPosition(boolean isWidthTooNarrow, int hostWidth,
            int hostXSize) {
        int i;
        if(!isWidthTooNarrow) {
            i = 177 + (hostWidth - hostXSize - 200) / 2;
        } else {
            i = (hostWidth - hostXSize) / 2;
        }

        return i;
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY,
            float partialTicks) {
        int i = (this.width - xSize) / 2 - this.xOffset;
        int j = (this.height - ySize) / 2;

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bind(this.GUI_TEXTURE);
        this.blit(matrixStack, i, j, 1, 1, xSize, ySize);

        RenderSystem.disableRescaleNormal();
        RenderSystem.disableDepthTest();
        RenderSystem.pushMatrix();
        RenderSystem.translatef((float)i, (float)j, 0.0F);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableRescaleNormal();
        RenderSystem.glMultiTexCoord2f(33986, 240.0F, 240.0F);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        drawGuiForegroundLayer(matrixStack, mouseX - i, mouseY - j);

        RenderSystem.popMatrix();
        RenderSystem.enableDepthTest();
    }

    protected abstract void drawGuiForegroundLayer(MatrixStack matrixStack,
            int mouseX, int mouseY);
}
