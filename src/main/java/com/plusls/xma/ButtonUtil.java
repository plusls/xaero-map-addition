package com.plusls.xma;

import com.plusls.ommc.feature.highlithtWaypoint.HighlightWaypointUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import xaero.common.AXaeroMinimap;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.WaypointWorld;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Supplier;

public class ButtonUtil {

    public static Button.OnPress getDirectDeleteButtonOnPress(
            Screen guiWaypoints, WaypointWorld displayedWorld, ConcurrentSkipListSet<Integer> selectedListSet,
            Supplier<ArrayList<Waypoint>> getSelectedWaypointsList, Runnable updateSortedList, AXaeroMinimap modMain) {
        return buttonWidget -> Objects.requireNonNull(Minecraft.getInstance()).setScreen(new ConfirmScreen(result -> {
            if (!result) {
                Minecraft.getInstance().setScreen(guiWaypoints);
                return;
            }
            for (Waypoint selected : getSelectedWaypointsList.get()) {
                displayedWorld.getCurrentSet().getList().remove(selected);
            }

            selectedListSet.clear();

            updateSortedList.run();

            try {
                modMain.getSettings().saveWaypoints(displayedWorld);
            } catch (IOException var5) {
                var5.printStackTrace();
            }
            Minecraft.getInstance().setScreen(guiWaypoints);
        }, new TranslatableComponent(ModInfo.MOD_ID + ".gui.title.direct_delete"), new TranslatableComponent(ModInfo.MOD_ID + ".gui.message.direct_delete")));
    }

    public static Button.OnPress getHighlightButtonOnPress(Supplier<ArrayList<Waypoint>> getSelectedWaypointsList) {
        return buttonWidget -> {
            if (getSelectedWaypointsList.get().size() >= 1) {
                Waypoint w = getSelectedWaypointsList.get().get(0);
                HighlightWaypointUtil.highlightPos = new BlockPos(w.getX(), w.getY(), w.getZ());
                HighlightWaypointUtil.lastBeamTime = System.currentTimeMillis() + 10000L;
            }
        };
    }
}
