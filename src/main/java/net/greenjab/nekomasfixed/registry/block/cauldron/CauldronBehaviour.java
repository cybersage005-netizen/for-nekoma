package net.greenjab.nekomasfixed.registry.block.cauldron;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CauldronBehaviour {

    public static void register() {

        java.util.Map<Item, CauldronBehavior> waterMap = CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map();
        for (Item item : Registries.ITEM) {
            if (item.getDefaultStack().isIn(ItemTags.WOOL)) {
                waterMap.put(item, CauldronBehaviour::cleanWool);
            }
        }
    }

    private static ActionResult cleanWool(BlockState state, World world, BlockPos pos,
                                          PlayerEntity player, Hand hand, ItemStack stack) {

        if (!stack.contains(DataComponentTypes.DYED_COLOR)) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }

        if (!world.isClient()) {
            stack.remove(DataComponentTypes.DYED_COLOR);
            LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
            world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY,
                    SoundCategory.BLOCKS, 1.0F, 1.0F);
        }

        return ActionResult.SUCCESS;
    }
}