package com.winthier.generic_events;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import java.util.UUID;
import lombok.Getter;

public final class GenericEventsPlugin extends PluginBase {
    @Getter private static GenericEventsPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = sender instanceof Player ? (Player)sender : null;
        String cmd = args.length > 0 ? args[0].toLowerCase() : null;
        if (cmd == null) return false;
        switch (cmd) {
        case "itemname":
            if (player == null) return false;
            sender.sendMessage("Item Name: '" + GenericEvents.getItemName(player.getInventory().getItemInHand()) + "'");
            return true;
        case "permission":
            if (args.length == 3) {
                UUID uuid = GenericEvents.cachedPlayerUuid(args[1]);
                if (uuid == null) {
                    sender.sendMessage("Unknown player: " + args[1]);
                    return true;
                }
                boolean has = GenericEvents.playerHasPermission(uuid, args[2]);
                sender.sendMessage(args[1] + " (" + uuid + ") has " + args[2] + ": " + has);
                return true;
            }
            break;
        default:
            break;
        }
        return true;
    }
}
