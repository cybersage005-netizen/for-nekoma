package net.greenjab.nekomasfixed.mixin.client;

import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeGridAligner;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceScreenHandler.class)
public class AbstractFurnaceScreenHandlerMixin {

    @Inject(method = "populateRecipeFinder", at = @At("HEAD"), cancellable = true)
    private void preventRecipeFinder(RecipeFinder finder, CallbackInfo ci) {
        AbstractFurnaceScreenHandler handler = (AbstractFurnaceScreenHandler) (Object) this;

        // Check if this is your kiln
        if (handler.getClass().getName().contains("KilnScreenHandler")) {
            // Don't add any items to the recipe finder
            ci.cancel();  // This prevents the method from running
        }
    }
}