package com.plusls.xma.mixin;

import com.plusls.xma.config.Configs;
import org.spongepowered.asm.mixin.Mixin;

//#if MC > 11502
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.vertex.PoseStack;
import com.plusls.ommc.feature.highlithtWaypoint.HighlightWaypointUtil;
import com.plusls.xma.RenderWaypointUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import xaero.common.AXaeroMinimap;
import xaero.common.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
import xaero.common.minimap.element.render.over.MinimapElementOverMapRendererHandler;
import xaero.common.minimap.render.MinimapRendererHelper;
//#else
//$$ import net.minecraft.client.Minecraft;
//#endif

//#if MC > 11502
@Dependencies(or = {@Dependency("xaerominimap"), @Dependency("xaerobetterpvp")})
@Mixin(MinimapElementOverMapRendererHandler.class)
//#else
//$$ @Mixin(Minecraft.class)
//#endif
public class MixinMinimapElementOverMapRendererHandler {

    //#if MC > 11502
    @Inject(method = "render", at = @At(value = "RETURN"))
    private void postRender(PoseStack matrixStack, Entity renderEntity, Player player, double renderX, double renderY,
                            double renderZ, double ps, double pc, double zoom, boolean cave, float partialTicks,
                            RenderTarget framebuffer, AXaeroMinimap modMain, MinimapRendererHelper helper,
                            MultiBufferSource.BufferSource renderTypeBuffers, Font font,
                            MultiTextureRenderTypeRendererProvider multiTextureRenderTypeRenderers,
                            int specW, int specH, boolean circle, float minimapScale, CallbackInfo ci) {
        if (!Configs.minimapHighlightWaypoint || HighlightWaypointUtil.highlightPos == null) {
            return;
        }
        matrixStack.pushPose();
        matrixStack.translate(0.0D, 0.0D, -980.0D);
        double offx = (double) HighlightWaypointUtil.highlightPos.getX() + 0.5D - renderX;
        double offz = (double) HighlightWaypointUtil.highlightPos.getZ() + 0.5D - renderZ;

        matrixStack.translate(0.0D, 0.0D, 0.1D);
        RenderWaypointUtil.translatePositionCompat(matrixStack, specW, specH, ps, pc, offx, offz, zoom, circle);
        matrixStack.scale(minimapScale * 0.5f, minimapScale * 0.5f, 1.0F);
        matrixStack.translate(0.0D, 0.0D, 0.05D);

        RenderWaypointUtil.drawHighlightWaypointPTC(matrixStack.last().pose());

        matrixStack.popPose();
    }
    //#endif
}
