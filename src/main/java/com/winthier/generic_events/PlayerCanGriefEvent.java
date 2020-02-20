package com.winthier.generic_events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;

/**
 * Test whether a player is allowed to grief a certain locations.
 * This goes beyond building and is intended to prevent random
 * landscape griefing.  For example, a player may be allowed to
 * explode the Resource world or their own claim using TNT, but not
 * unclaimed areas in the main build world.
 *
 * If this event gets cancelled, the calling plugin shall not modify
 * the block in questaion.  Any listening plugin should never use this
 * event to trigger any kind of feedback or message to the player.
 */
@Getter @Deprecated
public final class PlayerCanGriefEvent extends BlockEvent implements Cancellable {
    private final Player player;
    @Setter private boolean cancelled;

    PlayerCanGriefEvent(final Player player, final Block block) {
        super(block);
        this.player = player;
    }

    // Event Stuff
    @Getter private static HandlerList handlerList = new HandlerList();
    @Override public HandlerList getHandlers() {
        return handlerList;
    }
}
