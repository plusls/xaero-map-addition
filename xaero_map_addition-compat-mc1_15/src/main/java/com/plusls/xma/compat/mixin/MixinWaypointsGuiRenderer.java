package com.plusls.xma.compat.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Transformation;
import com.plusls.ommc.feature.highlithtWaypoint.HighlightWaypointUtil;
import com.plusls.xma.RenderWaypointUtil;
import com.plusls.xma.config.Configs;
import net.minecraft.client.renderer.MultiBufferSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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

    @Shadow
    public abstract void translatePosition(int specW, int specH, double ps, double pc, double offx, double offy,
                                           double zoom, boolean circle);

    @Inject(method = "render", at = @At(value = "RETURN"))
    private void drawHighlightWaypoint(XaeroMinimapSession minimapSession, MinimapRendererHelper rendererHelper,
                                       double playerX, double playerZ, int specW, int specH, double ps, double pc,
                                       float partial, double zoom, boolean circle, float minimapScale,
                                       MultiBufferSource.BufferSource textRenderTypeBuffer,
                                       boolean safeMode, CallbackInfo ci) {
        if (!Configs.minimapHighlightWaypoint || HighlightWaypointUtil.highlightPos == null) {
            return;
        }
        RenderSystem.pushMatrix();
        RenderSystem.translated(0.0D, 0.0D, -980.0D);
        double offx = (double) HighlightWaypointUtil.highlightPos.getX() + 0.5D - playerX;
        double offz = (double) HighlightWaypointUtil.highlightPos.getZ() + 0.5D - playerZ;
        RenderSystem.translated(0.0D, 0.0D, 0.1D);
        this.translatePosition(specW, specH, ps, pc, offx, offz, zoom, circle);
        RenderSystem.scalef(minimapScale * 0.5f, minimapScale * 0.5f, 1.0F);
        RenderSystem.translated(0.0D, 0.0D, 0.05D);


        RenderWaypointUtil.drawHighlightWaypointPTC(Transformation.identity().getMatrix());

        RenderSystem.popMatrix();
        RenderSystem.popMatrix();

    }
}
