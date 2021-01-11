package org.crumbleworks.forge.aTFC.blocks;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public abstract class UnstableBlock extends FallingBlock {

    public UnstableBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos,
            Random rand) {
        if(!isBlockSupported(worldIn, pos)
                || canFallThrough(worldIn.getBlockState(pos.down()))
                        && pos.getY() >= 0) {

            BlockPos fallPos = determineFallDirection(worldIn, pos, rand);
            FallingBlockEntity fallingblockentity = new FallingBlockEntity(
                    worldIn,
                    (double)fallPos.getX() + 0.5D,
                    (double)fallPos.getY(),
                    (double)fallPos.getZ() + 0.5D,
                    worldIn.getBlockState(pos));

            this.onStartFalling(fallingblockentity);

            worldIn.addEntity(fallingblockentity);
        }
    }

    private BlockPos determineFallDirection(ServerWorld worldIn, BlockPos pos, Random rand) {
        //FIXME >> get inspired by piston logik?
        
        //1. usefinde welli richtige überhaupt schiebbar sind (HET LUFT DETE)
        
        //2. usefinde ob i dere richtig platz isch zum abegheie
        
        //3. ???
        
        //4. PROFITS
        
        return pos;
    }

    private boolean isBlockSupported(ServerWorld worldIn, BlockPos pos) {
        // Block is supported, no falling possible
        // TODO https://github.com/CrumbleWorks/aTFC/issues/3
        
        // Needs to have a block below at all times
        if(worldIn.isAirBlock(pos.down())) {
            return false;
        }

        // Needs at least(!) two blocks on any sides
        int supportedSides = 0;
        if(worldIn.isAirBlock(pos.north())) supportedSides++;
        if(worldIn.isAirBlock(pos.east())) supportedSides++;
        if(worldIn.isAirBlock(pos.south())) supportedSides++;
        if(worldIn.isAirBlock(pos.west())) supportedSides++;
        if(supportedSides >= 2) {
            return false;
        }

        return true;
    }
}
