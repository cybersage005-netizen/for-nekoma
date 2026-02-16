//package net.greenjab.nekomasfixed.mixin;
//
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.util.Hand;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Redirect;
//
//@Mixin(MinecraftClient.class)
//public class SickleItemMixin {
//
//    @Redirect(
//            method = "doAttack",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/entity/player/PlayerEntity;swingHand(Lnet/minecraft/util/Hand;)V"
//            )
//    )
//    private void redirectSwing(PlayerEntity player, Hand hand) {
//        boolean inMain = player.getMainHandStack().isOf(Item);
//        boolean inOff  = player.getOffHandStack().isOf(stack.getItem());
//    }
//}
