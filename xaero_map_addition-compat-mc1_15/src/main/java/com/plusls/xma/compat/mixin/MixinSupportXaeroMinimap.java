package com.plusls.xma.compat.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Transformation;
import com.plusls.ommc.feature.highlithtWaypoint.HighlightWaypointUtil;
import com.plusls.xma.RenderWaypointUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import xaero.map.WorldMap;
import xaero.map.mods.SupportXaeroMinimap;
import xaero.map.mods.gui.Waypoint;

import java.util.regex.Pattern;

@Dependencies(and = @Dependency("xaeroworldmap"))
@Mixin(value = SupportXaeroMinimap.class, remap = false)
public class MixinSupportXaeroMinimap {
    @Inject(method = "renderWaypoints", at = @At("RETURN"))
    private void drawHighlightWaypoint(Screen gui, double cameraX, double cameraZ, int width, int height,
                                       double guiBasedScale, double scale, double mouseX, double mouseZ,
                                       Pattern regex, Pattern regexStartsWith, float brightness, Waypoint oldViewed,
                                       Minecraft mc, CallbackInfoReturnable<Waypoint> cir) {
        if (HighlightWaypointUtil.highlightPos == null) {
            return;
        }
        RenderSystem.pushMatrix();
        RenderSystem.translated(HighlightWaypointUtil.highlightPos.getX() - cameraX,
                HighlightWaypointUtil.highlightPos.getZ() - cameraZ, 0);
        double wpScale = guiBasedScale * (double) WorldMap.settings.worldmapWaypointsScale / scale * 2.0f;
        RenderSystem.scalef((float) wpScale, (float) wpScale, 1.0F);
        RenderWaypointUtil.drawHighlightWaypointPTC(Transformation.identity().getMatrix());
        RenderSystem.popMatrix();

    }
}
