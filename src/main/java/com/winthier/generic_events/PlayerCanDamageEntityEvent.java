package com.winthier.generic_events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;

@Getter
public final class PlayerCanDamageEntityEvent extends EntityEvent implements Cancellable {
    private final Player player;
    @Setter private boolean cancelled;

    public PlayerCanDamageEntityEvent(final Player player, final Entity entity) {
        super(entity);
        this.player = player;
    }

    // Event Stuff
    @Getter private static HandlerList handlerList = new HandlerList();
    @Override public HandlerList getHandlers() {
        return handlerList;
    }
}
