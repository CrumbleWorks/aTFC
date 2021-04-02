package org.crumbleworks.forge.aTFC.content.gamelogic.playerskills;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.crumbleworks.forge.aTFC.content.Colors;
import org.crumbleworks.forge.aTFC.content.gamelogic.playercharacter.CharacterScreen;
import org.crumbleworks.forge.aTFC.content.gamelogic.playercharacter.CharacterWiringAndEvents;
import org.crumbleworks.forge.aTFC.content.gamelogic.playerdata.ATFCPlayerData;
import org.crumbleworks.forge.aTFC.content.gamelogic.playerdata.ATFCPlayerDataCapabilityProvider;
import org.crumbleworks.forge.aTFC.content.gamelogic.playerdata.skills.Skills;
import org.crumbleworks.forge.aTFC.gui.BigBlankGui;
import org.crumbleworks.forge.aTFC.gui.GuiHelper;
import org.crumbleworks.forge.aTFC.networking.Networking;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class SkillsScreen extends BigBlankGui {

    private static final ITextComponent title = new TranslationTextComponent(
            "gui.atfc.skills.title");

    private static final Skill[] skills = {
            Skill.GEN_SMITHING,
            Skill.TOL_SMITHING,
            Skill.ARM_SMITHING,
            Skill.WPN_SMITHING,
            Skill.AGRICULTURE,
            Skill.COOKING,
            Skill.PROSPECTING,
            Skill.BUTCHERING
    };

    private static final ITextComponent[] skillNames = {
            new TranslationTextComponent("gui.atfc.skills.gen_smithing"),
            new TranslationTextComponent("gui.atfc.skills.tol_smithing"),
            new TranslationTextComponent("gui.atfc.skills.arm_smithing"),
            new TranslationTextComponent("gui.atfc.skills.wpn_smithing"),
            new TranslationTextComponent("gui.atfc.skills.agriculture"),
            new TranslationTextComponent("gui.atfc.skills.cooking"),
            new TranslationTextComponent("gui.atfc.skills.prospecting"),
            new TranslationTextComponent("gui.atfc.skills.butchering")
    };

    private static final List[] skillLevels = {
            Arrays.asList(new StringTextComponent(
                    "§f" + new TranslationTextComponent(
                            "gui.atfc.skills.novice_level").getString())),
            Arrays.asList(new StringTextComponent(
                    "§a" + new TranslationTextComponent(
                            "gui.atfc.skills.adept_level").getString())),
            Arrays.asList(new StringTextComponent(
                    "§9" + new TranslationTextComponent(
                            "gui.atfc.skills.expert_level").getString())),
            Arrays.asList(new StringTextComponent(
                    "§d" + new TranslationTextComponent(
                            "gui.atfc.skills.master_level").getString()))
    };

    private static final int[] skillColors = {
            Colors.GUI_LIGHT, Colors.WHITE, Colors.TEXT_GREEN,
            Colors.TEXT_BLUE, Colors.TEXT_PURPLE
    };

    private static final int[] yCoords = { 12, 31, 50, 69, 88, 107, 126,
            145 };
    private static final int progBarHeight = 6;
    private static final int progBarXOffset = 5;

    private ATFCPlayerData playerData = null;

    public SkillsScreen() {
        super(title, SkillsWiringAndEvents.SKILLS_KEY,
                new KeyBinding[] { CharacterWiringAndEvents.CHARACTER_KEY },
                new Supplier[] { () -> {
                    return new CharacterScreen();
                } });
    }

    @Override
    protected void init() {
        Networking.sendToServer(new SkillsCheckMessage());
        playerData = Minecraft.getInstance().player.getCapability(
                ATFCPlayerDataCapabilityProvider.CAPABILITY_ATFCPLAYERDATA)
                .orElseThrow(() -> new RuntimeException(
                        "PlayerEntity invalid, missing ATFCPlayerData Capability!"));
        super.init();
    }

    @Override
    public void onClose() {
        playerData = null;
        super.onClose();
    }

    protected void drawGuiForegroundLayer(MatrixStack matrixStack, int mouseX,
            int mouseY) {

        for(int i = 0 ; i < skillNames.length ; i++) {
            drawSkill(matrixStack, skillNames[i], yCoords[i],
                    playerData.skills().skillLevel(skills[i]),
                    playerData.skills().partialExp(skills[i]));
        }

        for(int i = 0 ; i < yCoords.length ; i++) {
            if( (mouseY >= (yCoords[i] + font.FONT_HEIGHT)
                    && mouseY <= yCoords[i] + 18)
                    && (mouseX >= progBarXOffset
                            && mouseX <= (xSize - progBarXOffset))) {

                GuiUtils.drawHoveringText(matrixStack,
                        skillLevels[playerData.skills()
                                .skillLevel(skills[i])],
                        mouseX, mouseY,
                        width, height,
                        0, font);
            }
        }
    }

    private void drawSkill(MatrixStack matrixStack, ITextComponent text,
            int y, int level, int experience) {
        int labelWidth = font.getStringWidth(text.getString());
        GuiHelper.writeText(matrixStack, font, text, xSize - labelWidth - 7,
                y, Colors.GUI_DARK);

        drawSkillBar(matrixStack, y + font.FONT_HEIGHT);
        fillSkillBar(matrixStack, y + font.FONT_HEIGHT, level, experience);
    }

    private void drawSkillBar(MatrixStack matrixStack, int y) {
        fill(matrixStack,
                progBarXOffset, y,
                xSize - progBarXOffset - 1, y + progBarHeight - 1,
                Colors.GUI_DARK);
        fill(matrixStack,
                progBarXOffset + 1, y + 1,
                xSize - progBarXOffset, y + progBarHeight,
                Colors.WHITE);
    }

    private void fillSkillBar(MatrixStack matrixStack, int y, int level,
            int experience) {
        int progbarSize = xSize - (progBarXOffset + 1) - (progBarXOffset + 1);

        fill(matrixStack,
                progBarXOffset + 1, y + 1,
                xSize - progBarXOffset - 1, y + progBarHeight - 1,
                skillColors[level]);

        if(level < Skills.MAX_SKILL_LEVEL) {
            fill(matrixStack,
                    progBarXOffset + 1, y + 1,
                    progBarXOffset + 1
                            + (progbarSize / Skills.EXPERIENCE_PER_LEVEL
                                    * experience),
                    y + progBarHeight - 1,
                    skillColors[level + 1]);
        }

        if(Minecraft.getInstance().player.isCreative()) {
            GuiHelper.writeTextSmall(matrixStack, font,
                    new StringTextComponent(
                            String.format("LVL %1s @ %4s / %4s Exp", level,
                                    experience, Skills.EXPERIENCE_PER_LEVEL)),
                    progBarXOffset + 1, y - 1 - (font.FONT_HEIGHT / 2),
                    Colors.WHITE);
        }
    }
}
