package com.plusls.xma.mixin;

import org.spongepowered.asm.mixin.Mixin;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import xaero.map.mods.gui.Waypoint;

//#if MC <= 11502
//$$ import com.plusls.ommc.feature.highlithtWaypoint.HighlightWaypointUtil;
//$$ import com.plusls.xma.config.Configs;
//$$ import net.minecraft.client.gui.screens.Screen;
//$$ import net.minecraft.core.BlockPos;
//$$ import org.spongepowered.asm.mixin.Shadow;
//$$ import org.spongepowered.asm.mixin.injection.At;
//$$ import org.spongepowered.asm.mixin.injection.Inject;
//$$ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//$$ import xaero.map.gui.IRightClickableElement;
//$$ import xaero.map.gui.RightClickOption;
//$$ import java.util.ArrayList;
//#endif

@Dependencies(and = @Dependency("xaeroworldmap"))
@Mixin(value = Waypoint.class, remap = false)
public abstract class MixinWaypoint {

    //#if MC <= 11502
    //$$ @Shadow
    //$$ private ArrayList<RightClickOption> rightClickOptions;

    //$$ @SuppressWarnings("DuplicatedCode")
    //$$ @Inject(method = "<init>", at = @At(value = "RETURN"))
    //$$ private void addHighlightOption(
    //$$         Object original, int x, int y, int z, String name, String symbol, int color, int type,
    //$$         boolean editable, String setName,
    //$$         CallbackInfo ci) {
    //$$     if (!Configs.worldMapHighlightWaypoint) {
    //$$         return;
    //$$     }
    //$$     rightClickOptions.add(new RightClickOption("xaero_map_addition.gui.xaero_right_click_map_highlight_waypoint", rightClickOptions.size(), (IRightClickableElement) this) {
    //$$         public void onAction(Screen screen) {
    //$$             HighlightWaypointUtil.highlightPos = new BlockPos(x, y, z);
    //$$             HighlightWaypointUtil.lastBeamTime = System.currentTimeMillis() + 10000L;
    //$$         }
    //$$     });
    //$$ }
    //#endif
}
