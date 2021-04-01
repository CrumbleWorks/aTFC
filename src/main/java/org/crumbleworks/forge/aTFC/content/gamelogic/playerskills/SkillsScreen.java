package org.crumbleworks.forge.aTFC.content.gamelogic.playerskills;

import java.util.Arrays;
import java.util.List;

import org.crumbleworks.forge.aTFC.content.Colors;
import org.crumbleworks.forge.aTFC.content.gamelogic.playerdata.ATFCPlayerData;
import org.crumbleworks.forge.aTFC.content.gamelogic.playerdata.ATFCPlayerDataCapabilityProvider;
import org.crumbleworks.forge.aTFC.gui.BigBlankGui;
import org.crumbleworks.forge.aTFC.networking.Networking;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;
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
            Arrays.asList(new StringTextComponent("§f" + new TranslationTextComponent("gui.atfc.skills.novice_level").getString())),
            Arrays.asList(new StringTextComponent("§a" + new TranslationTextComponent("gui.atfc.skills.adept_level").getString())),
            Arrays.asList(new StringTextComponent("§9" + new TranslationTextComponent("gui.atfc.skills.expert_level").getString())),
            Arrays.asList(new StringTextComponent("§d" + new TranslationTextComponent("gui.atfc.skills.master_level").getString()))
            };
    
    private static final int[] yCoords = {12, 31, 50, 69, 88, 107, 126, 145 };
    private static final int progBarHeight = 6;
    private static final int progBarXOffset = 5;
    
    private ATFCPlayerData playerData = null;
    
    protected SkillsScreen() {
        super(title);
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

    protected void drawGuiForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {

        for(int i = 0 ; i < skillNames.length ; i++) {
            drawSkill(matrixStack, skillNames[i], yCoords[i]);
        }
        
        for(int i = 0 ; i < yCoords.length ; i++) {
            if((mouseY >= (yCoords[i] + font.FONT_HEIGHT) && mouseY <= yCoords[i] + 18)
            && (mouseX >= progBarXOffset && mouseX <= (xSize - progBarXOffset))) {
                
                GuiUtils.drawHoveringText(matrixStack,
                        skillLevels[playerData.skills().skillLevel(skills[i])],
                        mouseX, mouseY,
                        width, height,
                        0, font);
            }
        }
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        InputMappings.Input mouseKey = InputMappings.getInputByCode(keyCode,
                scanCode);
        if(WiringAndEvents.SKILLS_KEY.isActiveAndMatches(mouseKey)) {
            this.closeScreen();
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }
    
    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        // TODO Auto-generated method stub
        return super.isMouseOver(mouseX, mouseY);
    }
    
    //TODO extend with params for progresslevels, once they are stored somewhere
    private void drawSkill(MatrixStack matrixStack, ITextComponent text, int y) {
        int labelWidth = font.getStringWidth(text.getString());
        writeText(matrixStack, text, xSize - labelWidth - 8, y, Colors.GUI_DARK);
        
        
        
        drawSkillBar(matrixStack, y + font.FONT_HEIGHT);
        
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
        fill(matrixStack,
                progBarXOffset + 1, y + 1,
                xSize - progBarXOffset - 1, y + progBarHeight - 1,
                Colors.GUI_LIGHT);
        
        //TODO draw second bar with progress, interpolate between current and next tier colors
    }
}
