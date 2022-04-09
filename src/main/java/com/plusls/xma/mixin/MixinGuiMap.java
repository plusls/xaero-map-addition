package com.plusls.xma.mixin;

import com.plusls.ommc.feature.highlithtWaypoint.HighlightWaypointUtil;
import com.plusls.xma.config.Configs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import xaero.map.gui.GuiMap;
import xaero.map.gui.IRightClickableElement;
import xaero.map.gui.RightClickOption;
import xaero.map.gui.ScreenBase;

import java.util.ArrayList;

@Dependencies(and = @Dependency("xaeroworldmap"))
@Mixin(value = GuiMap.class, remap = false)
public abstract class MixinGuiMap extends ScreenBase implements IRightClickableElement {

    @Shadow
    private int rightClickX;
    @Shadow
    private int rightClickY;
    @Shadow
    private int rightClickZ;

    protected MixinGuiMap(Screen parent, Screen escape, Component titleIn) {
        super(parent, escape, titleIn);
    }

    @Inject(method = "getRightClickOptions", at = @At(value = "RETURN"))
    private void addHighlightOption(CallbackInfoReturnable<ArrayList<RightClickOption>> cir) {
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
        options.add(new RightClickOption("xaero_map_addition.gui.xaero_right_click_map_highlight_location", options.size(), this) {
            public void onAction(Screen screen) {
                HighlightWaypointUtil.highlightPos = new BlockPos(MixinGuiMap.this.rightClickX,
                        MixinGuiMap.this.rightClickY == 32767 ? playerY : MixinGuiMap.this.rightClickY + 1,
                        MixinGuiMap.this.rightClickZ);
                HighlightWaypointUtil.lastBeamTime = System.currentTimeMillis() + 10000L;
            }
        });
    }
}
