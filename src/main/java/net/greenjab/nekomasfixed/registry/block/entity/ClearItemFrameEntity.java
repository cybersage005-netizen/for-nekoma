package net.greenjab.nekomasfixed.registry.block.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ClearItemFrameEntity extends ItemFrameEntity {
    public ClearItemFrameEntity(EntityType<? extends ItemFrameEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void setHeldItemStack(ItemStack stack, boolean update) {
        super.setHeldItemStack(stack, update);
        boolean willHide = (stack.isEmpty())?false:true;
        this.setInvisible(willHide);
    }
}
