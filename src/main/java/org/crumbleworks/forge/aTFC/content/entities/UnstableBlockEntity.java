package org.crumbleworks.forge.aTFC.content.entities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ConcretePowderBlock;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.DirectionalPlaceContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class UnstableBlockEntity extends FallingBlockEntity {

    public UnstableBlockEntity(World worldIn, double x, double y, double z,
            BlockState fallingBlockState) {
        super(worldIn, x, y, z, fallingBlockState);
    }

    private boolean isThisOrNeighbouringBlockOriginal(BlockPos blockpos,
            Block block) {

        if(this.level.getBlockState(blockpos).is(block)) {
            this.level.removeBlock(blockpos, false);
            return true;
        }

        if(this.level.getBlockState(blockpos.north()).is(block)) {
            this.level.removeBlock(blockpos.north(), false);
            return true;
        }

        if(this.level.getBlockState(blockpos.east()).is(block)) {
            this.level.removeBlock(blockpos.east(), false);
            return true;
        }

        if(this.level.getBlockState(blockpos.south()).is(block)) {
            this.level.removeBlock(blockpos.south(), false);
            return true;
        }

        if(this.level.getBlockState(blockpos.west()).is(block)) {
            this.level.removeBlock(blockpos.west(), false);
            return true;
        }

        return false;
    }

    // Copy of the orig with our changes
    @Override
    public void tick() {
        if(this.blockState.isAir()) {
            this.remove();
        } else {
            Block block = this.blockState.getBlock();
            if(this.time++ == 0) {
                BlockPos blockpos = this.blockPosition();

                // THIS IS CHANGED
                if(isThisOrNeighbouringBlockOriginal(blockpos, block)) {}
                else if(!this.level.isClientSide) {
                        this.remove();
                        return;
                    }
            }

            if(!this.isNoGravity()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
            }

            this.move(MoverType.SELF, this.getDeltaMovement());
            if(!this.level.isClientSide) {
                BlockPos blockpos1 = this.blockPosition();
                boolean flag = this.blockState
                        .getBlock() instanceof ConcretePowderBlock;
                boolean flag1 = flag && this.level.getFluidState(blockpos1)
                        .is(FluidTags.WATER);
                double d0 = this.getDeltaMovement().lengthSqr();
                if(flag && d0 > 1.0D) {
                    BlockRayTraceResult blockraytraceresult = this.level
                            .clip(new RayTraceContext(
                                    new Vector3d(this.xo, this.yo,
                                            this.zo),
                                    this.position(),
                                    RayTraceContext.BlockMode.COLLIDER,
                                    RayTraceContext.FluidMode.SOURCE_ONLY,
                                    this));
                    if(blockraytraceresult
                            .getType() != RayTraceResult.Type.MISS
                            && this.level
                                    .getFluidState(
                                            blockraytraceresult.getBlockPos())
                                    .is(FluidTags.WATER)) {
                        blockpos1 = blockraytraceresult.getBlockPos();
                        flag1 = true;
                    }
                }

                if(!this.onGround && !flag1) {
                    if(!this.level.isClientSide && (this.time > 100
                            && (blockpos1.getY() < 1
                                    || blockpos1.getY() > 256)
                            || this.time > 600)) {
                        if(this.dropItem && this.level.getGameRules()
                                .getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                            this.spawnAtLocation(block);
                        }

                        this.remove();
                    }
                } else {
                    BlockState blockstate = this.level
                            .getBlockState(blockpos1);
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
                    if(!blockstate.is(Blocks.MOVING_PISTON)) {
                        this.remove();
                        if(!this.cancelDrop) {
                            boolean flag2 = blockstate.canBeReplaced(
                                    new DirectionalPlaceContext(this.level,
                                            blockpos1, Direction.DOWN,
                                            ItemStack.EMPTY, Direction.UP));
                            boolean flag3 = FallingBlock
                                    .isFree(this.level
                                            .getBlockState(blockpos1.below()))
                                    && (!flag || !flag1);
                            boolean flag4 = this.blockState.canSurvive(
                                    this.level, blockpos1) && !flag3;
                            if(flag2 && flag4) {
                                if(this.blockState.hasProperty(
                                        BlockStateProperties.WATERLOGGED)
                                        && this.level.getFluidState(blockpos1)
                                                .getType() == Fluids.WATER) {
                                    this.blockState = this.blockState.setValue(
                                            BlockStateProperties.WATERLOGGED,
                                            Boolean.valueOf(true));
                                }

                                if(this.level.setBlock(blockpos1,
                                        this.blockState, 3)) {
                                    if(block instanceof FallingBlock) {
                                        ((FallingBlock)block).onLand(
                                                this.level, blockpos1,
                                                this.blockState, blockstate,
                                                this);
                                    }

                                    if(this.blockData != null
                                            && this.blockState
                                                    .hasTileEntity()) {
                                        TileEntity tileentity = this.level
                                                .getBlockEntity(blockpos1);
                                        if(tileentity != null) {
                                            CompoundNBT compoundnbt = tileentity
                                                    .save(new CompoundNBT());

                                            for(String s : this.blockData
                                                    .getAllKeys()) {
                                                INBT inbt = this.blockData
                                                        .get(s);
                                                if(!"x".equals(s)
                                                        && !"y".equals(s)
                                                        && !"z".equals(s)) {
                                                    compoundnbt.put(s,
                                                            inbt.copy());
                                                }
                                            }

                                            tileentity.load(this.blockState,
                                                    compoundnbt);
                                            tileentity.setChanged();
                                        }
                                    }
                                } else if(this.dropItem && this.level
                                        .getGameRules().getBoolean(
                                                GameRules.RULE_DOENTITYDROPS)) {
                                                    this.spawnAtLocation(
                                                            block);
                                                }
                            } else if(this.dropItem
                                    && this.level.getGameRules().getBoolean(
                                            GameRules.RULE_DOENTITYDROPS)) {
                                                this.spawnAtLocation(block);
                                            }
                        } else if(block instanceof FallingBlock) {
                            ((FallingBlock)block).onBroken(this.level,
                                    blockpos1, this);
                        }
                    }
                }
            }

            this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
        }
    }
}
