package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;

public class HoneyCauldronBlock extends AbstractCauldronBlock {
    public static final MapCodec<HoneyCauldronBlock> CODEC = createCodec(HoneyCauldronBlock::new);

    private static final CauldronBehavior.CauldronBehaviorMap HONEY_BEHAVIOR =
            CauldronBehavior.createMap("honey");

    static {
        Map<Item, CauldronBehavior> map = HONEY_BEHAVIOR.map();

    }

    public HoneyCauldronBlock(Settings settings) {
        super(settings, HONEY_BEHAVIOR);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(LeveledCauldronBlock.LEVEL, 3)); // Start full
    }

    @Override
    protected MapCodec<? extends AbstractCauldronBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected double getFluidHeight(BlockState state) {
        return (6.0 + (double)state.get(LeveledCauldronBlock.LEVEL) * 3.0) / 16.0;
    }

    @Override
    public boolean isFull(BlockState state) {
        return state.get(LeveledCauldronBlock.LEVEL) == 3;
    }


    protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(LeveledCauldronBlock.LEVEL);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LeveledCauldronBlock.LEVEL);
    }


}