package net.greenjab.nekomasfixed.enchantment;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.enchantment.custom.LeechingEnchantmentEffect;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantmentEffects {
    public static final MapCodec<? extends EnchantmentEntityEffect> LEECHING =  registerEntityEffect("leeching", LeechingEnchantmentEffect.CODEC);


    private static MapCodec<? extends EnchantmentEntityEffect> registerEntityEffect(String name, MapCodec<? extends EnchantmentEntityEffect> codec){
        return Registry.register(
                Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE,
                Identifier.of("nekomasfixed", name),
                codec
        );
    }

    public static void registerEnchantmentEffects(){
        NekomasFixed.LOGGER.info("Registering Mod Enchantments...");
    }
}
