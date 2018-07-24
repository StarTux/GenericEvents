package com.winthier.generic_events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter @RequiredArgsConstructor
public final class FormatMoneyEvent extends Event {
    private final double money;
    private String format;

    public void setFormat(String value) {
        if (this.format != null) throw new IllegalStateException("Must not set format more than once");
        this.format = value;
    }

    // Event Stuff
    @Getter private static HandlerList handlerList = new HandlerList();
    @Override public HandlerList getHandlers() {
        return handlerList;
    }
}
