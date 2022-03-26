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
        return (CompatStyle) ((Style) (Object) this).copy().setColor(chatFormatting);
    }

    @Override
    public CompatStyle withBoldCompat(@Nullable Boolean boolean_) {
        return (CompatStyle) ((Style) (Object) this).copy().setBold(boolean_);
    }

    @Override
    public CompatStyle withItalicCompat(@Nullable Boolean boolean_) {
        return (CompatStyle) ((Style) (Object) this).copy().setItalic(boolean_);
    }

    @Override
    public CompatStyle withUnderlinedCompat(@Nullable Boolean boolean_) {
        return (CompatStyle) ((Style) (Object) this).copy().setUnderlined(boolean_);
    }

    @Override
    public CompatStyle withStrikethroughCompat(@Nullable Boolean boolean_) {
        return (CompatStyle) ((Style) (Object) this).copy().setStrikethrough(boolean_);
    }

    @Override
    public CompatStyle withObfuscatedCompat(@Nullable Boolean boolean_) {
        return (CompatStyle) ((Style) (Object) this).copy().setObfuscated(boolean_);
    }

    @Override
    public CompatStyle withClickEventCompat(@Nullable ClickEvent clickEvent) {
        return (CompatStyle) ((Style) (Object) this).copy().setClickEvent(clickEvent);
    }

    @Override
    public CompatStyle withHoverEventCompat(@Nullable HoverEvent hoverEvent) {
        return (CompatStyle) ((Style) (Object) this).copy().setHoverEvent(hoverEvent);
    }

    @Override
    public CompatStyle withInsertionCompat(@Nullable String string) {
        return (CompatStyle) ((Style) (Object) this).copy().setInsertion(string);
    }

}
