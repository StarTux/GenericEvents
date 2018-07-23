package com.winthier.generic_events;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

@Getter @RequiredArgsConstructor
public final class TakePlayerMoneyEvent extends Event {
    private final UUID playerId;
    private final double amount;
    private final Plugin issuingPlugin;
    private final String comment;
    private boolean handled;
    private boolean successful;

    public void setSuccessful(boolean successful) {
        if (handled) throw new IllegalStateException("Must not set balance more than once");
        this.handled = true;
        this.successful = successful;
    }

    // Event Stuff
    @Getter private static HandlerList handlerList = new HandlerList();
    @Override public HandlerList getHandlers() {
        return handlerList;
    }
}
