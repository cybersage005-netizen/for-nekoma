package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HoneyCauldronBlock extends AbstractCauldronBlock {
    public static final MapCodec<HoneyCauldronBlock> CODEC = createCodec(HoneyCauldronBlock::new);

    // Define custom property for levels 1-4
    public static final IntProperty HONEY_LEVEL = IntProperty.of("honey_level", 1, 4);

    public static final int MAX_LEVEL = 4;

    public HoneyCauldronBlock(Settings settings) {
        super(settings, createBehaviorMap());
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(HONEY_LEVEL, MAX_LEVEL));
    }

    @Override
    protected MapCodec<? extends AbstractCauldronBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HONEY_LEVEL);
    }

    private static CauldronBehavior.CauldronBehaviorMap createBehaviorMap() {
        var behaviorMap = CauldronBehavior.createMap("honey");
        var map = behaviorMap.map();

        // Glass bottle takes honey
        map.put(Items.GLASS_BOTTLE, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient()) {
                int level = state.get(HONEY_LEVEL);
                player.setStackInHand(hand, new ItemStack(Items.HONEY_BOTTLE));

                if (level > 1) {
                    world.setBlockState(pos, state.with(HONEY_LEVEL, level - 1));
                } else {
                    world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
                }

                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            return ActionResult.SUCCESS;
        });

        // Honey bottle adds honey
        map.put(Items.HONEY_BOTTLE, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient()) {
                int level = state.get(HONEY_LEVEL);
                if (level < MAX_LEVEL) {
                    world.setBlockState(pos, state.with(HONEY_LEVEL, level + 1));
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
    protected double getFluidHeight(BlockState state) {
        return (4.0 + state.get(HONEY_LEVEL) * 3.0) / 16.0;
    }

    @Override
    public boolean isFull(BlockState state) {
        return state.get(HONEY_LEVEL) == MAX_LEVEL;
    }
}