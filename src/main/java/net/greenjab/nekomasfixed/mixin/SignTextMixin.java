package net.greenjab.nekomasfixed.mixin;

import net.minecraft.block.entity.SignText;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SignText.class)
public class SignTextMixin {
    @Inject(method = "withColor", at = @At("HEAD"), cancellable = true)
    private void customDyeChanges(DyeColor color, CallbackInfoReturnable<SignText> cir){
        SignText self = (SignText)(Object)this;
        if(color == DyeColor.BLACK){
            System.out.println("Black dye applied to sign!");
        }

    }
}
