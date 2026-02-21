package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
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

    @Override
    public MapCodec<? extends AbstractCauldronBlock> getCodec() {
        return CODEC;
    }

    public HoneyCauldronBlock(Settings settings) {
        super(settings, HONEY_BEHAVIOR);
        registerBehaviors();
    }

    private void registerBehaviors() {
        Map<Item, CauldronBehavior> map = HONEY_BEHAVIOR.map();

        // Empty bucket takes honey
        map.put(Items.BUCKET, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient()) {
                player.setStackInHand(hand, new ItemStack(Items.HONEY_BOTTLE));
                world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            return ActionResult.SUCCESS;
        });

        // Honey bottle fills cauldron
        map.put(Items.HONEY_BOTTLE, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient()) {
                player.setStackInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
                world.setBlockState(pos, this.getDefaultState());
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            return ActionResult.SUCCESS;
        });


        map.put(Items.GLASS_BOTTLE, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient()) {
                player.setStackInHand(hand, new ItemStack(Items.HONEY_BOTTLE));
                if (shouldDecrementLevel()) {
                    world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
                }
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            return ActionResult.SUCCESS;
        });
    }

    @Override
    protected double getFluidHeight(BlockState state) {
        return 0.9375;
    }

    @Override
    public boolean isFull(BlockState state) {
        return true;
    }


    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity living && !world.isClient()) {
            living.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.SLOWNESS, 100, 1));
            living.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.JUMP_BOOST, 100, 0));
        }
    }

    @Override
    protected boolean canBeFilledByDripstone(Fluid fluid) {
        return fluid == Fluids.WATER;
    }


    protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return 3;
    }

    private boolean shouldDecrementLevel() {
        return true;
    }
}