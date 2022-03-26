package com.plusls.xma.compat.mixin.compat.chat;

import com.plusls.xma.compat.chat.CompatComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = TranslatableComponent.class)
public abstract class MixinTranslatableComponent implements CompatComponent {

    @Override
    public CompatComponent appendCompat(Component component) {
        return (CompatComponent) this.append(component);
    }

    @Override
    public CompatComponent appendCompat(String string) {
        return (CompatComponent) this.append(string);
    }

    @Override
    public CompatComponent withStyleCompat(Style style) {
        this.setStyle(style);
        return this;
    }

    @Override
    public CompatComponent withStyleCompat(ChatFormatting... chatFormattings) {
        return (CompatComponent) this.withStyle(chatFormattings);
    }

    @Override
    public CompatComponent withStyleCompat(ChatFormatting chatFormatting) {
        return (CompatComponent) this.withStyle(chatFormatting);
    }
}