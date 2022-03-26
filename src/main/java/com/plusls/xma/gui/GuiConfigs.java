package com.plusls.xma.gui;

import com.plusls.xma.ModInfo;
import com.plusls.xma.config.Configs;
import net.minecraft.client.resources.language.I18n;
import top.hendrixshen.magiclib.config.ConfigManager;
import top.hendrixshen.magiclib.gui.ConfigGui;

public class GuiConfigs extends ConfigGui {

    private static GuiConfigs INSTANCE;

    private GuiConfigs(String identifier, String defaultTab, ConfigManager configManager) {
        super(identifier, defaultTab, configManager, () -> I18n.get(String.format("%s.gui.title.configs", ModInfo.MOD_ID), ModInfo.MOD_VERSION));
    }


    public static GuiConfigs getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GuiConfigs(ModInfo.MOD_ID, Configs.ConfigCategory.GENERIC, ConfigManager.get(ModInfo.MOD_ID));
        }
        return INSTANCE;
    }
}