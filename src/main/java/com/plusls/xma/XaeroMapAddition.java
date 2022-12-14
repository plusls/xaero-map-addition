package com.plusls.xma;

import com.plusls.xma.config.Configs;
import net.fabricmc.api.ClientModInitializer;
import top.hendrixshen.magiclib.config.ConfigHandler;
import top.hendrixshen.magiclib.config.ConfigManager;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;

public class XaeroMapAddition implements ClientModInitializer {
    private static final int CONFIG_VERSION = 1;

    @Dependencies(and = {
            //#if MC >= 11903
            @Dependency(value = "xaerominimap", versionPredicate = ">=22.16.4", optional = true),
            @Dependency(value = "xaerobetterpvp", versionPredicate = ">=22.16.4", optional = true),
            @Dependency(value = "xaeroworldmap", versionPredicate = ">=1.28.5", optional = true)
            //#elseif MC > 11502
            //$$ @Dependency(value = "xaerominimap", versionPredicate = ">=22.16.3", optional = true),
            //$$ @Dependency(value = "xaerobetterpvp", versionPredicate = ">=22.16.3", optional = true),
            //$$ @Dependency(value = "xaeroworldmap", versionPredicate = ">=1.28.4", optional = true)
            //#elseif MC > 11404
            //$$ @Dependency(value = "xaerominimap", versionPredicate = ">=21.10.0.4", optional = true),
            //$$ @Dependency(value = "xaerobetterpvp", versionPredicate = ">=21.10.0.4", optional = true),
            //$$ @Dependency(value = "xaeroworldmap", versionPredicate = ">=1.14.1", optional = true)
            //#endif
    })
    @Override
    public void onInitializeClient() {
        ConfigManager cm = ConfigManager.get(ModInfo.MOD_ID);
        cm.parseConfigClass(Configs.class);
        ConfigHandler.register(new ConfigHandler(ModInfo.MOD_ID, cm, CONFIG_VERSION, null, null));
        Configs.init(cm);
    }
}
