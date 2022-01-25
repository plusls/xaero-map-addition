package com.plusls.xma.mixin;

import com.plusls.ommc.feature.highlithtWaypoint.HighlightWaypointUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.map.gui.IRightClickableElement;
import xaero.map.gui.RightClickOption;
import xaero.map.mods.gui.Waypoint;
import xaero.map.mods.gui.WaypointMenuElement;

import java.util.ArrayList;

@Mixin(value = Waypoint.class, remap = false)
public abstract class MixinWaypoint extends WaypointMenuElement implements Comparable<Waypoint>, IRightClickableElement {

    @Shadow
    private ArrayList<RightClickOption> rightClickOptions;


    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void addHighlightOption(Object original, int x, int y, int z, String name, String symbol,
                                    int color, int type, boolean editable, String setName, CallbackInfo ci) {
        rightClickOptions.add(new RightClickOption("xma.gui.xaero_right_click_map_highlight_waypoint", rightClickOptions.size(), this) {
            public void onAction(Screen screen) {
                HighlightWaypointUtil.highlightPos = new BlockPos(x, y, z);
                HighlightWaypointUtil.lastBeamTime = System.currentTimeMillis() + 10000L;
            }
        });
    }

}
