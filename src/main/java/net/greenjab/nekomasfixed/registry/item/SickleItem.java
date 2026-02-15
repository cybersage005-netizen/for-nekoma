package net.greenjab.nekomasfixed.registry.item;

import com.google.common.collect.Multimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class SickleItem extends Item {

    public static final float DAMAGE_IRON = 1f;
    public static final float SPEED = 4.0f;

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
                if(entityID == previousEntityId){
                    attackCount+=1;
                    previousEntityId = entityID;
                    if(attackCount==5){
                        System.out.println("Now dealing extra damage of " + ((3.0*DAMAGE_IRON)+(3.0*DAMAGE_IRON*0.5)));
                        target.damage((ServerWorld) target.getEntityWorld(), player.getDamageSources().playerAttack(player), (float) ((3.0*DAMAGE_IRON)+(3.0*DAMAGE_IRON*0.5)));
                    }else if(attackCount==6){
                        System.out.println("Now dealing extra damage of " + ((4.0*DAMAGE_IRON)+(4.0*DAMAGE_IRON)));
                        target.damage((ServerWorld) target.getEntityWorld(), player.getDamageSources().playerAttack(player), (float) (2.0*4.0*DAMAGE_IRON));
                        attackCount=1;
                    }
                }else{
                    attackCount = 1;
                    previousEntityId = entityID;
                }


            } else if (inMain) {
                System.out.println("Main hand sickle");
            }
        }
    }


}
