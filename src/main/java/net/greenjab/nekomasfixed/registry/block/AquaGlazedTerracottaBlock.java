package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.Direction;

public class AquaGlazedTerracottaBlock extends HorizontalFacingBlock {
    protected AquaGlazedTerracottaBlock(Settings settings) {
        super(settings);
    }

    public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return null;
    }
}
