package com.plusls.xma.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.plusls.ommc.feature.highlithtWaypoint.HighlightWaypointUtil;
import com.plusls.xma.RenderWaypointUtil;
import com.plusls.xma.config.Configs;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependency;
import xaero.common.minimap.waypoints.Waypoint;

@Dependencies(or = {@Dependency("xaerominimap"), @Dependency("xaerobetterpvp")})
@Mixin(targets = "xaero.common.gui.GuiWaypoints$List", remap = false)
public class MixinGuiWaypoints_List {
    @Inject(method = "drawWaypointSlot", at = @At(value = "RETURN"))
    private void drawHighlightWaypoint(
            //#if MC > 11502
            PoseStack matrixStack,
            //#endif
            Waypoint w, int x, int y, CallbackInfo ci) {
        if (!Configs.minimapHighlightWaypoint || w == null) {
            return;
        }
        //#if MC <= 11502
        //$$ PoseStack matrixStack = new PoseStack();
        //#endif

        BlockPos pos = new BlockPos(w.getX(), w.getY(), w.getZ());
        if (pos.equals(HighlightWaypointUtil.highlightPos)) {
            matrixStack.pushPose();
            matrixStack.translate(x + 200, y + 7, 1.0D);
            RenderWaypointUtil.drawHighlightWaypointPTC(matrixStack.last().pose());
            matrixStack.popPose();
        }
    }
}
