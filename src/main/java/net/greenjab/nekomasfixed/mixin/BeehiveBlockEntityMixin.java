package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.block.HoneyCauldronBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.util.HoneyCauldronUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(BeehiveBlockEntity.class)
public class BeehiveBlockEntityMixin {

    @Inject(method = "releaseBee", at = @At("HEAD"))
    private static void onReleaseBee(World world, BlockPos pos, BlockState state,
                                     BeehiveBlockEntity.BeeData bee, @Nullable List<Entity> entities,
                                     BeehiveBlockEntity.BeeState beeState, @Nullable BlockPos flowerPos,
                                     CallbackInfoReturnable<Boolean> cir) {

        // Only care about honey delivery
        if (beeState != BeehiveBlockEntity.BeeState.HONEY_DELIVERED) return;

        // Check for cauldron 2 blocks below
        BlockPos belowPos = pos.down(2);
        BlockState belowState = world.getBlockState(belowPos);

        // If there's a honey cauldron below, fill it
        if (belowState.getBlock() == BlockRegistry.HONEY_CAULDRON) {
            HoneyCauldronUtil.incrementHoneyLevel(world, belowPos, belowState);
        }
        // If there's an empty cauldron below, convert it
        else if (belowState.getBlock() == Blocks.CAULDRON) {
            world.setBlockState(belowPos, BlockRegistry.HONEY_CAULDRON.getDefaultState()
                    .with(HoneyCauldronBlock.HONEY_LEVEL, 1));
            world.playSound(null, belowPos, SoundEvents.BLOCK_BEEHIVE_DRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);

        }
    }
}