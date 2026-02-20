package net.greenjab.nekomasfixed.client.screen;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.screen.KilnScreenHandler;
import net.minecraft.client.gui.Click;import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.client.input.KeyInput;import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class KilnScreen extends AbstractFurnaceScreen<KilnScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(NekomasFixed.MOD_ID, "textures/gui/container/kiln.png");
    private static final Identifier LIT_PROGRESS_TEXTURE = Identifier.ofVanilla("container/furnace/lit_progress");
    private static final Identifier BURN_PROGRESS_TEXTURE = Identifier.ofVanilla("container/furnace/burn_progress");

    public KilnScreen(KilnScreenHandler handler, PlayerInventory inventory, Text title) {
        super(
                handler,
                inventory,
                title,
                null,
                TEXTURE,
                LIT_PROGRESS_TEXTURE,
                BURN_PROGRESS_TEXTURE,
                null
        );
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        // Prevent recipe book from opening
        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        // Prevent recipe book hotkey
        return super.keyPressed(input);
    }
}