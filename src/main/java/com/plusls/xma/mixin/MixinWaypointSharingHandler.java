package com.plusls.xma.mixin;

import com.plusls.xma.ModInfo;
import com.plusls.xma.compat.chat.CompatComponent;
import com.plusls.xma.compat.chat.ComponentCompatApi;
import com.plusls.xma.config.Configs;
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

@Dependencies(or = {@Dependency("xaerominimap"), @Dependency("xaerobetterpvp")},
        and = @Dependency(value = "minecraft"))
@Mixin(value = WaypointSharingHandler.class, remap = false)
public abstract class MixinWaypointSharingHandler {

    @Shadow
    protected abstract String restoreFormatting(String s);

    @SuppressWarnings("ConstantConditions")
    @Inject(method = "onWaypointReceived", at = @At(value = "HEAD"), cancellable = true)
    private void betterOnWaypointReceived(String text, ClientboundChatPacket e, CallbackInfo ci) {
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
                // 1.15 不存在 TranslatableComponent.<init>(Ljava/lang/String;)V
                dimensionText = ((CompatComponent) new TranslatableComponent("createWorld.customize.preset.overworld", new Object[0])).withStyleCompat(ChatFormatting.DARK_GREEN);
            } else if (dimId == 1) {
                dimensionText = ((CompatComponent) new TranslatableComponent("advancements.end.root.title", new Object[0])).withStyleCompat(ChatFormatting.DARK_PURPLE);
            } else if (dimId == -1) {
                dimensionText = ((CompatComponent) new TranslatableComponent("advancements.nether.root.title", new Object[0])).withStyleCompat(ChatFormatting.DARK_RED);
            }

            StringBuilder addCommandBuilder = new StringBuilder();
            addCommandBuilder.append("xaero_waypoint_add:");
            addCommandBuilder.append(args[1]);

            for (int i = 2; i < args.length; ++i) {
                addCommandBuilder.append(':').append(args[i]);
            }

            String addCommand = addCommandBuilder.toString();
            Component addWaypointText = ((CompatComponent) new TextComponent("[+X]"))
                    .withStyleCompat((Style) ComponentCompatApi.getInstance().getEmptyStyle()
                            .withClickEventCompat(new ClickEvent(ClickEvent.Action.RUN_COMMAND, addCommand))
                            .withHoverEventCompat(ComponentCompatApi.getInstance().newHoverEvent(ComponentCompatApi.HoverEventAction.SHOW_TEXT, new TranslatableComponent("xaero_map_addition.gui.message.xaero_add_waypoint", new Object[0])))
                            .withColorCompat(ChatFormatting.GOLD));

            CompatComponent sendText = (CompatComponent) new TextComponent("<" + playerName + "> ");
            try {
                sendText.appendCompat(new TextComponent(waypointName));
                sendText.appendCompat(" @ ").appendCompat(dimensionText).appendCompat(" ");
                CompatComponent posText = (CompatComponent) new TextComponent(String.format("[%d, %d, %d]", Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5])));
                ModInfo.LOGGER.warn("dimId: {}", dimId);
                if (dimId == 0) {
                    posText.withStyleCompat(ChatFormatting.GREEN);
                    posText.appendCompat(" -> ").appendCompat(addWaypointText).appendCompat(" ")
                            .appendCompat(((CompatComponent) new TextComponent(String.format("[%d, %d, %d]", Integer.parseInt(args[3]) / 8, Integer.parseInt(args[4]), Integer.parseInt(args[5]) / 8))).withStyleCompat(ChatFormatting.RED));
                } else if (dimId == -1) {
                    posText.withStyleCompat(ChatFormatting.RED);
                    posText.appendCompat(" -> ").appendCompat(addWaypointText).appendCompat(" ")
                            .appendCompat(((CompatComponent) new TextComponent(String.format("[%d, %d, %d]", Integer.parseInt(args[3]) * 8, Integer.parseInt(args[4]), Integer.parseInt(args[5]) * 8))).withStyleCompat(ChatFormatting.GREEN));
                } else if (dimId == 1) {
                    posText.withStyleCompat(ChatFormatting.LIGHT_PURPLE);
                    posText.appendCompat(" -> ").appendCompat(addWaypointText);
                } else {
                    posText.appendCompat(" -> ").appendCompat(addWaypointText);
                }
                sendText.appendCompat(posText);
            } catch (NumberFormatException ignored) {
                return;
            }
            ((AccessorClientboundChatPacket) e).setMessage(sendText);
            ci.cancel();
        }

    }
}
