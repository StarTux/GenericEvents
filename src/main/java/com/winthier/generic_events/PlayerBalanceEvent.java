package com.winthier.generic_events;

import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public final class PlayerBalanceEvent extends Event {
    private final UUID playerId;
    private double balance;
    private boolean successful;

    public void setBalance(double value) {
        if (successful) throw new IllegalStateException("Must not set balance more than once");
        successful = true;
        this.balance = value;
    }

    // Event Stuff
    private static HandlerList handlerList = new HandlerList();
    public static HandlerList getHandlers() {
        return handlerList;
    }
}
