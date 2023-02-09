package com.plusls.xma.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;

//#if MC > 11502
import com.plusls.ommc.feature.highlithtWaypoint.HighlightWaypointUtil;
import com.plusls.xma.ModInfo;
import com.plusls.xma.config.Configs;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import xaero.map.gui.IRightClickableElement;
import xaero.map.gui.dropdown.rightclick.RightClickOption;
import xaero.map.mods.gui.Waypoint;
import xaero.map.mods.gui.WaypointReader;
import java.util.ArrayList;
//#endif

//#if MC > 11502
@Dependencies(and = @Dependency("xaeroworldmap"))
@Mixin(value = WaypointReader.class, remap = false)
//#else
//$$ @Mixin(value = Minecraft.class)
//#endif
public class MixinWaypointReader {

    //#if MC > 11502
    @SuppressWarnings("DuplicatedCode")
    @Inject(method = "getRightClickOptions*", at = @At(value = "RETURN"))
    private void addHighlightOption(Waypoint element, IRightClickableElement target,
                                    CallbackInfoReturnable<ArrayList<RightClickOption>> cir) {
        final int playerY;
        if (!Configs.worldMapHighlightWaypoint) {
            return;
        }
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            playerY = player.getBlockY();
        } else {
            playerY = 32767;
        }
        ArrayList<RightClickOption> options = cir.getReturnValue();
        options.add(new RightClickOption(ModInfo.MOD_ID + ".gui.xaero_right_click_map_highlight_waypoint",
                options.size(), target) {
            public void onAction(Screen screen) {
                HighlightWaypointUtil.highlightPos = new BlockPos(element.getX(),
                        element.getY() == 32767 ? playerY : element.getY() + 1,
                        element.getZ());
                HighlightWaypointUtil.lastBeamTime = System.currentTimeMillis() + 10000L;
            }
        });
    }
    //#endif
}
