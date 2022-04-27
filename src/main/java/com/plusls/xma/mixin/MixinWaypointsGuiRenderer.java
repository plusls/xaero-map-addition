package com.plusls.xma.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.plusls.ommc.feature.highlithtWaypoint.HighlightWaypointUtil;
import com.plusls.xma.RenderWaypointUtil;
import com.plusls.xma.config.Configs;
import net.minecraft.client.renderer.MultiBufferSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import xaero.common.XaeroMinimapSession;
import xaero.common.minimap.render.MinimapRendererHelper;
import xaero.common.minimap.waypoints.render.WaypointsGuiRenderer;

@Dependencies(or = {@Dependency("xaerominimap"), @Dependency("xaerobetterpvp")})
@Mixin(value = WaypointsGuiRenderer.class, remap = false)
public abstract class MixinWaypointsGuiRenderer {

    @Inject(method = "render", at = @At(value = "RETURN"))
    private void drawHighlightWaypoint(
            //#if MC > 11502
            XaeroMinimapSession minimapSession, PoseStack matrixStack,
            MinimapRendererHelper rendererHelper, double playerX, double playerY, double playerZ,
            int specW, int specH, double ps, double pc, float partial, double zoom, boolean circle,
            float minimapScale, MultiBufferSource.BufferSource renderTypeBuffer, boolean safeMode,
            //#else
            //$$ XaeroMinimapSession minimapSession, MinimapRendererHelper rendererHelper,
            //$$ double playerX, double playerZ, int specW, int specH, double ps, double pc,
            //$$ float partial, double zoom, boolean circle, float minimapScale,
            //$$ MultiBufferSource.BufferSource textRenderTypeBuffer,
            //$$ boolean safeMode,
            //#endif
            CallbackInfo ci) {
        if (!Configs.minimapHighlightWaypoint || HighlightWaypointUtil.highlightPos == null) {
            return;
        }
        //#if MC <= 11502
        //$$ PoseStack matrixStack = new PoseStack();
        //#endif
        matrixStack.pushPose();
        matrixStack.translate(0.0D, 0.0D, -980.0D);
        double offx = (double) HighlightWaypointUtil.highlightPos.getX() + 0.5D - playerX;
        double offz = (double) HighlightWaypointUtil.highlightPos.getZ() + 0.5D - playerZ;
        matrixStack.translate(0.0D, 0.0D, 0.1D);
        translatePositionCompat(matrixStack, specW, specH, ps, pc, offx, offz, zoom, circle);
        matrixStack.scale(minimapScale * 0.5f, minimapScale * 0.5f, 1.0F);
        matrixStack.translate(0.0D, 0.0D, 0.05D);

        RenderWaypointUtil.drawHighlightWaypointPTC(matrixStack.last().pose());

        matrixStack.popPose();

    }

    // From WaypointsGuiRenderer.translatePosition
    @SuppressWarnings("DuplicatedCode")
    private static void translatePositionCompat(PoseStack matrixStack, int specW, int specH, double ps,
                                          double pc, double offx, double offy, double zoom, boolean circle) {
        double Y = (pc * offx + ps * offy) * zoom;
        double X = (ps * offx - pc * offy) * zoom;
        double borderedX = X;
        double borderedY = Y;
        if (!circle) {
            if (X > (double) specW) {
                borderedX = specW;
                borderedY = Y * (double) specW / X;
            } else if (X < (double) (-specW)) {
                borderedX = (-specW);
                borderedY = -Y * (double) specW / X;
            }

            if (borderedY > (double) specH) {
                borderedY = specH;
                borderedX = X * (double) specH / Y;
            } else if (borderedY < (double) (-specH)) {
                borderedY = (-specH);
                borderedX = -X * (double) specH / Y;
            }
        } else {
            double distSquared = X * X + Y * Y;
            double maxDistSquared = (specW * specW);
            if (distSquared > maxDistSquared) {
                double scaleDown = Math.sqrt(maxDistSquared / distSquared);
                borderedX = X * scaleDown;
                borderedY = Y * scaleDown;
            }
        }
        matrixStack.translate((double) (Math.round(borderedX) - 1L), (double) (Math.round(borderedY) - 1L), 0.0);
    }
}
