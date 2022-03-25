package com.plusls.xma.compat.mixin;

import com.plusls.xma.compat.gui.screen.CompatScreen;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Screen.class)
public abstract class MixinScreen implements CompatScreen {

    // 不知道为什么，如果不加前缀会自动帮我mapping，然而这里不存在mapping
    @Shadow
    protected abstract <T extends AbstractWidget> T shadow$addButton(T abstractWidget);

    @Override
    public <T extends AbstractWidget> T addAbstractWidget(T abstractWidget) {
        return shadow$addButton(abstractWidget);
    }
}
