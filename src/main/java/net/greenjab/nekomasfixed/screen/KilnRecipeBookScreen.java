package net.greenjab.nekomasfixed.screen;

import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.text.Text;

public class KilnRecipeBookScreen extends RecipeBookWidget {
    @Override
    protected Text getToggleCraftableButtonText() {
        return Text.translatable("gui.nekomasfixed.kiln.toggle_recipes");
    }
}