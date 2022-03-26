package com.plusls.xma.compat.chat;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public interface CompatComponent extends Component {
    CompatComponent appendCompat(Component component);

    CompatComponent appendCompat(String string);

    CompatComponent withStyleCompat(Style style);

    CompatComponent withStyleCompat(ChatFormatting... chatFormattings);

    CompatComponent withStyleCompat(ChatFormatting chatFormatting);
}
