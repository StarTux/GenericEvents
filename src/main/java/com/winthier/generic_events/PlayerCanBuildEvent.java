package com.winthier.generic_events;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.block.BlockEvent;
import lombok.Getter;
import lombok.Setter;

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
    private static HandlerList handlerList = new HandlerList();
    public static HandlerList getHandlers() {
        return handlerList;
    }
}
