package org.crumbleworks.forge.aTFC.client.renderer.entity;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.client.renderer.entity.model.EurasianCootModel;
import org.crumbleworks.forge.aTFC.content.entities.animals.EurasianCootEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EurasianCootRenderer
        extends
        MobRenderer<EurasianCootEntity, EurasianCootModel<EurasianCootEntity>> {

    private static final ResourceLocation EURASIAN_COOT_TEXTURE = new ResourceLocation(
            Main.MOD_ID, "textures/entity/eurasian_coot.png");

    public EurasianCootRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new EurasianCootModel<>(), 0.3f);
    }

    @Override
    public ResourceLocation getEntityTexture(EurasianCootEntity entity) {
        return EURASIAN_COOT_TEXTURE;
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat(ChickenEntity livingBase,
            float partialTicks) {
        float f = MathHelper.lerp(partialTicks, livingBase.oFlap,
                livingBase.wingRotation);
        float f1 = MathHelper.lerp(partialTicks, livingBase.oFlapSpeed,
                livingBase.destPos);
        return (MathHelper.sin(f) + 1.0F) * f1;
    }

}
