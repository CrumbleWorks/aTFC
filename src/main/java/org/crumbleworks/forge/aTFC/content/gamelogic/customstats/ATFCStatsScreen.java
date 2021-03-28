package org.crumbleworks.forge.aTFC.content.gamelogic.customstats;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.StatsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.resources.I18n;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
@OnlyIn(Dist.CLIENT)
public class ATFCStatsScreen extends StatsScreen {

    private ATFCStatsScreen.aTFCStatsList ourStats;

    public ATFCStatsScreen(Screen parent, StatisticsManager manager) {
        super(parent, manager);
    }

    @Override
    public void initLists() {
        super.initLists();
        this.ourStats = new ATFCStatsScreen.aTFCStatsList(this.minecraft);
    }

    public void initButtons() {
        // TODO add button for our UI, change pos for everything..

        this.addButton(new Button(
                this.width / 2 - 160, this.height - 52,
                80, 20,
                new TranslationTextComponent("menu.atfc.stats.atfc_tab"),
                (button) -> {
                    this.func_213110_a(this.ourStats);
                }));
        this.addButton(new Button(
                this.width / 2 - 80, this.height - 52,
                80, 20,
                new TranslationTextComponent("stat.generalButton"),
                (button) -> {
                    this.func_213110_a(this.generalStats);
                }));
        Button itemStatsButton = this.addButton(new Button(
                this.width / 2, this.height - 52,
                80, 20,
                new TranslationTextComponent("stat.itemsButton"),
                (button) -> {
                    this.func_213110_a(this.itemStats);
                }));
        Button mobStatsButton = this.addButton(new Button(
                this.width / 2 + 80, this.height - 52,
                80, 20,
                new TranslationTextComponent("stat.mobsButton"),
                (button) -> {
                    this.func_213110_a(this.mobStats);
                }));


        this.addButton(new Button(
                this.width / 2 - 100, this.height - 28,
                200, 20,
                DialogTexts.GUI_DONE,
                (button) -> {
                    this.minecraft.displayGuiScreen(this.parentScreen);
                }));


        if(this.itemStats.getEventListeners().isEmpty()) {
            itemStatsButton.active = false;
        }

        if(this.mobStats.getEventListeners().isEmpty()) {
            mobStatsButton.active = false;
        }
    }

    public void onStatsUpdated() {
        if(this.doesGuiPauseGame) {
            this.initLists();
            this.initButtons();
            this.func_213110_a(super.generalStats);
            this.doesGuiPauseGame = false;
        }
    }

    public void func_213110_a(@Nullable ExtendedList<?> p_213110_1_) {
        this.children.remove(this.ourStats);
        super.func_213110_a(p_213110_1_);
    }

    @OnlyIn(Dist.CLIENT)
    class aTFCStatsList
            extends ExtendedList<ATFCStatsScreen.aTFCStatsList.Entry> {

        public aTFCStatsList(Minecraft mcIn) {
            super(mcIn, ATFCStatsScreen.this.width,
                    ATFCStatsScreen.this.height, 32,
                    ATFCStatsScreen.this.height - 64, 10);

            // This defines the statlist used
            ObjectArrayList<Stat<ResourceLocation>> objectarraylist = new ObjectArrayList<>(
                    aTFCStats.aTFC_CUSTOM
                            .iterator());

            objectarraylist
                    .sort(java.util.Comparator.comparing((p_238679_0_) -> {
                        return I18n.format(
                                StatsScreen.func_238672_b_(p_238679_0_));
                    }));

            for(Stat<ResourceLocation> stat : objectarraylist) {
                this.addEntry(new ATFCStatsScreen.aTFCStatsList.Entry(stat));
            }

        }

        protected void renderBackground(MatrixStack matStack) {
            ATFCStatsScreen.this.renderBackground(matStack);
        }

        @OnlyIn(Dist.CLIENT)
        class Entry extends
                ExtendedList.AbstractListEntry<ATFCStatsScreen.aTFCStatsList.Entry> {

            private final Stat<ResourceLocation> stat;
            private final ITextComponent displayText;

            private Entry(Stat<ResourceLocation> stat) {
                this.stat = stat;
                this.displayText = new TranslationTextComponent(
                        StatsScreen.func_238672_b_(stat));
            }

            public void render(MatrixStack matStack, int p_230432_2_,
                    int p_230432_3_, int p_230432_4_, int unusedInt,
                    int unusedIntTwo, int unusedIntThree, int unusedIntFour,
                    boolean unusedBool, float unusedFloat) {
                AbstractGui.drawString(matStack, ATFCStatsScreen.this.font,
                        this.displayText, p_230432_4_ + 2, p_230432_3_ + 1,
                        p_230432_2_ % 2 == 0 ? 16777215 : 9474192);
                String s = this.stat.format(
                        ATFCStatsScreen.this.stats.getValue(this.stat));
                AbstractGui.drawString(matStack, ATFCStatsScreen.this.font, s,
                        p_230432_4_ + 2 + 213
                                - ATFCStatsScreen.this.font.getStringWidth(s),
                        p_230432_3_ + 1,
                        p_230432_2_ % 2 == 0 ? 16777215 : 9474192);
            }
        }
    }
}
