package com.winthier.generic_events;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A frontend for incoming vault requests to be handed to
 * GenericEvents instead.
 */
@RequiredArgsConstructor
public final class VaultFrontend {
    private final JavaPlugin plugin;
    private final VaultEconomy economy = new VaultEconomy();

    void register() {
        Bukkit.getServicesManager().register(Economy.class, this.economy, plugin, ServicePriority.High);
    }

    void unregister() {
        Bukkit.getServicesManager().unregister(Economy.class, this.economy);
    }

    public class VaultEconomy implements Economy {
        /**
         * Checks if economy method is enabled.
         * @return Success or Failure
         */
        public boolean isEnabled() {
            return true;
        }

        /**
         * Gets name of economy method
         * @return Name of Economy Method
         */
        public String getName() {
            return "GenericEvents";
        }

        /**
         * Returns true if the given implementation supports banks.
         * @return true if the implementation supports banks
         */
        public boolean hasBankSupport() {
            return false;
        }

        /**
         * Some economy plugins round off after a certain number of digits.
         * This function returns the number of digits the plugin keeps
         * or -1 if no rounding occurs.
         * @return number of digits after the decimal point kept
         */
        public int fractionalDigits() {
            return 2;
        }

        /**
         * Format amount into a human readable String This provides translation into
         * economy specific formatting to improve consistency between plugins.
         *
         * @param amount to format
         * @return Human readable string describing amount
         */
        public String format(double amount) {
            return GenericEvents.formatMoney(amount);
        }

        /**
         * Returns the name of the currency in plural form.
         * If the economy being used does not support currency names then an empty string will be returned.
         *
         * @return name of the currency (plural)
         */
        public String currencyNamePlural() {
            return "Kitty Coin"; // TODO
        }


        /**
         * Returns the name of the currency in singular form.
         * If the economy being used does not support currency names then an empty string will be returned.
         *
         * @return name of the currency (singular)
         */
        public String currencyNameSingular() {
            return "Kitty Coins"; // TODO
        }

        /**
         *
         * @deprecated As of VaultAPI 1.4 use {@link #hasAccount(OfflinePlayer)} instead.
         */
        @Deprecated
        public boolean hasAccount(String playerName) {
            return GenericEvents.cachedPlayerUuid(playerName) != null;
        }

        /**
         * Checks if this player has an account on the server yet
         * This will always return true if the player has joined the server at least once
         * as all major economy plugins auto-generate a player account when the player joins the server
         *
         * @param player to check
         * @return if the player has an account
         */
        public boolean hasAccount(OfflinePlayer player) {
            return GenericEvents.cachedPlayerName(player.getUniqueId()) != null;
        }

        /**
         * @deprecated As of VaultAPI 1.4 use {@link #hasAccount(OfflinePlayer, String)} instead.
         */
        @Deprecated
        public boolean hasAccount(String playerName, String worldName) {
            return hasAccount(playerName);
        }

        /**
         * Checks if this player has an account on the server yet on the given world
         * This will always return true if the player has joined the server at least once
         * as all major economy plugins auto-generate a player account when the player joins the server
         *
         * @param player to check in the world
         * @param worldName world-specific account
         * @return if the player has an account
         */
        public boolean hasAccount(OfflinePlayer player, String worldName) {
            return hasAccount(player);
        }

        /**
         * @deprecated As of VaultAPI 1.4 use {@link #getBalance(OfflinePlayer)} instead.
         */
        @Deprecated
        public double getBalance(String playerName) {
            UUID uuid = GenericEvents.cachedPlayerUuid(playerName);
            if (uuid == null) return 0.0;
            return GenericEvents.getPlayerBalance(uuid);
        }

        /**
         * Gets balance of a player
         *
         * @param player of the player
         * @return Amount currently held in players account
         */
        public double getBalance(OfflinePlayer player) {
            return GenericEvents.getPlayerBalance(player.getUniqueId());
        }

        /**
         * @deprecated As of VaultAPI 1.4 use {@link #getBalance(OfflinePlayer, String)} instead.
         */
        @Deprecated
        public double getBalance(String playerName, String world) {
            return getBalance(playerName);
        }

        /**
         * Gets balance of a player on the specified world.
         * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this the global balance will be returned.
         * @param player to check
         * @param world name of the world
         * @return Amount currently held in players account
         */
        public double getBalance(OfflinePlayer player, String world) {
            return getBalance(player);
        }

