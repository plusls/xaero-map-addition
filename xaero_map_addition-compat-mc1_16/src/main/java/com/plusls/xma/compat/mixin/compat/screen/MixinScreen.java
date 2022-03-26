package com.plusls.xma.compat.mixin.compat.screen;

import com.plusls.xma.compat.gui.screen.CompatScreen;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Screen.class)
public abstract class MixinScreen implements CompatScreen {

    @Shadow
    protected abstract <T extends AbstractWidget> T addButton(T abstractWidget);

    @Override
    public <T extends AbstractWidget> T addAbstractWidget(T abstractWidget) {
        return addButton(abstractWidget);
    }
}
