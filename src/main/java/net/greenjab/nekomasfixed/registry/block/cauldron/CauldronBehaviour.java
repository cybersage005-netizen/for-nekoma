package net.greenjab.nekomasfixed.registry.block.cauldron;

import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
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

        addTerracottaBehaviour(waterMap, Items.WHITE_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.LIGHT_GRAY_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.GRAY_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.BLACK_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.BROWN_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.RED_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.ORANGE_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.YELLOW_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.LIME_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.GREEN_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.CYAN_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.LIGHT_BLUE_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.BLUE_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.PURPLE_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.MAGENTA_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.PINK_TERRACOTTA);

        addGlazedterracottaBehaviour(waterMap, Items.WHITE_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.LIGHT_GRAY_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.GRAY_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.BLACK_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.BROWN_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.RED_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.ORANGE_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.YELLOW_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.LIME_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.GREEN_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.CYAN_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.LIGHT_BLUE_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.BLUE_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.PURPLE_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.MAGENTA_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.PINK_GLAZED_TERRACOTTA);

    }

    private static void addWoolBehavior(Map<Item, CauldronBehavior> map, Item woolItem) {
        map.put(woolItem, CauldronBehaviour::cleanWool);
    }

    private static void addTerracottaBehaviour(Map<Item, CauldronBehavior> map, Item terracottaItem) {
        map.put(terracottaItem, CauldronBehaviour::cleanTerracotta);
    }

    private static void addGlazedterracottaBehaviour(Map<Item, CauldronBehavior> map, Item glazedTerracottaItem) {
        map.put(glazedTerracottaItem, CauldronBehaviour::cleanTerracotta);
    }

    private static ActionResult cleanWool(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {

        if (stack.getItem() == Items.WHITE_WOOL) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (!stack.isIn(ItemTags.WOOL)) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }
        ItemStack whiteWool = new ItemStack(Items.WHITE_WOOL, stack.getCount());
        player.setStackInHand(hand, whiteWool);
        LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
        world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY,
                SoundCategory.BLOCKS, 1.0F, 1.0F);

        return ActionResult.SUCCESS;
    }

    private static ActionResult cleanTerracotta(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {

        if (stack.getItem() == Items.TERRACOTTA) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (!stack.isIn(ItemTags.TERRACOTTA)) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if(!stack.isIn(OtherRegistry.GLAZED_TERRACOTTA)){
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }
        ItemStack whiteWool = new ItemStack(Items.TERRACOTTA, stack.getCount());
        player.setStackInHand(hand, whiteWool);
        LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
        world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY,
                SoundCategory.BLOCKS, 1.0F, 1.0F);

        return ActionResult.SUCCESS;
    }


}