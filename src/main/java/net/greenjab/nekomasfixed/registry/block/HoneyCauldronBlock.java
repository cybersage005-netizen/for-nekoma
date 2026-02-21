package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;

public class HoneyCauldronBlock extends AbstractCauldronBlock {
    public static final MapCodec<HoneyCauldronBlock> CODEC = createCodec(HoneyCauldronBlock::new);

    private static final CauldronBehavior.CauldronBehaviorMap HONEY_BEHAVIOR =
            CauldronBehavior.createMap("honey");

    static {
        System.out.println("Registering Honey Cauldron Behaviors!");

        Map<Item, CauldronBehavior> map = HONEY_BEHAVIOR.map();

        // Glass bottle takes honey out
        map.put(Items.GLASS_BOTTLE, (state, world, pos, player, hand, stack) -> {
            System.out.println("HONEY CAULDRON: Glass bottle used at " + pos);
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

        // Honey bottle adds honey
        map.put(Items.HONEY_BOTTLE, (state, world, pos, player, hand, stack) -> {
            System.out.println("HONEY CAULDRON: Honey bottle used at " + pos);
            if (!world.isClient()) {
                int level = state.get(LeveledCauldronBlock.LEVEL);
                System.out.println("Current level: " + level);

                if (level < 3) {
                    world.setBlockState(pos, state.with(LeveledCauldronBlock.LEVEL, level + 1));
                    player.setStackInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
                    System.out.println("Level increased to " + (level + 1));

                    world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY,
                            SoundCategory.BLOCKS, 1.0F, 1.0F);
                } else {
                    System.out.println("Already full!");
                }
            }
            return ActionResult.SUCCESS;
        });
    }

    public HoneyCauldronBlock(Settings settings) {
        super(settings, HONEY_BEHAVIOR);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(LeveledCauldronBlock.LEVEL, 3));
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