package com.winthier.generic_events;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter @RequiredArgsConstructor
public final class PlayerBalanceEvent extends Event {
    private final UUID uuid;
    private double balance;
    private boolean successful;

    void setBalance(double value) {
        if (successful) throw new IllegalStateException("Must not set balance more than once");
        successful = true;
        this.balance = value;
    }

    // Event Stuff
    @Getter private static HandlerList handlerList = new HandlerList();
    @Override public HandlerList getHandlers() {
        return handlerList;
    }
}
