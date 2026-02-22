package net.greenjab.nekomasfixed.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.greenjab.nekomasfixed.registry.block.HoneyCauldronBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(LeveledCauldronBlock.class)
public class LeveledCauldronBlockMixin {

    @Unique
    private static final Map<BlockPos, Integer> CHECK_TIMERS = new HashMap<>();

    @Inject(method = "precipitationTick", at = @At("HEAD"))
    private void onPrecipitationTick(BlockState state, World world, BlockPos pos, Biome.Precipitation precipitation, CallbackInfo ci) {
        if (world.isClient()) return;

        System.out.println("precipitationTick called for " + state.getBlock() + " at " + pos);

        // Check for beehive above (2 blocks above)
        BlockPos abovePos = pos.up(2);
        BlockState aboveState = world.getBlockState(abovePos);
        boolean hasBeehive = aboveState.isOf(Blocks.BEEHIVE) || aboveState.isOf(Blocks.BEE_NEST);

        if (!hasBeehive) return;

        // Handle conversion of water cauldron to honey cauldron
        if (state.getBlock() == Blocks.WATER_CAULDRON) {
            int level = state.get(LeveledCauldronBlock.LEVEL);
            world.setBlockState(pos, BlockRegistry.HONEY_CAULDRON.getDefaultState()
                    .with(HoneyCauldronBlock.HONEY_LEVEL, level));
            System.out.println("Converted water cauldron to honey cauldron at level " + level);
        }
    }


}