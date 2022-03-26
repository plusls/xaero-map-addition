package com.plusls.xma.compat.chat;

import net.minecraft.network.chat.HoverEvent;

public abstract class ComponentCompatApi {
    protected static ComponentCompatApi INSTANCE;

    public static ComponentCompatApi getInstance() {
        return INSTANCE;
    }

    public abstract CompatStyle getEmptyStyle();

    public abstract HoverEvent newHoverEvent(HoverEventAction action, Object obj);

    public enum HoverEventAction {
        SHOW_TEXT,
        SHOW_ITEM,
        SHOW_ENTITY
    }

}
