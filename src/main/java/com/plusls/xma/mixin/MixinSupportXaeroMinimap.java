package com.plusls.xma.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.plusls.ommc.feature.highlithtWaypoint.HighlightWaypointUtil;
import com.plusls.xma.RenderWaypointUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xaero.map.WorldMap;
import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
import xaero.map.gui.GuiMap;
import xaero.map.mods.SupportXaeroMinimap;
import xaero.map.mods.gui.Waypoint;

@Mixin(value = SupportXaeroMinimap.class, remap = false)
public class MixinSupportXaeroMinimap {
    @Inject(method = "renderWaypoints", at = @At("RETURN"))
    private void drawHighlightWaypoint(GuiMap mapScreen, PoseStack matrixStack,
                                       MultiBufferSource.BufferSource renderTypeBuffers,
                                       MultiTextureRenderTypeRendererProvider rendererProvider,
                                       double cameraX, double cameraZ, int width, int height,
                                       double guiBasedScale, double scale, double mouseX, double mouseZ,
                                       float brightness, Waypoint oldViewed, Minecraft mc,
                                       CallbackInfoReturnable<Waypoint> cir) {
        if (HighlightWaypointUtil.highlightPos == null) {
            return;
        }
        matrixStack.pushPose();
        matrixStack.translate(HighlightWaypointUtil.highlightPos.getX() - cameraX,
                HighlightWaypointUtil.highlightPos.getZ() - cameraZ, 0);
        double wpScale = guiBasedScale * (double) WorldMap.settings.worldmapWaypointsScale / scale * 2.0f;
        matrixStack.scale((float) wpScale, (float) wpScale, 1.0F);
        RenderWaypointUtil.drawHighlightWaypointPTC(matrixStack);
        matrixStack.popPose();

    }
}
