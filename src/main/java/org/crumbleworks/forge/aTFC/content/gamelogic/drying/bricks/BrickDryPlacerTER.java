package org.crumbleworks.forge.aTFC.content.gamelogic.drying.bricks;

import org.crumbleworks.forge.aTFC.content.gamelogic.drying.DryingCapabilityProvider;
import org.crumbleworks.forge.aTFC.content.items.CurableBrickItem;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class BrickDryPlacerTER extends TileEntityRenderer<BrickDryPlacerTE> {

    public BrickDryPlacerTER(
            TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(BrickDryPlacerTE tileEntity, float partialTicks,
            MatrixStack matrixStack, IRenderTypeBuffer buffer,
            int combinedLight, int combinedOverlay) {

        matrixStack.pushPose();

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        IItemHandler inventory = tileEntity.inventory.resolve().get();
        for(int i = 0 ; i < inventory.getSlots() ; i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if(stack.isEmpty()) {
                continue;
            }

            matrixStack.pushPose();

            float xTrans = 0.75f;
            if(i % 2 == 1) {
                xTrans -= 0.5f;
            }

            float zTrans = 0.75f;
            if(i > 1) {
                zTrans -= 0.5f;
            }

            CurableBrickItem item = (CurableBrickItem)stack.getItem();
            float dryingProgress = stack
                    .getCapability(DryingCapabilityProvider.CAPABILITY_DRYING)
                    .resolve().get().getDryingProgress();

            matrixStack.translate(xTrans, item.renderYOffset(), zTrans);

            IBakedModel bakedModel = Minecraft.getInstance().getModelManager()
                    .getModel(dryingProgress < item.dryingThreshold()
                            ? item.uncuredBrickModel()
                            : item.curedBrickModel());
            itemRenderer.render(stack,
                    ItemCameraTransforms.TransformType.FIXED, true,
                    matrixStack, buffer, combinedLight, combinedOverlay,
                    bakedModel);

            matrixStack.popPose();
        }

        matrixStack.popPose();
    }
}
