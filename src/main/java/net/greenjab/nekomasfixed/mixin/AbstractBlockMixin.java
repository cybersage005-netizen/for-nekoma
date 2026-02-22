package net.greenjab.nekomasfixed.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.greenjab.nekomasfixed.registry.block.HoneyCauldronBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {

    @Unique
    private static final Map<BlockPos, Integer> CHECK_TIMERS = new HashMap<>();

    @Inject(method = "onBlockAdded", at = @At("HEAD"))
    private void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
        if (!world.isClient()) {
            // Start ticking for both normal and honey cauldrons
            if (state.getBlock() == Blocks.CAULDRON || state.getBlock() == BlockRegistry.HONEY_CAULDRON) {
                world.scheduleBlockTick(pos, state.getBlock(), 20);
                System.out.println("Started ticking cauldron at " + pos);
            }
        }
    }

    @Inject(method = "scheduledTick", at = @At("HEAD"))
    private void onScheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        // Only process cauldrons
        if (state.getBlock() != Blocks.CAULDRON && state.getBlock() != BlockRegistry.HONEY_CAULDRON) {
            return;
        }

        // Check every 20 ticks (1 second)
        int timer = CHECK_TIMERS.getOrDefault(pos, 0);
        timer++;
        if (timer < 20) {
            CHECK_TIMERS.put(pos, timer);
            world.scheduleBlockTick(pos, state.getBlock(), 1);
            return;
        }
        CHECK_TIMERS.put(pos, 0);

        // Check for beehive above (2 blocks above)
        BlockPos abovePos = pos.up(2);
        BlockState aboveState = world.getBlockState(abovePos);
        boolean hasBeehive = aboveState.isOf(Blocks.BEEHIVE) || aboveState.isOf(Blocks.BEE_NEST);

        if (!hasBeehive) {
            world.scheduleBlockTick(pos, state.getBlock(), 20);
            return;
        }

        // Handle honey cauldron filling
        if (state.getBlock() == BlockRegistry.HONEY_CAULDRON) {
            int currentLevel = state.get(HoneyCauldronBlock.HONEY_LEVEL);
            if (currentLevel < HoneyCauldronBlock.MAX_LEVEL) {
                world.setBlockState(pos, state.with(HoneyCauldronBlock.HONEY_LEVEL, currentLevel + 1));
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);
                System.out.println("Honey cauldron filled to level " + (currentLevel + 1));
            }
        }

        // Handle normal cauldron conversion to honey cauldron
        if (state.getBlock() == Blocks.CAULDRON ) {
            world.setBlockState(pos, BlockRegistry.HONEY_CAULDRON.getDefaultState()
                    .with(HoneyCauldronBlock.HONEY_LEVEL, 1));
            world.playSound(null, pos, SoundEvents.BLOCK_BEEHIVE_DRIP,
                    SoundCategory.BLOCKS, 1.0F, 1.0F);
            System.out.println("Normal cauldron converted to honey cauldron");
        }

        // Reschedule next tick
        world.scheduleBlockTick(pos, state.getBlock(), 20);
    }
}