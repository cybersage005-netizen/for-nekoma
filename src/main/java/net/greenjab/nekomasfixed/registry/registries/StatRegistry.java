package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

public class StatRegistry {

    public static final Identifier INTERACT_WITH_KILN = register("interact_with_kiln", StatFormatter.DEFAULT);

    private static Identifier register(String id, StatFormatter formatter) {
        Identifier identifier = Identifier.of("nekomasfixed", id);
        Registry.register(Registries.CUSTOM_STAT, id, identifier);
        Stats.CUSTOM.getOrCreateStat(identifier, formatter);
        return identifier;
    }

    public static void registerStats() {
        NekomasFixed.LOGGER.info("Registering custom stats for " + "nekomasfixed");
    }
}