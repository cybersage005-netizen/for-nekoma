package net.greenjab.nekomasfixed.registry.block.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ClearItemFrameEntity extends ItemFrameEntity {
    public ClearItemFrameEntity(EntityType<? extends ItemFrameEntity> entityType, World world) {
        super(entityType, world);
    }

    public ClearItemFrameEntity(EntityType<? extends ItemFrameEntity> type,
                                World world,
                                BlockPos pos,
                                Direction facing) {
        super(type, world, pos, facing);
    }

    @Override
    public void setHeldItemStack(ItemStack stack, boolean update) {
        super.setHeldItemStack(stack, update);

        // Server-side invisibility toggle
        if (!this.getEntityWorld().isClient()) {
            this.setInvisible(!stack.isEmpty());
        }
    }
}
