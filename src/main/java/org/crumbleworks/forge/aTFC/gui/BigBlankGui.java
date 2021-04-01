package org.crumbleworks.forge.aTFC.gui;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.content.Colors;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public abstract class BigBlankGui extends Screen {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(
            Main.MOD_ID, "textures/gui/generic_gui.png");
    protected int xSize = 176;
    protected int ySize = 166;
    protected int titleX = 8;
    protected int titleY = 6;

    protected int guiLeft;
    protected int guiTop;

    protected BigBlankGui(ITextComponent title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY,
            float partialTicks) {
        int i = this.guiLeft;
        int j = this.guiTop;

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(this.GUI_TEXTURE);
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);

        RenderSystem.disableRescaleNormal();
        RenderSystem.disableDepthTest();
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        RenderSystem.pushMatrix();
        RenderSystem.translatef((float)i, (float)j, 0.0F);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableRescaleNormal();
        RenderSystem.glMultiTexCoord2f(33986, 240.0F, 240.0F);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.font.func_243248_b(matrixStack, this.title, (float)this.titleX,
                (float)this.titleY, Colors.GUI_DARK);
        this.drawGuiForegroundLayer(matrixStack, mouseX - i, mouseY - j);

        RenderSystem.popMatrix();
        RenderSystem.enableDepthTest();
    }

    protected abstract void drawGuiForegroundLayer(MatrixStack matrixStack,
            int mouseX, int mouseY);

    protected final void sunkenHLine(MatrixStack matrixStack, int minX,
            int maxX, int y) {
        hLine(matrixStack, minX, maxX - 1, y, Colors.GUI_DARK);
        hLine(matrixStack, minX + 1, maxX, y + 1, Colors.WHITE);
    }

    protected final void writeText(MatrixStack matrixStack,
            ITextComponent text, int x, int y, int color) {
        this.font.func_243248_b(matrixStack, text, x, y, color);
    }

    protected final void writeTextSmall(MatrixStack matrixStack,
            ITextComponent text, int x, int y, int color) {
        RenderSystem.pushMatrix();
        RenderSystem.translatef(x, y, 0.0f);
        RenderSystem.scalef(0.5f, 0.5f, 0.0f);
        this.font.func_243248_b(matrixStack, text, 0.0f, 0.0f, color);
        RenderSystem.popMatrix();
    }
}
