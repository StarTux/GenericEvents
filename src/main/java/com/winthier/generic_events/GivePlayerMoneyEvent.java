package com.winthier.generic_events;

import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;
import cn.nukkit.plugin.Plugin;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public final class GivePlayerMoneyEvent extends Event {
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
    private static HandlerList handlerList = new HandlerList();
    public static HandlerList getHandlers() {
        return handlerList;
    }
}
