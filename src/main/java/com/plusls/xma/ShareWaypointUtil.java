package com.plusls.xma;

import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.*;
import org.jetbrains.annotations.Nullable;
import top.hendrixshen.magiclib.compat.minecraft.network.chat.StyleCompatApi;
import xaero.common.minimap.waypoints.Waypoint;

public class ShareWaypointUtil {
    @Nullable
    public static Component getBetterShareText(String text, String[] args) {
        boolean newFormat = text.contains("xaero-waypoint:");
        String sharePrefix = newFormat ? "xaero-waypoint:" : "xaero_waypoint:";

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
                            switch (dimIdPart) {
                                case "0":
                                    dimId = 0;
                                    break;
                                case "1":
                                    dimId = 1;
                                    break;
                                case "-1":
                                    dimId = -1;
                                    break;
                                default:
                                    dimensionName = I18n.get("gui.xaero_waypoint_unknown_dimension");
                                    dimensionText = new TextComponent(dimensionName);
                                    break;
                            }
                        }
                    } else {
                        dimensionText = new TextComponent(dimensionName);
                        switch (dimensionName) {
                            case "overworld":
                                dimId = 0;
                                break;
                            case "the_nether":
                                dimId = -1;
                                break;
                            case "the_end":
                                dimId = 1;
                                break;
                        }
                    }
                } catch (IndexOutOfBoundsException ignored) {
                    return null;
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
                    .withStyle((StyleCompatApi.empty()
                            .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, addCommand))
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableComponent(ModInfo.MOD_ID + ".gui.message.xaero_add_waypoint")))
                            .withColor(ChatFormatting.GOLD)));


            TextComponent sendText = new TextComponent("<" + playerName + "> ");
            try {
                sendText.append(new TextComponent(waypointName));
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
                return null;
            }
            return sendText;
        }
        return null;
    }
}
