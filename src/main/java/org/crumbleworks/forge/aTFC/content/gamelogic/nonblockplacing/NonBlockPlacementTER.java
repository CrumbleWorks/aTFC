package org.crumbleworks.forge.aTFC.content.gamelogic.nonblockplacing;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.items.IItemHandler;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class NonBlockPlacementTER
        extends TileEntityRenderer<NonBlockPlacementTE> {

    public NonBlockPlacementTER(
            TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(NonBlockPlacementTE tileEntity, float partialTicks,
            MatrixStack matrixStack, IRenderTypeBuffer buffer,
            int combinedLight, int combinedOverlay) {

        matrixStack.pushPose();

        //FIXME this is only valid for 4 small items, need additional code in case of large item
        // -> make item bigger and render in center
        
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

            matrixStack.translate(xTrans, 0.33f, zTrans);

            //Rotate towards player position; looks shit when too close..
//            BlockPos tePos = tileEntityIn.getPos();
//            PlayerEntity player = Minecraft.getInstance().player;
//            double xDir = player.getPosX() - tePos.getX();
//            double zDir = player.getPosZ() - tePos.getZ();
//            float rotationYaw = MathHelper.wrapDegrees((float)(MathHelper.atan2(zDir, xDir) * (double)(180F / (float)Math.PI)) - 90.0F);
//            matrixStack.rotate(Vector3f.YN.rotationDegrees(rotationYaw));
            
            //Rotate counter to player facing; looks shit on screen corner/far away
            matrixStack.mulPose(Vector3f.YN.rotationDegrees(
                    Minecraft.getInstance().player.yHeadRot));

            IBakedModel bakedModel = itemRenderer.getModel(
                    stack, tileEntity.getLevel(), null);
            itemRenderer.render(stack,
                    ItemCameraTransforms.TransformType.GROUND, true,
                    matrixStack, buffer, combinedLight, combinedOverlay,
                    bakedModel);

            matrixStack.popPose();
        }

        matrixStack.popPose();
    }
}
