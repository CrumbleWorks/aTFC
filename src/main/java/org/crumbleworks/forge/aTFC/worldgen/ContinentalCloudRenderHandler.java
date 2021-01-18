package org.crumbleworks.forge.aTFC.worldgen;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.settings.CloudOption;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.ICloudRenderHandler;


/**
 * Responsible for rendering clouds at a proper altitude. The code in this class is mostly copied from the Minecraft sources.
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class ContinentalCloudRenderHandler implements ICloudRenderHandler {
    
    private int cloudsCheckX = Integer.MIN_VALUE;
    private int cloudsCheckY = Integer.MIN_VALUE;
    private int cloudsCheckZ = Integer.MIN_VALUE;
    private CloudOption cloudOption;
    private Vector3d cloudsCheckColor = Vector3d.ZERO;
    private boolean cloudsNeedUpdate = true;
    @Nullable
    private VertexBuffer cloudsVBO;
    
    private final int cloudLevel;
    
    public ContinentalCloudRenderHandler(int cloudLevel) {
        this.cloudLevel = cloudLevel;
    }

    @Override
    public void render(int ticks, float partialTicks, MatrixStack matrixStack, ClientWorld world, Minecraft mc, double viewEntityX, double viewEntityY, double viewEntityZ) {
        float f = cloudLevel;
        if (!Float.isNaN(f)) {
           RenderSystem.disableCull();
           RenderSystem.enableBlend();
           RenderSystem.enableAlphaTest();
           RenderSystem.enableDepthTest();
           RenderSystem.defaultAlphaFunc();
           RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
           RenderSystem.enableFog();
           RenderSystem.depthMask(true);
           
           double d1 = (double)(((float)ticks + partialTicks) * 0.03F);
           double d2 = (viewEntityX + d1) / 12.0D;
           double d3 = (double)(f - (float)viewEntityY + 0.33F);
           double d4 = viewEntityZ / 12.0D + (double)0.33F;
           d2 = d2 - (double)(MathHelper.floor(d2 / 2048.0D) * 2048);
           d4 = d4 - (double)(MathHelper.floor(d4 / 2048.0D) * 2048);
           float f3 = (float)(d2 - (double)MathHelper.floor(d2));
           float f4 = (float)(d3 / 4.0D - (double)MathHelper.floor(d3 / 4.0D)) * 4.0F;
           float f5 = (float)(d4 - (double)MathHelper.floor(d4));
           Vector3d vector3d = world.getCloudColor(partialTicks);
           int i = (int)Math.floor(d2);
           int j = (int)Math.floor(d3 / 4.0D);
           int k = (int)Math.floor(d4);
           if (i != cloudsCheckX || j != cloudsCheckY || k != cloudsCheckZ || mc.gameSettings.getCloudOption() != cloudOption || cloudsCheckColor.squareDistanceTo(vector3d) > 2.0E-4D) {
              cloudsCheckX = i;
              cloudsCheckY = j;
              cloudsCheckZ = k;
              cloudsCheckColor = vector3d;
              cloudOption = mc.gameSettings.getCloudOption();
              cloudsNeedUpdate = true;
           }

           if (cloudsNeedUpdate) {
              cloudsNeedUpdate = false;
              BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
              if (cloudsVBO != null) {
                 cloudsVBO.close();
              }

              cloudsVBO = new VertexBuffer(DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
              drawClouds(bufferbuilder, d2, d3, d4, vector3d);
              bufferbuilder.finishDrawing();
              cloudsVBO.upload(bufferbuilder);
           }

           mc.textureManager.bindTexture(WorldRenderer.CLOUDS_TEXTURES);
           matrixStack.push();
           matrixStack.scale(12.0F, 1.0F, 12.0F);
           matrixStack.translate((double)(-f3), (double)f4, (double)(-f5));
           if (cloudsVBO != null) {
              cloudsVBO.bindBuffer();
              DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL.setupBufferState(0L);
              int i1 = cloudOption == CloudOption.FANCY ? 0 : 1;

              for(int l = i1; l < 2; ++l) {
                 if (l == 0) {
                    RenderSystem.colorMask(false, false, false, false);
                 } else {
                    RenderSystem.colorMask(true, true, true, true);
                 }

                 cloudsVBO.draw(matrixStack.getLast().getMatrix(), 7);
              }

              VertexBuffer.unbindBuffer();
              DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL.clearBufferState();
           }

           matrixStack.pop();
           RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
           RenderSystem.disableAlphaTest();
           RenderSystem.enableCull();
           RenderSystem.disableBlend();
           RenderSystem.disableFog();
        }
    }
        
    private void drawClouds(BufferBuilder bufferIn, double cloudsX, double cloudsY, double cloudsZ, Vector3d cloudsColor) {
        float f3 = (float)MathHelper.floor(cloudsX) * 0.00390625F;
        float f4 = (float)MathHelper.floor(cloudsZ) * 0.00390625F;
        float f5 = (float)cloudsColor.x;
        float f6 = (float)cloudsColor.y;
        float f7 = (float)cloudsColor.z;
        float f8 = f5 * 0.9F;
        float f9 = f6 * 0.9F;
        float f10 = f7 * 0.9F;
        float f11 = f5 * 0.7F;
        float f12 = f6 * 0.7F;
        float f13 = f7 * 0.7F;
        float f14 = f5 * 0.8F;
        float f15 = f6 * 0.8F;
        float f16 = f7 * 0.8F;
        bufferIn.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        float f17 = (float)Math.floor(cloudsY / 4.0D) * 4.0F;
        if (this.cloudOption == CloudOption.FANCY) {
           for(int k = -3; k <= 4; ++k) {
              for(int l = -3; l <= 4; ++l) {
                 float f18 = (float)(k * 8);
                 float f19 = (float)(l * 8);
                 if (f17 > -5.0F) {
                    bufferIn.pos((double)(f18 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + 8.0F)).tex((f18 + 0.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f11, f12, f13, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
                    bufferIn.pos((double)(f18 + 8.0F), (double)(f17 + 0.0F), (double)(f19 + 8.0F)).tex((f18 + 8.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f11, f12, f13, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
                    bufferIn.pos((double)(f18 + 8.0F), (double)(f17 + 0.0F), (double)(f19 + 0.0F)).tex((f18 + 8.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f11, f12, f13, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
                    bufferIn.pos((double)(f18 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + 0.0F)).tex((f18 + 0.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f11, f12, f13, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
                 }

                 if (f17 <= 5.0F) {
                    bufferIn.pos((double)(f18 + 0.0F), (double)(f17 + 4.0F - 9.765625E-4F), (double)(f19 + 8.0F)).tex((f18 + 0.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
                    bufferIn.pos((double)(f18 + 8.0F), (double)(f17 + 4.0F - 9.765625E-4F), (double)(f19 + 8.0F)).tex((f18 + 8.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
                    bufferIn.pos((double)(f18 + 8.0F), (double)(f17 + 4.0F - 9.765625E-4F), (double)(f19 + 0.0F)).tex((f18 + 8.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
                    bufferIn.pos((double)(f18 + 0.0F), (double)(f17 + 4.0F - 9.765625E-4F), (double)(f19 + 0.0F)).tex((f18 + 0.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
                 }

                 if (k > -1) {
                    for(int i1 = 0; i1 < 8; ++i1) {
                       bufferIn.pos((double)(f18 + (float)i1 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + 8.0F)).tex((f18 + (float)i1 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
                       bufferIn.pos((double)(f18 + (float)i1 + 0.0F), (double)(f17 + 4.0F), (double)(f19 + 8.0F)).tex((f18 + (float)i1 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
                       bufferIn.pos((double)(f18 + (float)i1 + 0.0F), (double)(f17 + 4.0F), (double)(f19 + 0.0F)).tex((f18 + (float)i1 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
                       bufferIn.pos((double)(f18 + (float)i1 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + 0.0F)).tex((f18 + (float)i1 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
                    }
                 }

                 if (k <= 1) {
                    for(int j2 = 0; j2 < 8; ++j2) {
                       bufferIn.pos((double)(f18 + (float)j2 + 1.0F - 9.765625E-4F), (double)(f17 + 0.0F), (double)(f19 + 8.0F)).tex((f18 + (float)j2 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
                       bufferIn.pos((double)(f18 + (float)j2 + 1.0F - 9.765625E-4F), (double)(f17 + 4.0F), (double)(f19 + 8.0F)).tex((f18 + (float)j2 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
                       bufferIn.pos((double)(f18 + (float)j2 + 1.0F - 9.765625E-4F), (double)(f17 + 4.0F), (double)(f19 + 0.0F)).tex((f18 + (float)j2 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
                       bufferIn.pos((double)(f18 + (float)j2 + 1.0F - 9.765625E-4F), (double)(f17 + 0.0F), (double)(f19 + 0.0F)).tex((f18 + (float)j2 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
                    }
                 }

                 if (l > -1) {
                    for(int k2 = 0; k2 < 8; ++k2) {
                       bufferIn.pos((double)(f18 + 0.0F), (double)(f17 + 4.0F), (double)(f19 + (float)k2 + 0.0F)).tex((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)k2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
                       bufferIn.pos((double)(f18 + 8.0F), (double)(f17 + 4.0F), (double)(f19 + (float)k2 + 0.0F)).tex((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)k2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
                       bufferIn.pos((double)(f18 + 8.0F), (double)(f17 + 0.0F), (double)(f19 + (float)k2 + 0.0F)).tex((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)k2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
                       bufferIn.pos((double)(f18 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + (float)k2 + 0.0F)).tex((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)k2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
                    }
                 }

                 if (l <= 1) {
                    for(int l2 = 0; l2 < 8; ++l2) {
                       bufferIn.pos((double)(f18 + 0.0F), (double)(f17 + 4.0F), (double)(f19 + (float)l2 + 1.0F - 9.765625E-4F)).tex((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
                       bufferIn.pos((double)(f18 + 8.0F), (double)(f17 + 4.0F), (double)(f19 + (float)l2 + 1.0F - 9.765625E-4F)).tex((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
                       bufferIn.pos((double)(f18 + 8.0F), (double)(f17 + 0.0F), (double)(f19 + (float)l2 + 1.0F - 9.765625E-4F)).tex((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
                       bufferIn.pos((double)(f18 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + (float)l2 + 1.0F - 9.765625E-4F)).tex((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
                    }
                 }
              }
           }
        } else {
           for(int l1 = -32; l1 < 32; l1 += 32) {
              for(int i2 = -32; i2 < 32; i2 += 32) {
                 bufferIn.pos((double)(l1 + 0), (double)f17, (double)(i2 + 32)).tex((float)(l1 + 0) * 0.00390625F + f3, (float)(i2 + 32) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
                 bufferIn.pos((double)(l1 + 32), (double)f17, (double)(i2 + 32)).tex((float)(l1 + 32) * 0.00390625F + f3, (float)(i2 + 32) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
                 bufferIn.pos((double)(l1 + 32), (double)f17, (double)(i2 + 0)).tex((float)(l1 + 32) * 0.00390625F + f3, (float)(i2 + 0) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
                 bufferIn.pos((double)(l1 + 0), (double)f17, (double)(i2 + 0)).tex((float)(l1 + 0) * 0.00390625F + f3, (float)(i2 + 0) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
              }
           }
        }
     }
}
