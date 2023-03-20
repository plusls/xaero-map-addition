package com.plusls.xma.gui;

import com.plusls.xma.ModInfo;
import com.plusls.xma.config.Configs;
import lombok.Getter;
import net.minecraft.client.resources.language.I18n;
import top.hendrixshen.magiclib.malilib.impl.ConfigManager;
import top.hendrixshen.magiclib.malilib.impl.gui.ConfigGui;

public class GuiConfigs extends ConfigGui {
    @Getter
    private static final GuiConfigs instance = new GuiConfigs(ModInfo.getModIdentifier(), Configs.ConfigCategory.GENERIC, ConfigManager.get(ModInfo.getModIdentifier()));;

    private GuiConfigs(String identifier, String defaultTab, ConfigManager configManager) {
        super(identifier, defaultTab, configManager, () -> I18n.get(String.format("%s.gui.title.configs", ModInfo.getModIdentifier()), ModInfo.getModIdentifier()));
    }
}