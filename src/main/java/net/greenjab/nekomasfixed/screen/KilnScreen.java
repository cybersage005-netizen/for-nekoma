package net.greenjab.nekomasfixed.screen;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class KilnScreen extends AbstractFurnaceScreen<KilnScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(NekomasFixed.MOD_ID, "textures/gui/container/kiln.png");
    private static final Identifier LIT_PROGRESS_TEXTURE = Identifier.ofVanilla("container/furnace/lit_progress");
    private static final Identifier BURN_PROGRESS_TEXTURE = Identifier.ofVanilla("container/furnace/burn_progress");

    public KilnScreen(KilnScreenHandler handler, PlayerInventory inventory, Text title) {
        super(
                handler,
                inventory,
                title,
                Text.empty(), // Empty text for toggle button (no button)
                TEXTURE,
                LIT_PROGRESS_TEXTURE,
                BURN_PROGRESS_TEXTURE,
                List.of() // Empty list = no recipe book tabs
        );
    }
}