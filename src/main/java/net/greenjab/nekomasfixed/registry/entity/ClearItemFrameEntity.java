package net.greenjab.nekomasfixed.registry.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.world.World;

public class ClearItemFrameEntity extends ItemFrameEntity {

    public ClearItemFrameEntity(EntityType<? extends ItemFrameEntity> type, World world) {
        super(type, world);
    }

    @Override
    public boolean isInvisible() {
        // invisible ONLY when item exists inside
        return !this.getHeldItemStack().isEmpty();
    }
}
