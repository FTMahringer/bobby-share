package net.ngk22.bobbyshare.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.ngk22.bobbyshare.BobbyShare;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Invalidates Bobby data whenever the server successfully changes a block. */
@Mixin(Level.class)
public abstract class BlockChangeMixin {

    @Inject(method = "setBlock", at = @At("RETURN"))
    private void bobbyShare$onSetBlock(
        BlockPos pos,
        BlockState state,
        int flags,
        int recursionDepth,
        CallbackInfoReturnable<Boolean> cir
    ) {
        if (
            cir.getReturnValueZ() && (Object) this instanceof ServerLevel world
        ) {
            BobbyShare.queueChunkInvalidation(
                world,
                pos.getX() >> 4,
                pos.getZ() >> 4
            );
        }
    }
}
