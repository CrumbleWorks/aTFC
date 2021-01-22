package org.crumbleworks.forge.aTFC.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EurasianCootModel<T extends Entity> extends AgeableModel<T> {

    private final ModelRenderer body;
    private final ModelRenderer head;
    private final ModelRenderer leftWing;
    private final ModelRenderer rightWing;
    private final ModelRenderer leftLeg;
    private final ModelRenderer rightLeg;

    public EurasianCootModel() {
        textureWidth = 32;
        textureHeight = 32;

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 18.0F, 1.0F);
        body.setTextureOffset(0, 0).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 4.0F,
                7.0F, 0.0F, false);
        body.setTextureOffset(0, 5).addBox(-0.5F, -3.0F, 3.0F, 1.0F, 1.0F,
                1.0F, 0.0F, false);

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, -2.0F, -3.0F);
        body.addChild(head);
        head.setTextureOffset(0, 11).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F,
                3.0F, 0.0F, false);
        head.setTextureOffset(0, 1).addBox(-0.5F, -1.0F, -4.0F, 1.0F, 1.0F,
                2.0F, 0.0F, false);
        head.setTextureOffset(11, 11).addBox(-0.5F, -2.0F, -3.0F, 1.0F, 1.0F,
                1.0F, 0.0F, false);

        leftWing = new ModelRenderer(this);
        leftWing.setRotationPoint(3.0F, -0.5F, 0.0F);
        body.addChild(leftWing);
        leftWing.setTextureOffset(16, 16).addBox(0.0F, -0.5F, -2.0F, 1.0F,
                2.0F, 4.0F, 0.0F, false);

        rightWing = new ModelRenderer(this);
        rightWing.setRotationPoint(-3.0F, -0.5F, 0.0F);
        body.addChild(rightWing);
        rightWing.setTextureOffset(10, 14).addBox(-1.0F, -0.5F, -2.0F, 1.0F,
                2.0F, 4.0F, 0.0F, true);

        leftLeg = new ModelRenderer(this);
        leftLeg.setRotationPoint(1.5F, 20.0F, 1.0F);
        leftLeg.setTextureOffset(0, 18).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 4.0F,
                0.0F, 0.0F, false);
        leftLeg.setTextureOffset(0, 4).addBox(-1.5F, 4.0F, -1.0F, 3.0F, 0.0F,
                1.0F, 0.0F, false);
        leftLeg.setTextureOffset(3, 2).addBox(-0.5F, 4.0F, -2.0F, 1.0F, 0.0F,
                1.0F, 0.0F, false);

        rightLeg = new ModelRenderer(this);
        rightLeg.setRotationPoint(-1.5F, 20.0F, 1.0F);
        rightLeg.setTextureOffset(16, 11).addBox(-0.5F, 0.0F, 0.0F, 1.0F,
                4.0F, 0.0F, 0.0F, false);
        rightLeg.setTextureOffset(0, 0).addBox(-1.5F, 4.0F, -1.0F, 3.0F, 0.0F,
                1.0F, 0.0F, false);
        rightLeg.setTextureOffset(3, 1).addBox(-0.5F, 4.0F, -2.0F, 1.0F, 0.0F,
                1.0F, 0.0F, false);
    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return ImmutableList.of(head);
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(body, leftWing, rightWing, leftLeg, rightLeg);
    }

    @Override
    public void setRotationAngles(Entity entity, float limbSwing,
            float limbSwingAmount, float ageInTicks, float netHeadYaw,
            float headPitch) {
        head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);

        rightWing.rotateAngleZ = ageInTicks;
        leftWing.rotateAngleZ = -ageInTicks;

        leftLeg.rotateAngleX = MathHelper
                .cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F
                * limbSwingAmount;
        rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F)
                * 1.4F * limbSwingAmount;
    }

}
