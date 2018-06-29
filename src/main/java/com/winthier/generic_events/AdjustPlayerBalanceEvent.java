package com.winthier.generic_events;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter @RequiredArgsConstructor
public final class AdjustPlayerBalanceEvent extends Event {
    private final UUID uuid;
    private final double balance;
    private boolean successful;

    void setSuccessful() {
        if (successful) throw new IllegalStateException("Must not set balance more than once");
        successful = true;
    }

    // Event Stuff
    @Getter private static HandlerList handlerList = new HandlerList();
    @Override public HandlerList getHandlers() {
        return handlerList;
    }
}
