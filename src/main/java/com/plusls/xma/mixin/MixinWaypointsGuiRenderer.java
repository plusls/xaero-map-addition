package com.plusls.xma.mixin;

import org.spongepowered.asm.mixin.Mixin;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependency;
import xaero.common.minimap.waypoints.render.WaypointsGuiRenderer;

//#if MC <= 11502
//$$ import com.mojang.blaze3d.vertex.PoseStack;
//$$ import com.plusls.ommc.feature.highlithtWaypoint.HighlightWaypointUtil;
//$$ import com.plusls.xma.RenderWaypointUtil;
//$$ import com.plusls.xma.config.Configs;
//$$ import net.minecraft.client.renderer.MultiBufferSource;
//$$ import org.spongepowered.asm.mixin.injection.At;
//$$ import org.spongepowered.asm.mixin.injection.Inject;
//$$ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//$$ import xaero.common.XaeroMinimapSession;
//$$ import xaero.common.minimap.render.MinimapRendererHelper;
//#endif
@Dependencies(or = {@Dependency("xaerominimap"), @Dependency("xaerobetterpvp")})
@Mixin(value = WaypointsGuiRenderer.class, remap = false)
public abstract class MixinWaypointsGuiRenderer {
    //#if MC <= 11502
    //$$ @Inject(method = "render", at = @At(value = "RETURN"))
    //$$ private void drawHighlightWaypoint(XaeroMinimapSession minimapSession, MinimapRendererHelper rendererHelper,
    //$$                                    double playerX, double playerZ, int specW, int specH, double ps, double pc,
    //$$                                    float partial, double zoom, boolean circle, float minimapScale,
    //$$                                    MultiBufferSource.BufferSource textRenderTypeBuffer,
    //$$                                    boolean safeMode,
    //$$                                    CallbackInfo ci) {
    //$$     if (!Configs.minimapHighlightWaypoint || HighlightWaypointUtil.highlightPos == null) {
    //$$         return;
    //$$     }
    //$$    PoseStack matrixStack = new PoseStack();
    //$$     matrixStack.pushPose();
    //$$     matrixStack.translate(0.0D, 0.0D, -980.0D);
    //$$     double offx = (double) HighlightWaypointUtil.highlightPos.getX() + 0.5D - playerX;
    //$$      double offz = (double) HighlightWaypointUtil.highlightPos.getZ() + 0.5D - playerZ;
    //$$     matrixStack.translate(0.0D, 0.0D, 0.1D);
    //$$     RenderWaypointUtil.translatePositionCompat(matrixStack, specW, specH, ps, pc, offx, offz, zoom, circle);
    //$$     matrixStack.scale(minimapScale * 0.5f, minimapScale * 0.5f, 1.0F);
    //$$     matrixStack.translate(0.0D, 0.0D, 0.05D);
    //$$     RenderWaypointUtil.drawHighlightWaypointPTC(matrixStack.last().pose());
    //$$     matrixStack.popPose();
    //$$ }
    //#endif
}
