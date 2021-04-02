package org.crumbleworks.forge.aTFC.content.gamelogic.playercharacter;

import java.util.function.Supplier;

import org.crumbleworks.forge.aTFC.content.Colors;
import org.crumbleworks.forge.aTFC.content.gamelogic.playerdata.ATFCPlayerData;
import org.crumbleworks.forge.aTFC.content.gamelogic.playerdata.ATFCPlayerDataCapabilityProvider;
import org.crumbleworks.forge.aTFC.content.gamelogic.playerdata.tastepreferences.TastePreference;
import org.crumbleworks.forge.aTFC.content.gamelogic.playerskills.SkillsScreen;
import org.crumbleworks.forge.aTFC.content.gamelogic.playerskills.SkillsWiringAndEvents;
import org.crumbleworks.forge.aTFC.content.gamelogic.tasteprofile.Taste;
import org.crumbleworks.forge.aTFC.gui.BigBlankGui;
import org.crumbleworks.forge.aTFC.gui.GuiHelper;
import org.crumbleworks.forge.aTFC.gui.SideScreenGui;
import org.crumbleworks.forge.aTFC.networking.Networking;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class CharacterScreen extends BigBlankGui {

    private static final ITextComponent title = new TranslationTextComponent(
            "gui.atfc.character.title");

    private boolean widthTooNarrow;
    private SideScreenGui sideScreenGui;
    private ATFCPlayerData playerData = null;

    public CharacterScreen() {
        super(title, CharacterWiringAndEvents.CHARACTER_KEY,
                new KeyBinding[] { SkillsWiringAndEvents.SKILLS_KEY },
                new Supplier[] { () -> {
                    return new SkillsScreen();
                } });
    }

    @Override
    protected void init() {
        super.init();

        Networking.sendToServer(new CharacterCheckMessage());
        playerData = Minecraft.getInstance().player.getCapability(
                ATFCPlayerDataCapabilityProvider.CAPABILITY_ATFCPLAYERDATA)
                .orElseThrow(() -> new RuntimeException(
                        "PlayerEntity invalid, missing ATFCPlayerData Capability!"));

        if(Minecraft.getInstance().player.isCreative()) {
            widthTooNarrow = this.width < 379;
            sideScreenGui = new CharacterSideScreen();
            sideScreenGui.init(width, height, minecraft, widthTooNarrow);
            guiLeft = sideScreenGui.updateScreenPosition(widthTooNarrow,
                    width, xSize);
        }
    }

    @Override
    public void onClose() {
        playerData = null;
        super.onClose();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY,
            float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        if(Minecraft.getInstance().player.isCreative()) {
            sideScreenGui.render(matrixStack, mouseX, mouseY, partialTicks);
        }
    }

    protected void drawGuiForegroundLayer(MatrixStack matrixStack, int mouseX,
            int mouseY) {

    }

    public class CharacterSideScreen extends SideScreenGui {

        private static final int xOffset = 5;
        private static final int barHeight = 6;

        @Override
        protected void drawGuiForegroundLayer(MatrixStack matrixStack,
                int mouseX, int mouseY) {
            GuiHelper.writeText(matrixStack, font,
                    new TranslationTextComponent(
                            "gui.atfc.character.taste_prefs.title"),
                    5, 6, Colors.GUI_DARK);

            drawTaste(matrixStack,
                    new TranslationTextComponent("gui.atfc.character.taste_prefs.sweet"),
                    18, TastePreference.MIN_SWEET_DESIRE, TastePreference.MAX_SWEET_DESIRE,
                    playerData.tastePreferences().desireFor(Taste.SWEET));
        }

        private void drawTaste(MatrixStack matrixStack, ITextComponent text,
                int y, int min, int max, int taste) {
            GuiHelper.writeText(matrixStack, font,
                    new StringTextComponent(String.format("%s", text.getString())),
                    (xSize / 2) - 2 - font.getStringWidth(text.getString()), y,
                    Colors.GUI_DARK);
            drawTasteBar(matrixStack, y, min, max, taste);
        }

        private void drawTasteBar(MatrixStack matrixStack, int y, int min,
                int max, int taste) {
            int barStart = (xSize / 2);
            int barWidth = (xSize / 2) - xOffset;
            int barEnd = barStart + barWidth;

            fill(matrixStack,
                    barStart, y,
                    barEnd - 1, y + barHeight - 1,
                    Colors.GUI_DARK);
            fill(matrixStack,
                    barStart + 1, y + 1,
                    barEnd, y + barHeight,
                    Colors.WHITE);
        }
    }
}
