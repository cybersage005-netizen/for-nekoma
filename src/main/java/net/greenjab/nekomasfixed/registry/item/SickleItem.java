package net.greenjab.nekomasfixed.registry.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;

public class SickleItem extends Item {
    public SickleItem(Settings settings) {
        super(settings);
    }
    int previousEntityId;
    private int attackCount = 1;
    @Override

    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity player) {

            boolean inMain = player.getMainHandStack().isOf(stack.getItem());
            boolean inOff  = player.getOffHandStack().isOf(stack.getItem());
            String entityTargetted = target.getEntity().getName().getString();
            int entityID = target.getEntity().getId();
            if (inMain && inOff) {
                System.out.println("Dual wield sickles combo! attacked on " + target.getEntity().getId());
                if(entityID == previousEntityId){
                    attackCount+=1;
                    previousEntityId = entityID;
                }else{
                    previousEntityId = entityID;
                }
                System.out.println("Combo at lvl: " + attackCount);
            } else if (inMain) {
                System.out.println("Main hand sickle");
            }
        }
    }


}
