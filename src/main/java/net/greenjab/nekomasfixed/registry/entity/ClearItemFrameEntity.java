package net.greenjab.nekomasfixed.registry.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.world.World;

public class ClearItemFrameEntity extends ItemFrameEntity {

    public ClearItemFrameEntity(EntityType<? extends ClearItemFrameEntity> type, World world) {
        super(type, world);
    }

    // Invisible ONLY when item inside
    @Override
    public boolean isInvisible() {
        return !this.getHeldItemStack().isEmpty();
    }
}
