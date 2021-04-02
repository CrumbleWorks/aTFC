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
        private static final int barHeight = 8;

        private int threeDigs = font.getStringWidth("999");
        private int textStart = (xSize / 3);
        private int barStart = textStart + threeDigs;
        private int barWidth = (xSize / 3 * 2) - (2 * threeDigs) - (4 * 2);

        @Override
        protected void drawGuiForegroundLayer(MatrixStack matrixStack,
                int mouseX, int mouseY) {
            GuiHelper.writeText(matrixStack, font,
                    new TranslationTextComponent(
                            "gui.atfc.character.taste_prefs.title"),
                    5, 6, Colors.GUI_DARK);

            drawTaste(matrixStack,
                    new TranslationTextComponent(
                            "gui.atfc.character.taste_prefs.sweet"),
                    18, TastePreference.MIN_SWEET_DESIRE,
                    TastePreference.MAX_SWEET_DESIRE,
                    playerData.tastePreferences().desireFor(Taste.SWEET));
            drawTaste(matrixStack,
                    new TranslationTextComponent(
                            "gui.atfc.character.taste_prefs.sour"),
                    30, TastePreference.MIN_SOUR_DESIRE,
                    TastePreference.MAX_SOUR_DESIRE,
                    playerData.tastePreferences().desireFor(Taste.SOUR));
            drawTaste(matrixStack,
                    new TranslationTextComponent(
                            "gui.atfc.character.taste_prefs.salty"),
                    42, TastePreference.MIN_SALTY_DESIRE,
                    TastePreference.MAX_SALTY_DESIRE,
                    playerData.tastePreferences().desireFor(Taste.SALTY));
            drawTaste(matrixStack,
                    new TranslationTextComponent(
                            "gui.atfc.character.taste_prefs.bitter"),
                    54, TastePreference.MIN_BITTER_DESIRE,
                    TastePreference.MAX_BITTER_DESIRE,
                    playerData.tastePreferences().desireFor(Taste.BITTER));
            drawTaste(matrixStack,
                    new TranslationTextComponent(
                            "gui.atfc.character.taste_prefs.umami"),
                    66, TastePreference.MIN_UMAMI_DESIRE,
                    TastePreference.MAX_UMAMI_DESIRE,
                    playerData.tastePreferences().desireFor(Taste.UMAMI));
        }

        private void drawTaste(MatrixStack matrixStack, ITextComponent text,
                int y, int min, int max, int taste) {
            GuiHelper.writeText(matrixStack, font,
                    new StringTextComponent(
                            String.format("%s", text.getString())),
                    textStart - 2 - font.getStringWidth(text.getString()),
                    y, Colors.GUI_DARK);
            
            drawTasteBar(matrixStack, y, min, max, taste);
        }

        private void drawTasteBar(MatrixStack matrixStack, int y, int min,
                int max, int taste) {
            int barEnd = barStart + barWidth;

            GuiHelper.writeText(matrixStack, font,
                    new StringTextComponent(String.format("%s", String.valueOf(min))),
                    barStart - 1 - font.getStringWidth(String.valueOf(min)),
                    y, Colors.WHITE);
            
            GuiHelper.writeText(matrixStack, font,
                    new StringTextComponent(String.format("%s", String.valueOf(max))),
                    barEnd + 2, y, Colors.WHITE);
            
            fill(matrixStack,
                    barStart, y,
                    barEnd - 1, y + barHeight - 1,
                    Colors.GUI_DARK);
            fill(matrixStack,
                    barStart + 1, y + 1,
                    barEnd, y + barHeight,
                    Colors.WHITE);

            GuiHelper.fillGradientLeftToRight(matrixStack,
                    barStart + 1, y + 1,
                    barEnd - 1, y + barHeight - 1,
                    Colors.TEXT_RED, Colors.TEXT_GREEN);
            
            float sliderPos = (float)barWidth / (float)(max - min) * (float)(taste - min);
            GuiHelper.vLine(matrixStack, barStart + Math.round(sliderPos), y - 2, y + barHeight + 1, Colors.BLACK);
            
            GuiHelper.writeTextSmall(matrixStack, font, new StringTextComponent(String.valueOf(taste)), barStart + 2, y + 2, Colors.WHITE);
        }
    }
}
