package org.crumbleworks.forge.aTFC.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EurasianCootModel<T extends Entity> extends AgeableModel<T> {

    private final ModelRenderer left_leg;
    private final ModelRenderer foot;
    private final ModelRenderer right_leg;
    private final ModelRenderer foot2;
    private final ModelRenderer body;
    private final ModelRenderer rotation;
    private final ModelRenderer head;

    public EurasianCootModel() {
        textureWidth = 32;
        textureHeight = 32;

        left_leg = new ModelRenderer(this);
        left_leg.setRotationPoint(0.0F, 24.0F, 0.0F);
        // setRotationAngle(left_leg, 1.5708F, 0.0F, 0.0F);
        left_leg.setTextureOffset(10, 11).addBox(1.0F, 1.0F, 0.0F, 1.0F, 0.0F,
                4.0F, 0.0F, false);

        foot = new ModelRenderer(this);
        foot.setRotationPoint(0.0F, 0.0F, 0.0F);
        left_leg.addChild(foot);
        foot.setTextureOffset(0, 6).addBox(0.0F, 0.0F, 0.0F, 3.0F, 1.0F, 0.0F,
                0.0F, false);
        foot.setTextureOffset(0, 1).addBox(1.0F, -1.0F, 0.0F, 1.0F, 1.0F,
                0.0F, 0.0F, false);

        right_leg = new ModelRenderer(this);
        right_leg.setRotationPoint(0.0F, 24.0F, 0.0F);
        // setRotationAngle(right_leg, 1.5708F, 0.0F, 0.0F);
        right_leg.setTextureOffset(0, 0).addBox(-2.0F, 1.0F, 0.0F, 1.0F, 0.0F,
                4.0F, 0.0F, false);

        foot2 = new ModelRenderer(this);
        foot2.setRotationPoint(0.0F, 0.0F, 0.0F);
        right_leg.addChild(foot2);
        foot2.setTextureOffset(0, 5).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 1.0F,
                0.0F, 0.0F, false);
        foot2.setTextureOffset(0, 0).addBox(-2.0F, -1.0F, 0.0F, 1.0F, 1.0F,
                0.0F, 0.0F, false);

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 24.0F, 0.0F);

        rotation = new ModelRenderer(this);
        rotation.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(rotation);
        rotation.setTextureOffset(0, 0).addBox(-3.0F, -8.0F, -2.0F, 6.0F,
                4.0F, 7.0F, 0.0F, false);

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 24.0F, 0.0F);
        head.setTextureOffset(0, 11).addBox(-2.0F, -10.0F, -4.0F, 4.0F, 4.0F,
                3.0F, 0.0F, false);
        head.setTextureOffset(0, 2).addBox(-0.5F, -9.0F, -6.0F, 1.0F, 1.0F,
                2.0F, 0.0F, false);
        head.setTextureOffset(0, 3).addBox(-0.5F, -10.0F, -5.0F, 1.0F, 1.0F,
                1.0F, 0.0F, true);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn,
            int packedLightIn, int packedOverlayIn, float red, float green,
            float blue, float alpha) {
        left_leg.render(matrixStackIn, bufferIn, packedLightIn,
                packedOverlayIn);
        right_leg.render(matrixStackIn, bufferIn, packedLightIn,
                packedOverlayIn);
        body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return ImmutableList.of(head);
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(body, left_leg, right_leg);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing,
            float limbSwingAmount, float ageInTicks, float netHeadYaw,
            float headPitch) {
        this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        this.body.rotateAngleX = ((float)Math.PI / 2F);
        this.left_leg.rotateAngleX = MathHelper
                .cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F
                * limbSwingAmount;
        this.right_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F)
                * 1.4F * limbSwingAmount;
        // this.leftWing.rotateAngleZ = -ageInTicks;
        // this.rightWing.rotateAngleZ = ageInTicks;
    }

}
