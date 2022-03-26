package com.plusls.xma.compat.mixin.compat.chat;

import com.plusls.xma.compat.chat.CompatComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import org.spongepowered.asm.mixin.Mixin;

@SuppressWarnings("ConstantConditions")
@Mixin(value = TranslatableComponent.class)
public abstract class MixinTranslatableComponent implements CompatComponent {

    @Override
    public CompatComponent appendCompat(Component component) {
        return (CompatComponent) ((TranslatableComponent) (Object) this).append(component);
    }

    @Override
    public CompatComponent appendCompat(String string) {
        return (CompatComponent) ((TranslatableComponent) (Object) this).append(string);
    }

    @Override
    public CompatComponent withStyleCompat(Style style) {
        return (CompatComponent) ((TranslatableComponent) (Object) this).withStyle(style);
    }

    @Override
    public CompatComponent withStyleCompat(ChatFormatting... chatFormattings) {
        return (CompatComponent) ((TranslatableComponent) (Object) this).withStyle(chatFormattings);
    }

    @Override
    public CompatComponent withStyleCompat(ChatFormatting chatFormatting) {
        return (CompatComponent) ((TranslatableComponent) (Object) this).withStyle(chatFormatting);
    }
}