        /**
         * @deprecated As of VaultAPI 1.4 use {@link #has(OfflinePlayer, double)} instead.
         */
        @Deprecated
        public boolean has(String playerName, double amount) {
            return getBalance(playerName) >= amount;
        }

        /**
         * Checks if the player account has the amount - DO NOT USE NEGATIVE AMOUNTS
         *
         * @param player to check
         * @param amount to check for
         * @return True if <b>player</b> has <b>amount</b>, False else wise
         */
        public boolean has(OfflinePlayer player, double amount) {
            return getBalance(player) >= amount;
        }

        /**
         * @deprecated As of VaultAPI 1.4 use @{link {@link #has(OfflinePlayer, String, double)} instead.
         */
        @Deprecated
        public boolean has(String playerName, String worldName, double amount) {
            return getBalance(playerName) >= amount;
        }

        /**
         * Checks if the player account has the amount in a given world - DO NOT USE NEGATIVE AMOUNTS
         * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this the global balance will be returned.
         *
         * @param player to check
         * @param worldName to check with
         * @param amount to check for
         * @return True if <b>player</b> has <b>amount</b>, False else wise
         */
        public boolean has(OfflinePlayer player, String worldName, double amount) {
            return getBalance(player) >= amount;
        }

        /**
         * @deprecated As of VaultAPI 1.4 use {@link #withdrawPlayer(OfflinePlayer, double)} instead.
         */
        @Deprecated
        public EconomyResponse withdrawPlayer(String playerName, double amount) {
            UUID playerId = GenericEvents.cachedPlayerUuid(playerName);
            if (playerId == null) return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Player not found: " + playerName);
            boolean res = GenericEvents.takePlayerMoney(playerId, amount, plugin, "GenericEvents VaultFrontend");
            if (res) {
                return new EconomyResponse(amount, GenericEvents.getPlayerBalance(playerId), EconomyResponse.ResponseType.SUCCESS, "Money withdrawn");
            } else {
                return new EconomyResponse(0.0, GenericEvents.getPlayerBalance(playerId), EconomyResponse.ResponseType.FAILURE, "Balance insufficient");
            }
        }

        /**
         * Withdraw an amount from a player - DO NOT USE NEGATIVE AMOUNTS
         *
         * @param player to withdraw from
         * @param amount Amount to withdraw
         * @return Detailed response of transaction
         */
        public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
            UUID playerId = player.getUniqueId();
            boolean res = GenericEvents.takePlayerMoney(playerId, amount, plugin, "GenericEvents VaultFrontend");
            if (res) {
                return new EconomyResponse(amount, GenericEvents.getPlayerBalance(playerId), EconomyResponse.ResponseType.SUCCESS, "Money withdrawn");
            } else {
                return new EconomyResponse(0.0, GenericEvents.getPlayerBalance(playerId), EconomyResponse.ResponseType.FAILURE, "Balance insufficient");
            }
        }

