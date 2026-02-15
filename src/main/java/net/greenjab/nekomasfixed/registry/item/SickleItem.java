package net.greenjab.nekomasfixed.registry.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

public class SickleItem extends Item {

    public static final float DAMAGE_IRON = 2f;
    public static final float SPEED = 4.0f;

    public SickleItem(Item.Settings settings) {
        super(settings);
    }

    long lastHitAt = 0;
    int previousEntityId;
    private int attackCount = 1;

    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!(attacker instanceof PlayerEntity player)) return;

        long now = attacker.getEntityWorld().getTime();

        boolean inMain = player.getMainHandStack().isOf(stack.getItem());
        boolean inOff  = player.getOffHandStack().isOf(stack.getItem());

        int entityID = target.getId();

        if (inMain && inOff) {

            if (now - lastHitAt > 40) {
                attackCount = 1;
            }

            lastHitAt = now;

            if (entityID == previousEntityId) {
                attackCount++;
            } else {
                attackCount = 1;
                previousEntityId = entityID;
            }

            System.out.println("Combo: " + attackCount);
            if (attackCount == 5) {
                float dmg = (float)((3.0 * DAMAGE_IRON) * 1.5);

                target.damage((ServerWorld) target.getEntityWorld(), player.getDamageSources().playerAttack(player), dmg);
                System.out.println("Combo 5 bonus dmg: " + dmg);
            }
            else if (attackCount == 6) {
                float dmg = 2f * 4f * DAMAGE_IRON;

                target.damage((ServerWorld) target.getEntityWorld(), player.getDamageSources().playerAttack(player), dmg);
                System.out.println("Combo finisher dmg: " + dmg);

                attackCount = 1;
            }
        }
    }
}
