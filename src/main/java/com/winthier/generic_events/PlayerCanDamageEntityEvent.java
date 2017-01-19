package com.winthier.generic_events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;

@Getter
public class PlayerCanDamageEntityEvent extends EntityEvent implements Cancellable {
    final Player player;
    @Setter boolean cancelled;

    public PlayerCanDamageEntityEvent(Player player, Entity entity) {
        super(entity);
        this.player = player;
    }

    // Event Stuff 
    @Getter static HandlerList handlerList = new HandlerList();
    @Override public HandlerList getHandlers() { return handlerList; }
}
