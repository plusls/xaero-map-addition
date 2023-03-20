package com.plusls.xma.config;

import com.plusls.xma.ModInfo;
import com.plusls.xma.gui.GuiConfigs;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.jetbrains.annotations.NotNull;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependency;
import top.hendrixshen.magiclib.malilib.api.annotation.Config;
import top.hendrixshen.magiclib.malilib.api.annotation.Hotkey;
import top.hendrixshen.magiclib.malilib.impl.ConfigManager;

public class Configs {
    @Config(category = ConfigCategory.GENERIC)
    public static boolean debug = false;

    @Hotkey(hotkey = "X,A")
    @Config(category = ConfigCategory.GENERIC)
    public static ConfigHotkey openConfigGui;

    @Config(category = ConfigCategory.XAERO_MINIMAP, dependencies = @Dependencies(or = {
            @Dependency("xaerominimap"), @Dependency("xaerobetterpvp")}))
    public static boolean betterWaypointSharingHandler = true;

    @Config(category = ConfigCategory.XAERO_MINIMAP, dependencies = @Dependencies(or = {
            @Dependency("xaerominimap"), @Dependency("xaerobetterpvp")}))
    public static boolean directDeleteButton = true;

    @Config(category = ConfigCategory.XAERO_MINIMAP, dependencies = @Dependencies(or = {
            @Dependency("xaerominimap"), @Dependency("xaerobetterpvp")}))
    public static boolean minimapHighlightWaypoint = true;


    @Config(category = ConfigCategory.XAERO_WORLD_MAP, dependencies = @Dependencies(and = @Dependency("xaeroworldmap")))
    public static boolean worldMapHighlightWaypoint = true;

    public static void init(@NotNull ConfigManager cm) {
        openConfigGui.getKeybind().setCallback((keyAction, iKeybind) -> {
            GuiConfigs screen = GuiConfigs.getInstance();
            screen.setParentGui(Minecraft.getInstance().screen);
            Minecraft.getInstance().setScreen(screen);
            return true;
        });

        cm.setValueChangeCallback("debug", option -> {
            Configurator.setLevel(ModInfo.getModIdentifier(), debug ? Level.DEBUG : Level.INFO);
            GuiConfigs.getInstance().reDraw();
        });
        if (debug) {
            Configurator.setLevel(ModInfo.getModIdentifier(), Level.toLevel("DEBUG"));
        }
    }

    public static class ConfigCategory {
        public static final String GENERIC = "generic";
        public static final String XAERO_MINIMAP = "xaerominimap";
        public static final String XAERO_WORLD_MAP = "xaeroworldmap";
    }
}
