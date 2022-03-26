package com.plusls.xma.compat.modmenu;

import com.plusls.xma.gui.GuiConfigs;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

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