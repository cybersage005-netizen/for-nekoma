package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;

public class HoneyCauldronBlock extends AbstractCauldronBlock {
    public static final MapCodec<HoneyCauldronBlock> CODEC = createCodec(HoneyCauldronBlock::new);
    public HoneyCauldronBlock(Settings settings) {
        super(settings, createBehaviorMap());
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(LeveledCauldronBlock.LEVEL, 3));
    }

    private static CauldronBehavior.CauldronBehaviorMap createBehaviorMap() {
        var behaviorMap = CauldronBehavior.createMap("honey");
        var map = behaviorMap.map();


        map.put(Items.GLASS_BOTTLE, (state, world, pos, player, hand, stack) -> {
            System.out.println("Glass bottle used on honey cauldron at " + pos);
            if (!world.isClient()) {
                int level = state.get(LeveledCauldronBlock.LEVEL);
                System.out.println("Current level: " + level);
                player.setStackInHand(hand, new ItemStack(Items.HONEY_BOTTLE));

                if (level > 1) {
                    world.setBlockState(pos, state.with(LeveledCauldronBlock.LEVEL, level - 1));
                    System.out.println("Level decreased to " + (level - 1));
                } else {
                    world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
                    System.out.println("Cauldron emptied");
                }

                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            return ActionResult.SUCCESS;
        });


        map.put(Items.HONEY_BOTTLE, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient()) {
                int level = state.get(LeveledCauldronBlock.LEVEL);
                if (level < 3) {
                    world.setBlockState(pos, state.with(LeveledCauldronBlock.LEVEL, level + 1));
                    player.setStackInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
                    world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY,
                            SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }
            return ActionResult.SUCCESS;
        });

        return behaviorMap;
    }


    @Override
    protected MapCodec<? extends AbstractCauldronBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected double getFluidHeight(BlockState state) {
        return (6.0 + state.get(LeveledCauldronBlock.LEVEL) * 3.0) / 16.0;
    }

    @Override
    public boolean isFull(BlockState state) {
        return state.get(LeveledCauldronBlock.LEVEL) == 3;
    }

    @Override
    protected void appendProperties(net.minecraft.state.StateManager.Builder<net.minecraft.block.Block, BlockState> builder) {
        builder.add(LeveledCauldronBlock.LEVEL);
    }
}