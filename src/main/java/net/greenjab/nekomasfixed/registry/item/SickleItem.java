package net.greenjab.nekomasfixed.registry.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;

public class SickleItem extends Item {
    public SickleItem(Settings settings) {
        super(settings);
    }
    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity player) { //Important
            if (player.getMainHandStack() == stack) {
                if(player.getOffHandStack() == stack){
                    System.out.println("Ready for combo");
                }else{
                    System.out.println(player.getOffHandStack());
                }
            }
        }

    }

}
