package com.winthier.generic_events;

import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;
import cn.nukkit.item.Item;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @RequiredArgsConstructor
public final class ItemNameEvent extends Event {
    private final Item item;
    @Setter private String itemName;

    // Event Stuff
    private static HandlerList handlerList = new HandlerList();
    public static HandlerList getHandlers() {
        return handlerList;
    }
}
