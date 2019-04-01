package com.winthier.generic_events;

import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public final class PlayerCacheEvent extends Event {
    private UUID uniqueId;
    private String name;

    /**
     * Request to get the name matching the UUID.
     */
    public PlayerCacheEvent(UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.name = null;
    }

    /**
     * Request to get the UUID matching the name.
     */
    public PlayerCacheEvent(String name) {
        this.name = name;
        this.uniqueId = null;
    }

    // Event Stuff
    private static HandlerList handlerList = new HandlerList();
    public static HandlerList getHandlers() {
        return handlerList;
    }
}
