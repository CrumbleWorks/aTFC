package org.crumbleworks.forge.aTFC.dataGeneration;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;


/**
 * TODO
 * 
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class DynamicPainter implements IBlockColor, IItemColor {

    public static final int TINT_GREENERY = 1;
    public static final int TINT_SOIL = 2;

    @Override
    public int getColor(BlockState arg0, IBlockDisplayReader arg1,
            BlockPos arg2, int arg3) {
        // TODO implement #19 then use the tints here instead of the
        // placeholder we set now

        if(arg3 == TINT_GREENERY) {
            return 0xB2EF9B;
        }

        if(arg3 == TINT_SOIL) {
            return 0xB3BFB8;
        }

        return 0xFF69B4;
    }

    @Override
    public int getColor(ItemStack p_getColor_1_, int p_getColor_2_) {
        // TODO implement #19 then use the tints here instead of the
        // placeholder we set now

        if(p_getColor_2_ == TINT_GREENERY) {
            return 0xB2EF9B;
        }

        if(p_getColor_2_ == TINT_SOIL) {
            return 0xB3BFB8;
        }

        return 0xFF69B4;
    }
}
