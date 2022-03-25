package com.plusls.xma.compat.gui.screen;

import net.minecraft.client.gui.components.AbstractWidget;

public interface CompatScreen {
    <T extends AbstractWidget> T addAbstractWidget(T abstractWidget);
}
