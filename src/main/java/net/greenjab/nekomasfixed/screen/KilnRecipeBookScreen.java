package net.greenjab.nekomasfixed.screen;

import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.recipebook.GhostRecipe;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.display.RecipeDisplay;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.context.ContextParameterMap;

import java.util.List;

public class KilnRecipeBookScreen extends RecipeBookWidget {
    public KilnRecipeBookScreen(AbstractRecipeScreenHandler craftingScreenHandler, List list) {
        super(craftingScreenHandler, list);
    }

    @Override
    protected ButtonTextures getBookButtonTextures() {
        return null;
    }

    @Override
    protected boolean isCraftingSlot(Slot slot) {
        return false;
    }

    @Override
    protected void populateRecipes(RecipeResultCollection recipeResultCollection, RecipeFinder recipeFinder) {

    }

    @Override
    protected Text getToggleCraftableButtonText() {
        return Text.translatable("gui.nekomasfixed.kiln.toggle_recipes");
    }

    @Override
    protected void showGhostRecipe(GhostRecipe ghostRecipe, RecipeDisplay display, ContextParameterMap context) {

    }
}