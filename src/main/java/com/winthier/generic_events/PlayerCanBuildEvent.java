package com.winthier.generic_events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;

/**
 * A plugin may call this event to ask around if a player is
 * allowed to build at a certain location.  If the event gets
 * cancelled, it means no.  The calling plugin may then choose not
 * do whatever action it was about to commit.
 */
@Getter
public final class PlayerCanBuildEvent extends BlockEvent implements Cancellable {
    private final Player player;
    @Setter private boolean cancelled;

    public PlayerCanBuildEvent(Player player, Block block) {
        super(block);
        this.player = player;
    }

    // Event Stuff
    @Getter private static HandlerList handlerList = new HandlerList();
    @Override public HandlerList getHandlers() {
        return handlerList;
    }
}
