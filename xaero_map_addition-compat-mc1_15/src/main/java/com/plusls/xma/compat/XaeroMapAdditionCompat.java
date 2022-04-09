package com.plusls.xma.compat;

import net.fabricmc.api.ClientModInitializer;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;

public class XaeroMapAdditionCompat implements ClientModInitializer {

    @Dependencies(and = {
            @Dependency(value = "xaerominimap", versionPredicate = ">=21.10.0.4", optional = true),
            @Dependency(value = "xaerobetterpvp", versionPredicate = ">=21.10.0.4", optional = true),
            @Dependency(value = "xaeroworldmap", versionPredicate = ">=1.14.1", optional = true)
    })
    @Override
    public void onInitializeClient() {
        RenderWaypointCompatImpl.init();
    }
}
