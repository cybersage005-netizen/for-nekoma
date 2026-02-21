package net.greenjab.nekomasfixed.registry.block.cauldron;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;

public class CauldronBehaviour {

    public static void register() {

        Map<Item, CauldronBehavior> waterMap = CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map();

        System.out.println("Start Registering the Items");
        addWoolBehavior(waterMap, Items.WHITE_WOOL);
        addWoolBehavior(waterMap, Items.ORANGE_WOOL);
        addWoolBehavior(waterMap, Items.MAGENTA_WOOL);
        addWoolBehavior(waterMap, Items.LIGHT_BLUE_WOOL);
        addWoolBehavior(waterMap, Items.YELLOW_WOOL);
        addWoolBehavior(waterMap, Items.LIME_WOOL);
        addWoolBehavior(waterMap, Items.PINK_WOOL);
        addWoolBehavior(waterMap, Items.GRAY_WOOL);
        addWoolBehavior(waterMap, Items.LIGHT_GRAY_WOOL);
        addWoolBehavior(waterMap, Items.CYAN_WOOL);
        addWoolBehavior(waterMap, Items.PURPLE_WOOL);
        addWoolBehavior(waterMap, Items.BLUE_WOOL);
        addWoolBehavior(waterMap, Items.BROWN_WOOL);
        addWoolBehavior(waterMap, Items.GREEN_WOOL);
        addWoolBehavior(waterMap, Items.RED_WOOL);
        addWoolBehavior(waterMap, Items.BLACK_WOOL);
        System.out.println("Done Registering the Items");
    }

    private static void addWoolBehavior(Map<Item, CauldronBehavior> map, Item woolItem) {
        map.put(woolItem, CauldronBehaviour::cleanWool);
    }

    private static ActionResult cleanWool(BlockState state, World world, BlockPos pos,
                                          PlayerEntity player, Hand hand, ItemStack stack) {
        System.out.println("Action with " + stack + " done on cauldron");
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