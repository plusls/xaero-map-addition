package com.plusls.xma.compat.mixin.compat.chat;

import com.plusls.xma.compat.chat.CompatStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@SuppressWarnings("ConstantConditions")
@Mixin(Style.class)
public class MixinStyle implements CompatStyle {

    @Override
    public CompatStyle withColorCompat(@Nullable ChatFormatting chatFormatting) {
        return (CompatStyle) ((Style) (Object) this).withColor(chatFormatting);
    }

    @Override
    public CompatStyle withBoldCompat(@Nullable Boolean boolean_) {
        return (CompatStyle) ((Style) (Object) this).withBold(boolean_);
    }

    @Override
    public CompatStyle withItalicCompat(@Nullable Boolean boolean_) {
        return (CompatStyle) ((Style) (Object) this).withItalic(boolean_);
    }

    @Override
    public CompatStyle withUnderlinedCompat(@Nullable Boolean boolean_) {
        return (CompatStyle) ((Style) (Object) this).withUnderlined(boolean_);
    }

    @Override
    public CompatStyle withStrikethroughCompat(@Nullable Boolean boolean_) {
        return (CompatStyle) ((Style) (Object) this).withUnderlined(boolean_);
    }

    @Override
    public CompatStyle withObfuscatedCompat(@Nullable Boolean boolean_) {
        return (CompatStyle) ((Style) (Object) this).withObfuscated(boolean_);
    }

    @Override
    public CompatStyle withClickEventCompat(@Nullable ClickEvent clickEvent) {
        return (CompatStyle) ((Style) (Object) this).withClickEvent(clickEvent);
    }

    @Override
    public CompatStyle withHoverEventCompat(@Nullable HoverEvent hoverEvent) {
        return (CompatStyle) ((Style) (Object) this).withHoverEvent(hoverEvent);
    }

    @Override
    public CompatStyle withInsertionCompat(@Nullable String string) {
        return (CompatStyle) ((Style) (Object) this).withInsertion(string);
    }

}
