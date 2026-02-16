package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Redirect(
            method = "doAttack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;swingHand(Lnet/minecraft/util/Hand;)V"
            )
    )
    private void redirectSwing(ClientPlayerEntity player, Hand hand) {
        if (player.getMainHandStack().isIn(OtherRegistry.SICKLES)
                && player.getOffHandStack().isIn(OtherRegistry.SICKLES)) {
            return;
        }
        player.swingHand(hand);
    }

}
