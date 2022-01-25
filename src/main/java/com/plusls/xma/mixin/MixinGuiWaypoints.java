package com.plusls.xma.mixin;

import com.plusls.ommc.feature.highlithtWaypoint.HighlightWaypointUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.common.AXaeroMinimap;
import xaero.common.gui.GuiWaypoints;
import xaero.common.gui.IDropDownCallback;
import xaero.common.gui.MyTinyButton;
import xaero.common.gui.ScreenBase;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.WaypointWorld;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ConcurrentSkipListSet;

@Mixin(value = GuiWaypoints.class, remap = false)
public abstract class MixinGuiWaypoints extends ScreenBase implements IDropDownCallback {

    private ButtonWidget directDeleteButton;
    private ButtonWidget highlightButton;

    @Shadow
    private WaypointWorld displayedWorld;

    @Shadow
    private ConcurrentSkipListSet<Integer> selectedListSet;

    protected MixinGuiWaypoints(AXaeroMinimap modMain, Screen parent, Screen escape, Text titleIn) {
        super(modMain, parent, escape, titleIn);
    }

    @Shadow
    protected abstract void updateSortedList();

    @Shadow
    protected abstract ArrayList<Waypoint> getSelectedWaypointsList();

    @Inject(method = "init", at = @At(value = "RETURN"), remap = true)
    private void initXMAButtons(CallbackInfo ci) {
        MinecraftClient mc = MinecraftClient.getInstance();
        this.directDeleteButton = new MyTinyButton(this.width / 2 + 212, this.height - 53, I18n.translate("xma.gui.button.direct_delete"),
                buttonWidget -> Objects.requireNonNull(mc).openScreen(new ConfirmScreen(result -> {
                    if (!result) {
                        MinecraftClient.getInstance().openScreen(this);
                        return;
                    }
                    ArrayList<Waypoint> selectedWaypoints = this.getSelectedWaypointsList();

                    for (Waypoint selected : selectedWaypoints) {
                        this.displayedWorld.getCurrentSet().getList().remove(selected);
                    }

                    this.selectedListSet.clear();

                    this.updateSortedList();

                    try {
                        this.modMain.getSettings().saveWaypoints(this.displayedWorld);
                    } catch (IOException var5) {
                        var5.printStackTrace();
                    }
                    MinecraftClient.getInstance().openScreen(this);
                }, new TranslatableText("xma.gui.title.direct_delete"), new TranslatableText("xma.gui.message.direct_delete"))));

        this.addButton(this.directDeleteButton);
        this.highlightButton = new MyTinyButton(this.width / 2 - 286, this.height - 53,
                I18n.translate("xma.gui.button.highlight_waypoint"), buttonWidget -> {
            ArrayList<Waypoint> selectedWaypoints = this.getSelectedWaypointsList();
            if (selectedWaypoints.size() >= 1) {
                Waypoint w = selectedWaypoints.get(0);
                HighlightWaypointUtil.highlightPos = new BlockPos(w.getX(), w.getY(), w.getZ());
                HighlightWaypointUtil.lastBeamTime = System.currentTimeMillis() + 10000L;
            }

        });
        this.addButton(this.highlightButton);
    }

    @Inject(method = "updateButtons", at = @At(value = "HEAD"))
    private void updateXMAButtons(CallbackInfo ci) {
        this.directDeleteButton.active = this.selectedListSet.size() > 0;
        this.highlightButton.active = this.selectedListSet.size() == 1;
    }
}