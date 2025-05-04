package com.tumod.endportalmod.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndPortalFrameBlock.class)
public class EndPortalFrameBlockMixin {
    @Inject(method = "trySpawnPortal", at = @At("HEAD"), cancellable = true)
    private void onTrySpawnPortal(ServerWorld world, BlockPos pos, CallbackInfo ci) {
        ci.cancel(); // Cancelamos el portal original

        BlockState state = world.getBlockState(pos);
        Direction facing = state.get(Properties.HORIZONTAL_FACING);
        Direction right = facing.rotateYClockwise();
        BlockPos origin = pos.offset(right, 4).down();

        for (int x = 0; x < 3; x++) {
            for (int z = 0; z < 3; z++) {
                BlockPos portalPos = origin.add(x, 0, z);
                world.setBlockState(portalPos, Blocks.END_PORTAL.getDefaultState());
            }
        }
    }
}
