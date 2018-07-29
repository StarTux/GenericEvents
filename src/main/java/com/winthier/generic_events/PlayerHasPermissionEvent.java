package com.winthier.generic_events;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@RequiredArgsConstructor @Getter
public final class PlayerHasPermissionEvent extends Event {
    private final UUID playerId;
    private final String permission;
    @Setter private boolean permitted = false;

    // Event Stuff
    @Getter private static HandlerList handlerList = new HandlerList();
    @Override public HandlerList getHandlers() {
        return handlerList;
    }
}
