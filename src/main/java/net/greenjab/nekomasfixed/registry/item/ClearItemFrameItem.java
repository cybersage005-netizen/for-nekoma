package net.greenjab.nekomasfixed.registry.item;

import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ClearItemFrameItem extends Item {

    public ClearItemFrameItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();

        if (!(world instanceof ServerWorld serverWorld)) {
            return ActionResult.SUCCESS;
        }

        BlockPos pos = context.getBlockPos();
        Direction side = context.getSide();

        // place on face clicked
        BlockPos placePos = pos.offset(side);

        ItemFrameEntity frame = new ItemFrameEntity(
                EntityTypeRegistry.CLEAR_ITEM_FRAME,
                world,
                placePos,
                side
        );

        serverWorld.spawnEntity(frame);

        PlayerEntity player = context.getPlayer();
        if (player != null && !player.isCreative()) {
            context.getStack().decrement(1);
        }

        return ActionResult.CONSUME;
    }
}
