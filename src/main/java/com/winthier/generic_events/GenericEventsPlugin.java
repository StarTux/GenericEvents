package com.winthier.generic_events;

import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class GenericEventsPlugin extends JavaPlugin {
    @Getter private static GenericEventsPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public boolean playerCanBuild(Player player, Block block) {
        PlayerCanBuildEvent event = new PlayerCanBuildEvent(player, block);
        getServer().getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    public boolean playerCanDamageEntity(Player player, Entity entity) {
        PlayerCanDamageEntityEvent event = new PlayerCanDamageEntityEvent(player, entity);
        getServer().getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    public String getItemName(ItemStack item) {
        ItemNameEvent event = new ItemNameEvent(item);
        getServer().getPluginManager().callEvent(event);
        return event.getItemName();
    }
}
