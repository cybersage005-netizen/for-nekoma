package net.greenjab.nekomasfixed.mixin.client;

import net.minecraft.recipe.RecipeFinder;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceScreenHandler.class)
public class AbstractFurnaceScreenMixin {

    @Inject(method = "populateRecipeFinder", at = @At("HEAD"), cancellable = true)
    private void preventRecipePopulation(RecipeFinder finder, CallbackInfo ci) {
        AbstractFurnaceScreenHandler handler = (AbstractFurnaceScreenHandler) (Object) this;

        // Check if this is your kiln
        if (handler.getClass().getName().contains("KilnScreenHandler")) {
            ci.cancel(); // This completely prevents recipe population
        }
    }
}