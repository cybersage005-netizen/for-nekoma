package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.block.HoneyCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LeveledCauldronBlock.class)
public class HoneyAutoFillMixin {

    @Inject(method = "precipitationTick", at = @At("HEAD"))
    private void onPrecipitationTick(BlockState state, World world, BlockPos pos, Biome.Precipitation precipitation, CallbackInfo ci) {
        if (world.isClient()) return;

        // Only affect honey cauldrons
        if (state.getBlock() != BlockRegistry.HONEY_CAULDRON) return;

        // Check for beehive 2 blocks above
        BlockPos abovePos = pos.up(2);
        BlockState aboveState = world.getBlockState(abovePos);
        if (!aboveState.isOf(Blocks.BEEHIVE) && !aboveState.isOf(Blocks.BEE_NEST)) return;

        // Fill the honey cauldron
        int level = state.get(HoneyCauldronBlock.HONEY_LEVEL);
        if (level < HoneyCauldronBlock.MAX_LEVEL) {
            world.setBlockState(pos, state.with(HoneyCauldronBlock.HONEY_LEVEL, level + 1));
            world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            System.out.println("Honey cauldron auto-filled to level " + (level + 1));
        }
    }
}