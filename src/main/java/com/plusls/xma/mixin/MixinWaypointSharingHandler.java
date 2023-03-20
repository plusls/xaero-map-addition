package com.plusls.xma.mixin;

import com.plusls.xma.ShareWaypointUtil;
import com.plusls.xma.config.Configs;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependency;
import xaero.common.minimap.waypoints.WaypointSharingHandler;

//#if MC <= 11802
//$$ import net.minecraft.network.protocol.game.ClientboundChatPacket;
//#endif

@Dependencies(or = {@Dependency("xaerominimap"), @Dependency("xaerobetterpvp")},
        and = @Dependency(value = "minecraft"))
@Mixin(value = WaypointSharingHandler.class, remap = false)
public abstract class MixinWaypointSharingHandler {

    @Shadow
    protected abstract String restoreFormatting(String s);

    @Inject(method = "onWaypointReceived", at = @At(value = "HEAD"), cancellable = true)
    private void betterOnWaypointReceived(
            //#if MC > 11802
            String playerName, String text,
            //#else
            //$$ String text, ClientboundChatPacket e,
            //#endif
            CallbackInfo ci) {
        if (!Configs.betterWaypointSharingHandler) {
            return;
        }
        text = text.replaceAll("§.", "");
        boolean newFormat = text.contains("xaero-waypoint:");
        String sharePrefix = newFormat ? "xaero-waypoint:" : "xaero_waypoint:";
        String[] args = text.substring(text.indexOf(sharePrefix)).split(":");
        if (newFormat) {
            args[1] = this.restoreFormatting(args[1]);
            args[2] = this.restoreFormatting(args[2]);
            if (args.length > 9) {
                args[9] = this.restoreFormatting(args[9]);
            }
        }
        //#if MC > 11802
        text = "<" + playerName + "> " + text;
        //#endif
        Component sendText = ShareWaypointUtil.getBetterShareText(text, args);
        if (sendText != null) {
            //#if MC > 11802
            Minecraft.getInstance().gui.getChat().addMessage(sendText);
            //#else
            //$$ ((AccessorClientboundChatPacket) e).setMessage(sendText);
            //#endif
            ci.cancel();
        }
    }
}
