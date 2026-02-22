package net.greenjab.nekomasfixed.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBlock.class)
public class BlockTickSchedulerMixin {

    @Inject(method = "onBlockAdded", at = @At("TAIL"))
    private void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
        if (world.isClient()) return;

        // Schedule tick for honey cauldrons too!
        if (state.getBlock() == BlockRegistry.HONEY_CAULDRON) {
            ((ServerWorld)world).scheduleBlockTick(pos, state.getBlock(), 20);
            System.out.println("✅ Scheduled tick for honey cauldron at " + pos);
        }
    }
}