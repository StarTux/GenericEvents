package com.winthier.generic_events;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

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
    @Getter private static HandlerList handlerList = new HandlerList();
    @Override public HandlerList getHandlers() {
        return handlerList;
    }
}
