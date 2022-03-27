package com.plusls.xma.compat.mixin.compat.screen;

import com.plusls.xma.compat.gui.screen.CompatScreen;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Screen.class)
public abstract class MixinScreen implements CompatScreen {

    // mojang mappings 这里显示是泛型
    // yarn: protected Element addDrawableChild(Element drawableElement)
    // 如果采用泛型会在编译时类型擦除成 Object，然后无法匹配上方法导致 shadow 失败
    @Shadow
    protected abstract AbstractWidget addButton(AbstractWidget guiEventListener);

    @Override
    public AbstractWidget addAbstractWidget(AbstractWidget abstractWidget) {
        return addButton(abstractWidget);
    }
}
