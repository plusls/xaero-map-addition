package com.plusls.xma.compat.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Transformation;
import com.plusls.ommc.feature.highlithtWaypoint.HighlightWaypointUtil;
import com.plusls.xma.RenderWaypointUtil;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import xaero.common.minimap.waypoints.Waypoint;

@Dependencies(or = {@Dependency("xaerominimap"), @Dependency("xaerobetterpvp")})
@Mixin(targets = "xaero.common.gui.GuiWaypoints$List", remap = false)
public class MixinGuiWaypoints_List {
    @Inject(method = "drawWaypointSlot", at = @At(value = "RETURN"))
    private void drawHighlightWaypoint(Waypoint w, int x, int y, CallbackInfo ci) {
        if (w == null) {
            return;
        }
        BlockPos pos = new BlockPos(w.getX(), w.getY(), w.getZ());
        if (pos.equals(HighlightWaypointUtil.highlightPos)) {
            RenderSystem.pushMatrix();
            RenderSystem.translated(x + 200, y + 7, 1.0D);
            RenderWaypointUtil.drawHighlightWaypointPTC(Transformation.identity().getMatrix());
            RenderSystem.popMatrix();
        }
    }
}
