package com.plusls.xma.mixin;

import com.plusls.xma.ModInfo;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.*;
import net.minecraft.network.protocol.game.ClientboundChatPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.WaypointSharingHandler;
import xaero.common.settings.ModSettings;

// 1.15 TODO
@Dependencies(or = {@Dependency("xaerominimap"), @Dependency("xaerobetterpvp")},
        and = @Dependency(value = "minecraft", versionPredicate = ">=1.16.5"))
@Mixin(value = WaypointSharingHandler.class, remap = false)
public abstract class MixinWaypointSharingHandler {

    @Shadow
    protected abstract String restoreFormatting(String s);

    @Inject(method = "onWaypointReceived", at = @At(value = "HEAD"), cancellable = true)
    private void betterOnWaypointReceived(String text, ClientboundChatPacket e, CallbackInfo ci) {
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

            String waypointName = I18n.get(Waypoint.getStringFromStringSafe(args[1], "^col^"));
            Component dimensionText = null;
            int dimId = 114514;
            if (args.length > 9 && args[9].startsWith("Internal_")) {
                try {
                    String dimensionName = args[9].substring(9, args[9].lastIndexOf("_")).replace("^col^", ":");
                    if (dimensionName.startsWith("dim%")) {
                        if (dimensionName.length() == 4) {
                            dimensionName = I18n.get("gui.xaero_waypoint_unknown_dimension");
                            dimensionText = new TextComponent(dimensionName);
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
                                dimensionName = I18n.get("gui.xaero_waypoint_unknown_dimension");
                                dimensionText = new TextComponent(dimensionName);
                            }
                        }
                    } else {
                        dimensionText = new TextComponent(dimensionName);
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
                dimensionText = new TranslatableComponent("createWorld.customize.preset.overworld").withStyle(ChatFormatting.DARK_GREEN);
            } else if (dimId == 1) {
                dimensionText = new TranslatableComponent("advancements.end.root.title").withStyle(ChatFormatting.DARK_PURPLE);
            } else if (dimId == -1) {
                dimensionText = new TranslatableComponent("advancements.nether.root.title").withStyle(ChatFormatting.DARK_RED);
            }

            StringBuilder addCommandBuilder = new StringBuilder();
            addCommandBuilder.append("xaero_waypoint_add:");
            addCommandBuilder.append(args[1]);

            for (int i = 2; i < args.length; ++i) {
                addCommandBuilder.append(':').append(args[i]);
            }

            String addCommand = addCommandBuilder.toString();
            Component addWaypointText = new TextComponent("[+X]")
                    .setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, addCommand))
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableComponent("xma.gui.message.xaero_add_waypoint")))
                            .withColor(TextColor.fromLegacyFormat(ChatFormatting.GOLD)));

            TextComponent sendText = new TextComponent("<" + playerName + "> ");
            try {
                sendText.append(new TextComponent(waypointName).setStyle(Style.EMPTY.withColor(ModSettings.COLORS[Integer.parseInt(args[6])])));
                sendText.append(" @ ").append(dimensionText).append(" ");
                TextComponent posText = new TextComponent(String.format("[%d, %d, %d]", Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5])));
                ModInfo.LOGGER.warn("dimId: {}", dimId);
                if (dimId == 0) {
                    posText.withStyle(ChatFormatting.GREEN);
                    posText.append(" -> ").append(addWaypointText).append(" ")
                            .append(new TextComponent(String.format("[%d, %d, %d]", Integer.parseInt(args[3]) / 8, Integer.parseInt(args[4]), Integer.parseInt(args[5]) / 8)).withStyle(ChatFormatting.RED));
                } else if (dimId == -1) {
                    posText.withStyle(ChatFormatting.RED);
                    posText.append(" -> ").append(addWaypointText).append(" ")
                            .append(new TextComponent(String.format("[%d, %d, %d]", Integer.parseInt(args[3]) * 8, Integer.parseInt(args[4]), Integer.parseInt(args[5]) * 8)).withStyle(ChatFormatting.GREEN));
                } else if (dimId == 1) {
                    posText.withStyle(ChatFormatting.LIGHT_PURPLE);
                    posText.append(" -> ").append(addWaypointText);
                } else {
                    posText.append(" -> ").append(addWaypointText);
                }
                sendText.append(posText);
            } catch (NumberFormatException ignored) {
                return;
            }
            ((AccessorClientboundChatPacket) e).setMessage(sendText);
            ci.cancel();
        }

    }
}
