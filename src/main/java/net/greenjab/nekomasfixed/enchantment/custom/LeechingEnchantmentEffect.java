package net.greenjab.nekomasfixed.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public record LeechingEnchantmentEffect() implements EnchantmentEntityEffect {
        public static final MapCodec<LeechingEnchantmentEffect> CODEC = MapCodec.unit(LeechingEnchantmentEffect::new);

        public void apply(ServerWorld world, int level, EnchantmentEffectContext context, LivingEntity user, LivingEntity target, Vec3d pos) {
            if(target != null) {
                float damage = 0.0f;
                if(level == 1){
                    damage = target.getHealth() * 0.05f;
                }
                if(level == 2){
                    damage = target.getHealth() * 0.07f;
                }
                if(level == 3){
                    damage = target.getHealth() * 0.1f;
                }
                target.damage((ServerWorld) user.getEntityWorld(), target.getRecentDamageSource(), damage);
                user.heal(damage);
            }
        }

        @Override
        public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {}

        @Override
        public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
            return CODEC;
        }
    }


