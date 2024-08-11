package org.chinmay.headsbounty;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class HeadsBounty extends JavaPlugin {

    private static Economy econ = null;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        Objects.requireNonNull(this.getCommand("hbr")).setExecutor(new ReloadCommand(this));
        getServer().getPluginManager().registerEvents(new DeathEvent(this), this);
        getServer().getPluginManager().registerEvents(new SkullListener(this), this);
        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled Due to no Vault or Economy Provider (EssentialsX, CMI) Found", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getLogger().info("HeadsBounty has been enabled!");
        getLogger().info("Thank you for using my plugin");
        getLogger().info("You can see more of my plugins @ plugins.chinmay.lol");
    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public static Economy getEconomy() {
        return econ;
    }

  @Override
    public void onDisable() {
      saveConfig();
      getLogger().info("HeadsBounty has been disabled!");
      getLogger().info("Thank you for using my plugin");
      getLogger().info("You can see more of my plugins @ plugins.chinmay.lol");
    }
}

