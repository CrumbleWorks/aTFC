package org.crumbleworks.forge.aTFC.content.gamelogic.playerskills;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.networking.Networking;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class StatsScreen extends Screen {

    private static final ITextComponent title = new TranslationTextComponent(
            "gui.atfc.stats.title");

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(
            Main.MOD_ID, "textures/gui/generic_gui.png");
    protected int xSize = 176;
    protected int ySize = 166;
    protected int titleX = 8;
    protected int titleY = 6;

    protected int guiLeft;
    protected int guiTop;

    protected StatsScreen() {
        super(title);
    }

    @Override
    protected void init() {
        Networking.sendToServer(new StatCheckMessage());
        super.init();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY,
            float partialTicks) {
        int i = this.guiLeft;
        int j = this.guiTop;
        this.drawGuiBackgroundLayer(matrixStack, partialTicks, mouseX,
                mouseY);
        RenderSystem.disableRescaleNormal();
        RenderSystem.disableDepthTest();
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        RenderSystem.pushMatrix();
        RenderSystem.translatef((float)i, (float)j, 0.0F);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableRescaleNormal();
        RenderSystem.glMultiTexCoord2f(33986, 240.0F, 240.0F);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawGuiForegroundLayer(matrixStack, mouseX, mouseY);
        RenderSystem.popMatrix();
        RenderSystem.enableDepthTest();
    }

    protected void drawGuiForegroundLayer(MatrixStack matrixStack, int x,
            int y) {
        this.font.func_243248_b(matrixStack, this.title, (float)this.titleX,
                (float)this.titleY, 4210752);
    }

    protected void drawGuiBackgroundLayer(MatrixStack matrixStack,
            float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(this.GUI_TEXTURE);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        InputMappings.Input mouseKey = InputMappings.getInputByCode(keyCode,
                scanCode);
        if(WiringAndEvents.STATS_KEY.isActiveAndMatches(mouseKey)) {
            this.closeScreen();
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
