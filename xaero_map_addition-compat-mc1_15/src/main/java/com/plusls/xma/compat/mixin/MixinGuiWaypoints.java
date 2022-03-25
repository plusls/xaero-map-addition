package com.plusls.xma.compat.mixin;

import com.plusls.ommc.feature.highlithtWaypoint.HighlightWaypointUtil;
import com.plusls.xma.compat.gui.screen.CompatScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
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

@Dependencies(or = {@Dependency("xaerominimap"), @Dependency("xaerobetterpvp")})
@Mixin(value = GuiWaypoints.class, remap = false)
public abstract class MixinGuiWaypoints extends ScreenBase implements IDropDownCallback {

    private Button directDeleteButton;
    private Button highlightButton;

    @Shadow
    private WaypointWorld displayedWorld;

    @Shadow
    private ConcurrentSkipListSet<Integer> selectedListSet;

    protected MixinGuiWaypoints(AXaeroMinimap modMain, Screen parent, Screen escape, Component titleIn) {
        super(modMain, parent, escape, titleIn);
    }

    @Shadow
    protected abstract void updateSortedList();

    @Shadow
    protected abstract ArrayList<Waypoint> getSelectedWaypointsList();

    @Inject(method = "init", at = @At(value = "RETURN"), remap = true)
    private void initXMAButtons(CallbackInfo ci) {
        this.directDeleteButton = new MyTinyButton(this.width / 2 + 212, this.height - 53, I18n.get("xma.gui.button.direct_delete"),
                buttonWidget -> Objects.requireNonNull(this.minecraft).setScreen(new ConfirmScreen(result -> {
                    if (!result) {
                        Minecraft.getInstance().setScreen(this);
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
                    Minecraft.getInstance().setScreen(this);
                }, new TranslatableComponent("xma.gui.title.direct_delete"), new TranslatableComponent("xma.gui.message.direct_delete"))));
        ((CompatScreen) this).addAbstractWidget(this.directDeleteButton);
        this.highlightButton = new MyTinyButton(this.width / 2 - 286, this.height - 53,
                I18n.get("xma.gui.button.highlight_waypoint"), buttonWidget -> {
            ArrayList<Waypoint> selectedWaypoints = this.getSelectedWaypointsList();
            if (selectedWaypoints.size() >= 1) {
                Waypoint w = selectedWaypoints.get(0);
                HighlightWaypointUtil.highlightPos = new BlockPos(w.getX(), w.getY(), w.getZ());
                HighlightWaypointUtil.lastBeamTime = System.currentTimeMillis() + 10000L;
            }

        });
        ((CompatScreen) this).addAbstractWidget(this.highlightButton);
    }

    @Inject(method = "updateButtons", at = @At(value = "HEAD"))
    private void updateXMAButtons(CallbackInfo ci) {
        this.directDeleteButton.active = this.selectedListSet.size() > 0;
        this.highlightButton.active = this.selectedListSet.size() == 1;
    }
}