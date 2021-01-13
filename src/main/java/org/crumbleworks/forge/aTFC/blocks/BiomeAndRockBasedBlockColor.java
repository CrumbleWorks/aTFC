package org.crumbleworks.forge.aTFC.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;


/**
 * tintindex1: greenery
 * tintindex2: dirt
 * tintindex3: rocks
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class BiomeAndRockBasedBlockColor implements IBlockColor {

    @Override
    public int getColor(BlockState arg0, IBlockDisplayReader arg1,
            BlockPos arg2, int arg3) {
        // TODO implement #19 then use the tints here instead of the
        // placeholder we set now
        switch(arg3) {
            case 1:
                return 11726747;
            case 2:
                return 5524285;
            case 3:
                return 11780024;
        }

        return 16738740;
    }
}
