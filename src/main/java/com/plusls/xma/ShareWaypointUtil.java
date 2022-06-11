package com.plusls.xma;

import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import org.jetbrains.annotations.Nullable;
import top.hendrixshen.magiclib.compat.minecraft.network.chat.ComponentCompatApi;
import top.hendrixshen.magiclib.compat.minecraft.network.chat.StyleCompatApi;
import xaero.common.minimap.waypoints.Waypoint;

//#if MC > 11502
import net.minecraft.network.chat.MutableComponent;
//#else
//$$ import net.minecraft.network.chat.BaseComponent;
//#endif
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
                            dimensionText = ComponentCompatApi.translatable("gui.xaero_waypoint_unknown_dimension");
                        } else {
                            String dimIdPart = dimensionName.substring(4);
                            ModInfo.LOGGER.warn("dimIdPart: {}", dimIdPart);
                            switch (dimIdPart) {
                                case "0":
                                    dimId = 0;
                                    dimensionText = ComponentCompatApi.literal("overworld");
                                    break;
                                case "1":
                                    dimId = 1;
                                    dimensionText = ComponentCompatApi.literal("the_end");
                                    break;
                                case "-1":
                                    dimId = -1;
                                    dimensionText = ComponentCompatApi.literal("the_nether");
                                    break;
                                default:
                                    dimensionText = ComponentCompatApi.translatable("gui.xaero_waypoint_unknown_dimension");
                                    break;
                            }
                        }
                    } else {
                        dimensionText = ComponentCompatApi.literal(dimensionName);
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
            } else {
                dimensionText = ComponentCompatApi.translatable("gui.xaero_waypoint_unknown_dimension");
            }

            if (dimId == 0) {
                dimensionText = ComponentCompatApi.translatable("createWorld.customize.preset.overworld").withStyle(ChatFormatting.DARK_GREEN);
            } else if (dimId == 1) {
                dimensionText = ComponentCompatApi.translatable("advancements.end.root.title").withStyle(ChatFormatting.DARK_PURPLE);
            } else if (dimId == -1) {
                dimensionText = ComponentCompatApi.translatable("advancements.nether.root.title").withStyle(ChatFormatting.DARK_RED);
            }

            StringBuilder addCommandBuilder = new StringBuilder();
            addCommandBuilder.append("xaero_waypoint_add:");
            addCommandBuilder.append(args[1]);

            for (int i = 2; i < args.length; ++i) {
                addCommandBuilder.append(':').append(args[i]);
            }

            String addCommand = addCommandBuilder.toString();
            Component addWaypointText = ComponentCompatApi.literal("[+X]")
                    .withStyle((StyleCompatApi.empty()
                            .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, addCommand))
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, ComponentCompatApi.translatable(ModInfo.MOD_ID + ".gui.message.xaero_add_waypoint")))
                            .withColor(ChatFormatting.GOLD)));


            //#if MC > 11502
            MutableComponent
            //#else
            //$$ BaseComponent
            //#endif
                    sendText = ComponentCompatApi.literal("<" + playerName + "> ");
            try {
                sendText.append(ComponentCompatApi.literal(waypointName));
                sendText.append(" @ ").append(dimensionText).append(" ");
                //#if MC > 11502
                MutableComponent
                //#else
                //$$ BaseComponent
                //#endif
                        posText = ComponentCompatApi.literal(String.format("[%d, %d, %d]", Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5])));
                ModInfo.LOGGER.warn("dimId: {}", dimId);
                if (dimId == 0) {
                    posText.withStyle(ChatFormatting.GREEN);
                    posText.append(" -> ").append(addWaypointText).append(" ")
                            .append(ComponentCompatApi.literal(String.format("[%d, %d, %d]", Integer.parseInt(args[3]) / 8, Integer.parseInt(args[4]), Integer.parseInt(args[5]) / 8)).withStyle(ChatFormatting.RED));
                } else if (dimId == -1) {
                    posText.withStyle(ChatFormatting.RED);
                    posText.append(" -> ").append(addWaypointText).append(" ")
                            .append(ComponentCompatApi.literal(String.format("[%d, %d, %d]", Integer.parseInt(args[3]) * 8, Integer.parseInt(args[4]), Integer.parseInt(args[5]) * 8)).withStyle(ChatFormatting.GREEN));
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
