package com.winthier.generic_events;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.entity.EntityEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
public final class PlayerCanDamageEntityEvent extends EntityEvent implements Cancellable {
    private final Player player;
    @Setter private boolean cancelled;

    public PlayerCanDamageEntityEvent(Player player, Entity entity) {
        this.entity = entity;
        this.player = player;
    }

    // Event Stuff
    private static HandlerList handlerList = new HandlerList();
    public static HandlerList getHandlers() {
        return handlerList;
    }
}
