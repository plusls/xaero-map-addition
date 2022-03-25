package com.plusls.xma.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.plusls.ommc.feature.highlithtWaypoint.HighlightWaypointUtil;
import com.plusls.xma.RenderWaypointUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.common.XaeroMinimapSession;
import xaero.common.minimap.render.MinimapRendererHelper;
import xaero.common.minimap.waypoints.render.WaypointsGuiRenderer;

@Mixin(value = WaypointsGuiRenderer.class, remap = false)
public abstract class MixinWaypointsGuiRenderer {

    @Shadow
    public abstract void translatePosition(PoseStack matrixStack, int specW, int specH, double ps, double pc,
                                           double offx, double offy, double zoom, boolean circle);

    @Inject(method = "render", at = @At(value = "RETURN"))
    private void drawHighlightWaypoint(XaeroMinimapSession minimapSession, PoseStack matrixStack,
                                       MinimapRendererHelper rendererHelper, double playerX, double playerY, double playerZ,
                                       int specW, int specH, double ps, double pc, float partial, double zoom, boolean circle,
                                       float minimapScale, MultiBufferSource.BufferSource renderTypeBuffer, boolean safeMode,
                                       CallbackInfo ci) {
        if (HighlightWaypointUtil.highlightPos == null) {
            return;
        }
        matrixStack.pushPose();
        matrixStack.translate(0.0D, 0.0D, -980.0D);
        double offx = (double) HighlightWaypointUtil.highlightPos.getX() + 0.5D - playerX;
        double offz = (double) HighlightWaypointUtil.highlightPos.getZ() + 0.5D - playerZ;
        matrixStack.translate(0.0D, 0.0D, 0.1D);
        this.translatePosition(matrixStack, specW, specH, ps, pc, offx, offz, zoom, circle);
        matrixStack.scale(minimapScale * 0.5f, minimapScale * 0.5f, 1.0F);
        matrixStack.translate(0.0D, 0.0D, 0.05D);


        RenderWaypointUtil.drawHighlightWaypointPTC(matrixStack);

        matrixStack.popPose();
        matrixStack.popPose();

    }
}
