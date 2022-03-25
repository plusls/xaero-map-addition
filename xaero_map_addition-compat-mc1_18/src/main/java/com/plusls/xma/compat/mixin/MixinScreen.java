package com.plusls.xma.compat.mixin;

import com.plusls.xma.compat.gui.screen.CompatScreen;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Screen.class)
public abstract class MixinScreen implements CompatScreen {

    @Shadow
    protected abstract <T extends GuiEventListener & Widget & NarratableEntry> T addRenderableWidget(T guiEventListener);


    @Override
    public <T extends AbstractWidget> T addAbstractWidget(T abstractWidget) {
        return addRenderableWidget(abstractWidget);
    }
}
