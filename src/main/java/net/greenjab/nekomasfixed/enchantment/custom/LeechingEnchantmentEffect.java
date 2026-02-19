package net.greenjab.nekomasfixed.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public class LeechingEnchantmentEffect implements EnchantmentEntityEffect {

    public static final LeechingEnchantmentEffect INSTANCE = new LeechingEnchantmentEffect();
    public static final MapCodec<LeechingEnchantmentEffect> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {

        if (!(user instanceof LivingEntity attacker)) return;

        Entity target = attacker.getAttacking(); // ✅ Yarn-safe target

        if (target instanceof LivingEntity victim) {
            attacker.heal(1.0f * level);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
