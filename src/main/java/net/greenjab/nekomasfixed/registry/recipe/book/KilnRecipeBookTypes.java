package net.greenjab.nekomasfixed.registry.recipe.book;

import net.greenjab.nekomasfixed.mixin.RecipeBookTypeInvoker;
import net.minecraft.recipe.book.RecipeBookType;

public class KilnRecipeBookTypes {
    public static final RecipeBookType KILN;

    static {
        RecipeBookType[] oldValues = RecipeBookTypeInvoker.invokeValues();

        KILN = RecipeBookTypeInvoker.invokeInit("KILN", oldValues.length);
    }
}