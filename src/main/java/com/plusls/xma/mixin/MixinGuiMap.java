package com.plusls.xma.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.plusls.ommc.feature.highlithtWaypoint.HighlightWaypointUtil;
import com.plusls.xma.ModInfo;
import com.plusls.xma.RenderWaypointUtil;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import xaero.map.WorldMap;
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

    @Shadow
    private double cameraX;

    @Shadow
    private double cameraZ;

    @Shadow
    private double scale;

    @Shadow
    private double screenScale;

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
        options.add(new RightClickOption(ModInfo.MOD_ID + ".gui.xaero_right_click_map_highlight_location",
                options.size(), this) {
            public void onAction(Screen screen) {
                HighlightWaypointUtil.highlightPos = new BlockPos(MixinGuiMap.this.rightClickX,
                        MixinGuiMap.this.rightClickY == 32767 ? playerY : MixinGuiMap.this.rightClickY + 1,
                        MixinGuiMap.this.rightClickZ);
                HighlightWaypointUtil.lastBeamTime = System.currentTimeMillis() + 10000L;
            }
        });
    }

    @Inject(method = "render", at = @At(value = "RETURN"), remap = true)
    private void renderHighlightWaypoint(
            //#if MC > 11502
            PoseStack matrixStack,
            //#endif
            int scaledMouseX, int scaledMouseY, float partialTicks, CallbackInfo ci) {
        if (!Configs.worldMapHighlightWaypoint || HighlightWaypointUtil.highlightPos == null) {
            return;
        }
        //#if MC <= 11502
        //$$ PoseStack matrixStack = new PoseStack();
        //#endif

        Minecraft mc = Minecraft.getInstance();

        matrixStack.pushPose();

        matrixStack.scale((float) (1.0 / this.screenScale), (float) (1.0 / this.screenScale), 1.0F);
        matrixStack.translate((double) mc.getWindow().getWidth() / 2, (double) mc.getWindow().getHeight() / 2, 0.0);
        matrixStack.scale((float) this.scale, (float) this.scale, 1.0F);

        matrixStack.translate(HighlightWaypointUtil.highlightPos.getX() - this.cameraX,
                HighlightWaypointUtil.highlightPos.getZ() - this.cameraZ, 0);

        double minGuiScale = 4.0D;
        float guiBasedScale = 1.0F;
        if (this.screenScale > minGuiScale) {
            guiBasedScale = (float) (this.screenScale / minGuiScale);
        }

        double wpScale = guiBasedScale * (double) WorldMap.settings.worldmapWaypointsScale / this.scale * 2;
        matrixStack.scale((float) wpScale, (float) wpScale, 1.0F);
        RenderWaypointUtil.drawHighlightWaypointPTC(matrixStack.last().pose());
        matrixStack.popPose();
    }
}
