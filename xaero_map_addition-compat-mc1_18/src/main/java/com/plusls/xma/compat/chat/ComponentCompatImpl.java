package com.plusls.xma.compat.chat;

import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;

import java.util.HashMap;
import java.util.Map;

public class ComponentCompatImpl extends ComponentCompatApi {

    private static final Map<HoverEventAction, HoverEvent.Action<?>> hoverEventActionMap = new HashMap<>();

    static {
        hoverEventActionMap.put(HoverEventAction.SHOW_TEXT, HoverEvent.Action.SHOW_TEXT);
        hoverEventActionMap.put(HoverEventAction.SHOW_ENTITY, HoverEvent.Action.SHOW_ENTITY);
        hoverEventActionMap.put(HoverEventAction.SHOW_ITEM, HoverEvent.Action.SHOW_ITEM);
    }

    public static void init() {
        INSTANCE = new ComponentCompatImpl();
    }

    @SuppressWarnings("unchecked")
    private static <T> HoverEvent newHoverEventHelper(HoverEvent.Action<?> action, T obj) {
        return new HoverEvent((HoverEvent.Action<T>) action, obj);
    }

    @Override
    public CompatStyle getEmptyStyle() {
        return (CompatStyle) Style.EMPTY;
    }

    @Override
    public HoverEvent newHoverEvent(HoverEventAction action, Object obj) {
        return newHoverEventHelper(hoverEventActionMap.get(action), obj);
    }
}