        /**
         * @deprecated As of VaultAPI 1.4 use {@link #withdrawPlayer(OfflinePlayer, String, double)} instead.
         */
        @Deprecated
        public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
            return withdrawPlayer(playerName, amount);
        }

        /**
         * Withdraw an amount from a player on a given world - DO NOT USE NEGATIVE AMOUNTS
         * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this the global balance will be returned.
         * @param player to withdraw from
         * @param worldName - name of the world
         * @param amount Amount to withdraw
         * @return Detailed response of transaction
         */
        public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
            return withdrawPlayer(player, amount);
        }

        /**
         * @deprecated As of VaultAPI 1.4 use {@link #depositPlayer(OfflinePlayer, double)} instead.
         */
        @Deprecated
        public EconomyResponse depositPlayer(String playerName, double amount) {
            UUID playerId = GenericEvents.cachedPlayerUuid(playerName);
            if (playerId == null) return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Player not found: " + playerName);
            GenericEvents.givePlayerMoney(playerId, amount, plugin, "GenericEvents VaultFrontend");
            return new EconomyResponse(amount, GenericEvents.getPlayerBalance(playerId), EconomyResponse.ResponseType.SUCCESS, "Money deposited");
        }

        /**
         * Deposit an amount to a player - DO NOT USE NEGATIVE AMOUNTS
         *
         * @param player to deposit to
         * @param amount Amount to deposit
         * @return Detailed response of transaction
         */
        public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
            UUID playerId = player.getUniqueId();
            GenericEvents.givePlayerMoney(playerId, amount, plugin, "GenericEvents VaultFrontend");
            return new EconomyResponse(amount, GenericEvents.getPlayerBalance(playerId), EconomyResponse.ResponseType.SUCCESS, "Money deposited");
        }

        /**
         * @deprecated As of VaultAPI 1.4 use {@link #depositPlayer(OfflinePlayer, String, double)} instead.
         */
        @Deprecated
        public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
            return depositPlayer(playerName, amount);
        }

        /**
         * Deposit an amount to a player - DO NOT USE NEGATIVE AMOUNTS
         * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this the global balance will be returned.
         *
         * @param player to deposit to
         * @param worldName name of the world
         * @param amount Amount to deposit
         * @return Detailed response of transaction
         */
        public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
            return depositPlayer(player, amount);
        }

        /**
         * @deprecated As of VaultAPI 1.4 use {{@link #createBank(String, OfflinePlayer)} instead.
         */
        @Deprecated
        public EconomyResponse createBank(String name, String player) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not implemented");
        }

        /**
         * Creates a bank account with the specified name and the player as the owner
         * @param name of account
         * @param player the account should be linked to
         * @return EconomyResponse Object
         */
        public EconomyResponse createBank(String name, OfflinePlayer player) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not implemented");
        }

        /**
         * Deletes a bank account with the specified name.
         * @param name of the back to delete
         * @return if the operation completed successfully
         */
        public EconomyResponse deleteBank(String name) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not implemented");
        }

        /**
         * Returns the amount the bank has
         * @param name of the account
         * @return EconomyResponse Object
         */
        public EconomyResponse bankBalance(String name) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not implemented");
        }

        /**
         * Returns true or false whether the bank has the amount specified - DO NOT USE NEGATIVE AMOUNTS
         *
         * @param name of the account
         * @param amount to check for
         * @return EconomyResponse Object
         */
        public EconomyResponse bankHas(String name, double amount) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not implemented");
        }

        /**
         * Withdraw an amount from a bank account - DO NOT USE NEGATIVE AMOUNTS
         *
         * @param name of the account
         * @param amount to withdraw
         * @return EconomyResponse Object
         */
        public EconomyResponse bankWithdraw(String name, double amount) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not implemented");
        }

        /**
         * Deposit an amount into a bank account - DO NOT USE NEGATIVE AMOUNTS
         *
         * @param name of the account
         * @param amount to deposit
         * @return EconomyResponse Object
         */
        public EconomyResponse bankDeposit(String name, double amount) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not implemented");
        }

        /**
         * @deprecated As of VaultAPI 1.4 use {{@link #isBankOwner(String, OfflinePlayer)} instead.
         */
        @Deprecated
        public EconomyResponse isBankOwner(String name, String playerName) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not implemented");
        }

        /**
         * Check if a player is the owner of a bank account
         *
         * @param name of the account
         * @param player to check for ownership
         * @return EconomyResponse Object
         */
        public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not implemented");
        }

        /**
         * @deprecated As of VaultAPI 1.4 use {{@link #isBankMember(String, OfflinePlayer)} instead.
         */
        @Deprecated
        public EconomyResponse isBankMember(String name, String playerName) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not implemented");
        }

        /**
         * Check if the player is a member of the bank account
         *
         * @param name of the account
         * @param player to check membership
         * @return EconomyResponse Object
         */
        public EconomyResponse isBankMember(String name, OfflinePlayer player) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not implemented");
        }

        /**
         * Gets the list of banks
         * @return the List of Banks
         */
        public List<String> getBanks() {
            return Collections.emptyList();
        }

        /**
         * @deprecated As of VaultAPI 1.4 use {{@link #createPlayerAccount(OfflinePlayer)} instead.
         */
        @Deprecated
        public boolean createPlayerAccount(String playerName) {
            return GenericEvents.cachedPlayerUuid(playerName) != null;
        }

        /**
         * Attempts to create a player account for the given player
         * @param player OfflinePlayer
         * @return if the account creation was successful
         */
        public boolean createPlayerAccount(OfflinePlayer player) {
            return true;
        }

        /**
         * @deprecated As of VaultAPI 1.4 use {{@link #createPlayerAccount(OfflinePlayer, String)} instead.
         */
        @Deprecated
        public boolean createPlayerAccount(String playerName, String worldName) {
            return createPlayerAccount(playerName);
        }

        /**
         * Attempts to create a player account for the given player on the specified world
         * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this the global balance will be returned.
         * @param player OfflinePlayer
         * @param worldName String name of the world
         * @return if the account creation was successful
         */
        public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
            return createPlayerAccount(player);
        }
    }
}
