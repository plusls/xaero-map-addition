package com.plusls.xma.mixin;

import com.plusls.ommc.feature.highlithtWaypoint.HighlightWaypointUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xaero.map.WorldMap;
import xaero.map.gui.GuiMap;
import xaero.map.gui.IRightClickableElement;
import xaero.map.gui.RightClickOption;
import xaero.map.gui.ScreenBase;
import xaero.map.mods.SupportMods;

import java.util.ArrayList;

@Mixin(value = GuiMap.class, remap = false)
public abstract class MixinGuiMap extends ScreenBase implements IRightClickableElement {

    @Shadow
    private int rightClickX;
    @Shadow
    private int rightClickY;
    @Shadow
    private int rightClickZ;

    protected MixinGuiMap(Screen parent, Screen escape, Text titleIn) {
        super(parent, escape, titleIn);
    }

    @Inject(method = "getRightClickOptions", at = @At(value = "RETURN"))
    private void addHighlightOption(CallbackInfoReturnable<ArrayList<RightClickOption>> cir) {
        final int playerY;
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            playerY = player.getBlockY();
        } else {
            playerY = 32767;
        }
        ArrayList<RightClickOption> options = cir.getReturnValue();
        options.add(new RightClickOption("xma.gui.xaero_right_click_map_highlight_location", options.size(), this) {
            public void onAction(Screen screen) {
                if (SupportMods.minimap()) {
                    if (WorldMap.settings.waypoints) {
                        HighlightWaypointUtil.highlightPos = new BlockPos(MixinGuiMap.this.rightClickX,
                                MixinGuiMap.this.rightClickY == 32767 ? playerY : MixinGuiMap.this.rightClickY + 1,
                                MixinGuiMap.this.rightClickZ);
                        HighlightWaypointUtil.lastBeamTime = System.currentTimeMillis() + 10000L;
                    }
                }
            }
        });
    }
}
