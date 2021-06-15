package org.crumbleworks.forge.aTFC.gui;

import java.util.function.Supplier;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.utilities.Colors;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
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
    protected static final int xSize = 176;
    protected static final int ySize = 166;
    protected static final int titleX = 8;
    protected static final int titleY = 6;

    private final KeyBinding activeGuiBinding;
    private final KeyBinding[] otherGuisBindings;
    private final Supplier<Screen>[] otherGuisWindows;

    protected int guiLeft;
    protected int guiTop;

    protected BigBlankGui(ITextComponent title, KeyBinding activeGuiBinding,
            KeyBinding[] otherGuisBindings,
            Supplier<Screen>[] otherGuisWindows) {
        super(title);

        this.activeGuiBinding = activeGuiBinding;
        this.otherGuisBindings = otherGuisBindings;
        this.otherGuisWindows = otherGuisWindows;
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
        this.minecraft.getTextureManager().bind(this.GUI_TEXTURE);
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

        this.font.draw(matrixStack, this.title, (float)this.titleX,
                (float)this.titleY, Colors.GUI_DARK);
        this.drawGuiForegroundLayer(matrixStack, mouseX - i, mouseY - j);

        RenderSystem.popMatrix();
        RenderSystem.enableDepthTest();
    }

    protected abstract void drawGuiForegroundLayer(MatrixStack matrixStack,
            int mouseX, int mouseY);

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        InputMappings.Input mouseKey = InputMappings.getKey(keyCode,
                scanCode);
        if(activeGuiBinding.isActiveAndMatches(mouseKey)) {
            this.onClose();
            return true;
        }

        for(int i = 0 ; i < otherGuisBindings.length ; i++) {
            if(otherGuisBindings[i].isActiveAndMatches(mouseKey)) {
                this.onClose();
                Minecraft.getInstance()
                        .setScreen(otherGuisWindows[i].get());
                return true;
            }
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
