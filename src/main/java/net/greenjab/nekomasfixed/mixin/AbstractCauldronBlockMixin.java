package net.greenjab.nekomasfixed.mixin;

import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.greenjab.nekomasfixed.registry.block.HoneyCauldronBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(AbstractCauldronBlock.class)
public class AbstractCauldronBlockMixin {

    @Unique
    private static final Map<BlockPos, Integer> CHECK_TIMERS = new HashMap<>();

    @Inject(method = "scheduledTick", at = @At("HEAD"))
    private void onScheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {

        // 🔴 RESCHEDULE IMMEDIATELY - THIS IS KEY!
        world.scheduleBlockTick(pos, state.getBlock(), 20);
        System.out.println("⏰ Tick at " + pos + " - rescheduled next tick in 20");

        // Only process honey cauldrons
        if (state.getBlock() != BlockRegistry.HONEY_CAULDRON) {
            return;
        }

        // Check every 20 ticks
        int timer = CHECK_TIMERS.getOrDefault(pos, 0);
        timer++;
        if (timer < 20) {
            CHECK_TIMERS.put(pos, timer);
            return;
        }
        CHECK_TIMERS.put(pos, 0);

        System.out.println("🍯 Processing honey cauldron at " + pos);

        // Check for beehive above (2 blocks above)
        BlockPos abovePos = pos.up(2);
        BlockState aboveState = world.getBlockState(abovePos);
        boolean hasBeehive = aboveState.isOf(Blocks.BEEHIVE) || aboveState.isOf(Blocks.BEE_NEST);

        if (!hasBeehive) return;

        // Fill honey cauldron
        int currentLevel = state.get(HoneyCauldronBlock.HONEY_LEVEL);
        if (currentLevel < HoneyCauldronBlock.MAX_LEVEL) {
            world.setBlockState(pos, state.with(HoneyCauldronBlock.HONEY_LEVEL, currentLevel + 1));
            world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY,
                    SoundCategory.BLOCKS, 1.0F, 1.0F);
            System.out.println("✅ Honey cauldron filled to level " + (currentLevel + 1));
        }
    }
}