package com.plusls.xma.mixin;

import com.plusls.xma.ModInfo;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.common.core.IXaeroMinimapGameMessageS2CPacket;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.WaypointSharingHandler;
import xaero.common.settings.ModSettings;

@Mixin(value = WaypointSharingHandler.class, remap = false)
public abstract class MixinWaypointSharingHandler {

    @Shadow
    protected abstract String restoreFormatting(String s);

    @Inject(method = "onWaypointReceived", at = @At(value = "HEAD"), cancellable = true)
    private void betterOnWaypointReceived(String text, GameMessageS2CPacket e, CallbackInfo ci) {
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

        if (args.length < 9) {
            System.out.println("Incorrect format of the shared waypoint! Error: 0");
        } else {
            String playerName = text.substring(0, text.indexOf(sharePrefix));
            int lastGreater = playerName.lastIndexOf(">");
            if (lastGreater != -1) {
                playerName = playerName.substring(0, lastGreater).replaceFirst("<", "");
            }

            String waypointName = I18n.translate(Waypoint.getStringFromStringSafe(args[1], "^col^"));
            Text dimensionText = null;
            int dimId = 114514;
            if (args.length > 9 && args[9].startsWith("Internal_")) {
                try {
                    String dimensionName = args[9].substring(9, args[9].lastIndexOf("_")).replace("^col^", ":");
                    if (dimensionName.startsWith("dim%")) {
                        if (dimensionName.length() == 4) {
                            dimensionName = I18n.translate("gui.xaero_waypoint_unknown_dimension");
                            dimensionText = new LiteralText(dimensionName);
                        } else {
                            String dimIdPart = dimensionName.substring(4);
                            ModInfo.LOGGER.warn("dimIdPart: {}", dimIdPart);
                            if (dimIdPart.equals("0")) {
                                dimId = 0;
                            } else if (dimIdPart.equals("1")) {
                                dimId = 1;
                            } else if (dimIdPart.equals("-1")) {
                                dimId = -1;
                            } else {
                                dimensionName = I18n.translate("gui.xaero_waypoint_unknown_dimension");
                                dimensionText = new LiteralText(dimensionName);
                            }
                        }
                    } else {
                        dimensionText = new LiteralText(dimensionName);
                        if (dimensionName.equals("overworld")) {
                            dimId = 0;
                        } else if (dimensionName.equals("the_nether")) {
                            dimId = -1;
                        } else if (dimensionName.equals("the_end")) {
                            dimId = 1;
                        }
                    }
                } catch (IndexOutOfBoundsException ignored) {
                    return;
                }
            }
            if (dimId == 0) {
                dimensionText = new TranslatableText("createWorld.customize.preset.overworld").formatted(Formatting.DARK_GREEN);
            } else if (dimId == 1) {
                dimensionText = new TranslatableText("advancements.end.root.title").formatted(Formatting.DARK_PURPLE);
            } else if (dimId == -1) {
                dimensionText = new TranslatableText("advancements.nether.root.title").formatted(Formatting.DARK_RED);
            }


            StringBuilder addCommandBuilder = new StringBuilder();
            addCommandBuilder.append("xaero_waypoint_add:");
            addCommandBuilder.append(args[1]);

            for (int i = 2; i < args.length; ++i) {
                addCommandBuilder.append(':').append(args[i]);
            }

            String addCommand = addCommandBuilder.toString();
            Text addWaypointText = new LiteralText("[+X]")
                    .setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, addCommand))
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableText("xma.gui.message.xaero_add_waypoint")))
                            .withColor(TextColor.fromFormatting(Formatting.GOLD)));

            LiteralText sendText = new LiteralText("<" + playerName + "> ");
            try {
                sendText.append(new LiteralText(waypointName).setStyle(Style.EMPTY.withColor(ModSettings.COLORS[Integer.parseInt(args[6])])));
                sendText.append(" @ ").append(dimensionText).append(" ");
                LiteralText posText = new LiteralText(String.format("[%d, %d, %d]", Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5])));
                ModInfo.LOGGER.warn("dimId: {}", dimId);
                if (dimId == 0) {
                    posText.formatted(Formatting.GREEN);
                    posText.append(" -> ").append(addWaypointText).append(" ")
                            .append(new LiteralText(String.format("[%d, %d, %d]", Integer.parseInt(args[3]) / 8, Integer.parseInt(args[4]), Integer.parseInt(args[5]) / 8)).formatted(Formatting.RED));
                } else if (dimId == -1) {
                    posText.formatted(Formatting.RED);
                    posText.append(" -> ").append(addWaypointText).append(" ")
                            .append(new LiteralText(String.format("[%d, %d, %d]", Integer.parseInt(args[3]) * 8, Integer.parseInt(args[4]), Integer.parseInt(args[5]) * 8)).formatted(Formatting.GREEN));
                } else if (dimId == 1) {
                    posText.formatted(Formatting.LIGHT_PURPLE);
                    posText.append(" -> ").append(addWaypointText);
                } else {
                    posText.append(" -> ").append(addWaypointText);
                }
                sendText.append(posText);
            } catch (NumberFormatException ignored) {
                return;
            }
            ((IXaeroMinimapGameMessageS2CPacket) e).setMessage(sendText);
            ci.cancel();
        }

    }
}
