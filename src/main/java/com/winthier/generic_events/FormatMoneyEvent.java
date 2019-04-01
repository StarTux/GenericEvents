package com.winthier.generic_events;

import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public final class FormatMoneyEvent extends Event {
    private final double money;
    private String format;

    public void setFormat(String value) {
        if (this.format != null) throw new IllegalStateException("Must not set format more than once");
        this.format = value;
    }

    // Event Stuff
    private static HandlerList handlerList = new HandlerList();
    public static HandlerList getHandlers() {
        return handlerList;
    }
}
