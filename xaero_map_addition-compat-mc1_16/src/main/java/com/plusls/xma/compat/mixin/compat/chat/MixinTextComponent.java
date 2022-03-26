package com.plusls.xma.compat.mixin.compat.chat;

import com.plusls.xma.compat.chat.CompatComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import org.spongepowered.asm.mixin.Mixin;

@SuppressWarnings("ConstantConditions")
@Mixin(value = TextComponent.class)
public abstract class MixinTextComponent implements CompatComponent {

    @Override
    public CompatComponent appendCompat(Component component) {
        return (CompatComponent) ((TextComponent) (Object) this).append(component);
    }

    @Override
    public CompatComponent appendCompat(String string) {
        return (CompatComponent) ((TextComponent) (Object) this).append(string);
    }

    @Override
    public CompatComponent withStyleCompat(Style style) {
        return (CompatComponent) ((TextComponent) (Object) this).withStyle(style);
    }

    @Override
    public CompatComponent withStyleCompat(ChatFormatting... chatFormattings) {
        return (CompatComponent) ((TextComponent) (Object) this).withStyle(chatFormattings);
    }

    @Override
    public CompatComponent withStyleCompat(ChatFormatting chatFormatting) {
        return (CompatComponent) ((TextComponent) (Object) this).withStyle(chatFormatting);
    }

}
