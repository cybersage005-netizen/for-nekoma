package net.greenjab.nekomasfixed.registry.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ClearItemFrameEntity extends ItemFrameEntity {

    public ClearItemFrameEntity(EntityType<? extends ClearItemFrameEntity> type, World world) {
        super(type, world);
    }

    @Override
    public boolean canStayAttached() {
        return true;
    }

    public ClearItemFrameEntity(World world, BlockPos pos, Direction facing) {
        super(world, pos, facing);
    }


    // Invisible ONLY when item inside
    @Override
    public boolean isInvisible() {
        return !this.getHeldItemStack().isEmpty();
    }
}
