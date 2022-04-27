package com.plusls.xma.mixin;

import com.plusls.xma.ButtonUtil;
import com.plusls.xma.ModInfo;
import com.plusls.xma.config.Configs;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
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

import java.util.ArrayList;
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
        if (Configs.directDeleteButton) {
            this.directDeleteButton = new MyTinyButton(this.width / 2 + 212, this.height - 53,
                    //#if MC > 11502
                    new TextComponent(ModInfo.translate("gui.button.direct_delete")),
                    //#else
                    //$$ ModInfo.translate("gui.button.direct_delete"),
                    //#endif
                    ButtonUtil.getDirectDeleteButtonOnPress(this,
                            this.displayedWorld, this.selectedListSet, this::getSelectedWaypointsList,
                            this::updateSortedList, this.modMain
                    ));
            this.addRenderableWidget(this.directDeleteButton);
        }

        if (Configs.minimapHighlightWaypoint) {
            this.highlightButton = new MyTinyButton(this.width / 2 - 286, this.height - 53,
                    //#if MC > 11502
                    new TextComponent(ModInfo.translate("gui.button.highlight_waypoint")),
                    //#else
                    //$$ ModInfo.translate("gui.button.highlight_waypoint"),
                    //#endif
                    ButtonUtil.getHighlightButtonOnPress(this::getSelectedWaypointsList));
            this.addRenderableWidget(this.highlightButton);
        }
    }

    @Inject(method = "updateButtons", at = @At(value = "HEAD"))
    private void updateXMAButtons(CallbackInfo ci) {
        if (Configs.directDeleteButton) {
            this.directDeleteButton.active = this.selectedListSet.size() > 0;
        }
        if (Configs.minimapHighlightWaypoint) {
            this.highlightButton.active = this.selectedListSet.size() == 1;
        }
    }
}