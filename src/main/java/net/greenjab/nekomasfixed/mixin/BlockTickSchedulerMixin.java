package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.block.HoneyCauldronBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBlock.class)
public class BlockTickSchedulerMixin {

    @Inject(method = "onBlockAdded", at = @At("TAIL"))
    private void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
        if (world.isClient()) return;

        // Check if this is a normal cauldron with a beehive above
        if (state.getBlock() == Blocks.CAULDRON) {
            BlockPos abovePos = pos.up(2);
            BlockState aboveState = world.getBlockState(abovePos);

            if (aboveState.isOf(Blocks.BEE_NEST) || aboveState.isOf(Blocks.BEEHIVE)) {

                world.setBlockState(pos, BlockRegistry.HONEY_CAULDRON.getDefaultState()
                        .with(HoneyCauldronBlock.HONEY_LEVEL, 1));

            }
        }
        if (state.getBlock() == Blocks.BEEHIVE || state.getBlock() == Blocks.BEE_NEST) {
            BlockPos downPos = pos.down(2);
            BlockState downState = world.getBlockState(downPos);

            if (downState.isOf(Blocks.CAULDRON) || downState.isOf(BlockRegistry.HONEY_CAULDRON)) {

                world.setBlockState(pos, BlockRegistry.HONEY_CAULDRON.getDefaultState()
                        .with(HoneyCauldronBlock.HONEY_LEVEL, 1));

            }
        }
        if (state.getBlock() == BlockRegistry.HONEY_CAULDRON) {
            world.scheduleBlockTick(pos, state.getBlock(), 20);
        }
    }
}