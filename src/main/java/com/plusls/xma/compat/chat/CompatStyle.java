package com.plusls.xma.compat.chat;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.HoverEvent;
import org.jetbrains.annotations.Nullable;

public interface CompatStyle {

    CompatStyle withColorCompat(@Nullable ChatFormatting chatFormatting);

    CompatStyle withBoldCompat(@Nullable Boolean boolean_);

    CompatStyle withItalicCompat(@Nullable Boolean boolean_);

    CompatStyle withUnderlinedCompat(@Nullable Boolean boolean_);

    CompatStyle withStrikethroughCompat(@Nullable Boolean boolean_);

    CompatStyle withObfuscatedCompat(@Nullable Boolean boolean_);

    CompatStyle withClickEventCompat(@Nullable ClickEvent clickEvent);

    CompatStyle withHoverEventCompat(@Nullable HoverEvent hoverEvent);

    CompatStyle withInsertionCompat(@Nullable String string);

}
