package com.plusls.xma.compat.chat;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;

import java.util.HashMap;
import java.util.Map;

public class ComponentCompatImpl extends ComponentCompatApi {

    private static final Map<HoverEventAction, HoverEvent.Action> hoverEventActionMap = new HashMap<>();

    static {
        hoverEventActionMap.put(HoverEventAction.SHOW_TEXT, HoverEvent.Action.SHOW_TEXT);
        hoverEventActionMap.put(HoverEventAction.SHOW_ENTITY, HoverEvent.Action.SHOW_ENTITY);
        hoverEventActionMap.put(HoverEventAction.SHOW_ITEM, HoverEvent.Action.SHOW_ITEM);
    }

    public static void init() {
        INSTANCE = new ComponentCompatImpl();
    }

    @Override
    public CompatStyle getEmptyStyle() {
        return (CompatStyle) new Style();
    }

    @Override
    public HoverEvent newHoverEvent(HoverEventAction action, Object obj) {
        return new HoverEvent(hoverEventActionMap.get(action), (Component) obj);
    }
}
