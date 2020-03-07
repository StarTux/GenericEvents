package com.winthier.generic_events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@RequiredArgsConstructor
@Getter @ToString
public final class PlayerCanTeleportEvent extends Event implements Cancellable {
    private final Player player;
    private final Location location;
    @Setter private boolean cancelled;

    // Event Stuff

    @Getter private static HandlerList handlerList = new HandlerList();

    @Override public HandlerList getHandlers() {
        return handlerList;
    }
}
