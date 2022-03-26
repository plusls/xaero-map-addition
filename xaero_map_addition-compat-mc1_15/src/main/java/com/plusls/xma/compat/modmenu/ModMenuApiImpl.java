package com.plusls.xma.compat.modmenu;

import com.plusls.xma.gui.GuiConfigs;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;

public class ModMenuApiImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (screen) -> {
            GuiConfigs gui = GuiConfigs.getInstance();
            gui.setParent(screen);
            return gui;
        };
    }
}