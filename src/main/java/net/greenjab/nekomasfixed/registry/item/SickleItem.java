package net.greenjab.nekomasfixed.registry.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;

public class SickleItem extends Item {
    public SickleItem(Settings settings) {
        super(settings);
    }
    private int attackCount = 1;
    @Override

    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity player) {

            boolean inMain = player.getMainHandStack().isOf(stack.getItem());
            boolean inOff  = player.getOffHandStack().isOf(stack.getItem());

            if (inMain && inOff) {
                System.out.println("Dual wield sickles combo! attacked on" + target);
            } else if (inMain) {
                System.out.println("Main hand sickle");
            }
        }
    }


}
