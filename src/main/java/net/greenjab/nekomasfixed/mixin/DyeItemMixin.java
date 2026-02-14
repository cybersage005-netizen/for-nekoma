package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.greenjab.nekomasfixed.util.ModColors.AMBER;

@Mixin(DyeItem.class)
public class DyeItemMixin {
    @Inject(method = "useOnSign", at = @At("HEAD"), cancellable = true)
    private void changeDye(World world, SignBlockEntity signBlockEntity, boolean front, PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = player.getStackInHand(player.getActiveHand());
        if (stack.isOf(ItemRegistry.AMBER_DYE)) {
            final int dyeFromMod = AMBER.getColor();
            var signText = signBlockEntity.getText(front);
            for (int i = 0; i < 4; i++) {
                Text line = signText.getMessage(i, false);
                MutableText newLine = line.copyContentOnly();
                newLine.setStyle(line.getStyle().withColor(dyeFromMod).withFormatting(Formatting.BOLD));

                signText = signText.withMessage(i, newLine, newLine);
            }

            signBlockEntity.setText(signText, front);

            signBlockEntity.markDirty();
            signBlockEntity.getWorld().updateListeners(
                    signBlockEntity.getPos(),
                    signBlockEntity.getCachedState(),
                    signBlockEntity.getCachedState(),
                    Block.NOTIFY_ALL
            );

            cir.setReturnValue(true); // cancel vanilla dye logic
        }
    }
}
