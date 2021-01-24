package org.crumbleworks.forge.aTFC.content.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

/**
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class BogBlock extends GrasscoverableBlock {

    public BogBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    static VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
       return SHAPE;
    }

    public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos) {
       return VoxelShapes.fullCube();
    }

    public VoxelShape getRayTraceShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
       return VoxelShapes.fullCube();
    }
    
    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        Vector3d mot = entityIn.getMotion();
        entityIn.setMotion(mot.x * 0.95D, mot.y * 0.75D, mot.z * 0.95D);
    }
}
