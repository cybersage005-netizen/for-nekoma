package net.greenjab.nekomasfixed.enchantment;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.enchantment.custom.LeechingEnchantmentEffect;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantmentEffects {

    public static final MapCodec<? extends EnchantmentEntityEffect> LEECHING =
            Registry.register(
                    Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE,
                    Identifier.of("nekomasfixed", "leeching"),
                    LeechingEnchantmentEffect.CODEC
            );

    public static void register() {}
}
