package com.plusls.xma.compat;

import net.fabricmc.api.ClientModInitializer;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;

public class XaeroMapAdditionCompat implements ClientModInitializer {

    @Dependencies(and = {
            @Dependency(value = "xaerominimap", versionPredicate = ">=22.3.1.1", optional = true),
            @Dependency(value = "xaerobetterpvp", versionPredicate = ">=22.3.1.1", optional = true),
            @Dependency(value = "xaeroworldmap", versionPredicate = ">=1.20.4.1", optional = true)
    })
    @Override
    public void onInitializeClient() {
        RenderWaypointCompatUtilImpl.init();
    }
}
