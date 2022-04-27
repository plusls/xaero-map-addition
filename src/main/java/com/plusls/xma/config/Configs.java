package com.plusls.xma.config;

import com.plusls.xma.ModInfo;
import com.plusls.xma.gui.GuiConfigs;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import top.hendrixshen.magiclib.config.ConfigManager;
import top.hendrixshen.magiclib.config.annotation.Config;
import top.hendrixshen.magiclib.config.annotation.Hotkey;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;

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

    public static void init(ConfigManager cm) {
        openConfigGui.getKeybind().setCallback((keyAction, iKeybind) -> {
            GuiConfigs screen = GuiConfigs.getInstance();
            screen.setParentGui(Minecraft.getInstance().screen);
            Minecraft.getInstance().setScreen(screen);
            return true;
        });

        cm.setValueChangeCallback("debug", option -> {
            if (debug) {
                Configurator.setLevel(ModInfo.MOD_ID, Level.toLevel("DEBUG"));
            } else {
                Configurator.setLevel(ModInfo.MOD_ID, Level.toLevel("INFO"));
            }
            GuiConfigs.getInstance().reDraw();
        });
        if (debug) {
            Configurator.setLevel(ModInfo.MOD_ID, Level.toLevel("DEBUG"));
        }
    }

    public static class ConfigCategory {
        public static final String GENERIC = "generic";
        public static final String XAERO_MINIMAP = "xaerominimap";
        public static final String XAERO_WORLD_MAP = "xaeroworldmap";
    }
}
