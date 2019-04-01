package com.winthier.generic_events;

import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor @Getter
public final class PlayerHasPermissionEvent extends Event {
    private final UUID playerId;
    private final String permission;
    @Setter private boolean permitted = false;

    // Event Stuff
    private static HandlerList handlerList = new HandlerList();
    public static HandlerList getHandlers() {
        return handlerList;
    }
}
