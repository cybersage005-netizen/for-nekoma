package net.greenjab.nekomasfixed.enchantment.effect;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public record LeechingEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<LeechingEnchantmentEffect> CODEC = MapCodec.unit(LeechingEnchantmentEffect::new);

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {
        if (context.owner() instanceof LivingEntity attacker) {
            if (target instanceof LivingEntity victim) {
                if (victim.hurtTime > 0) {
                    attacker.heal(level);
                }
            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}