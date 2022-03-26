package com.plusls.xma;

import com.plusls.xma.config.Configs;
import net.fabricmc.api.ClientModInitializer;
import top.hendrixshen.magiclib.config.ConfigHandler;
import top.hendrixshen.magiclib.config.ConfigManager;

public class XaeroMapAddition implements ClientModInitializer {
    private static final int CONFIG_VERSION = 1;

    @Override
    public void onInitializeClient() {
        ConfigManager cm = ConfigManager.get(ModInfo.MOD_ID);
        cm.parseConfigClass(Configs.class);
        ConfigHandler.register(new ConfigHandler(ModInfo.MOD_ID, cm, CONFIG_VERSION, null, null));
        Configs.init(cm);
    }
}
