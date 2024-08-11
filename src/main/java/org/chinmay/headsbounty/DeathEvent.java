package org.chinmay.headsbounty;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class DeathEvent implements Listener {
    private final HeadsBounty headsBounty;
    public DeathEvent(HeadsBounty headsBounty) {
        this.headsBounty = headsBounty;
    }@EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player victim = e.getEntity();
        Player killer = victim.getKiller();
        if (killer!= null && killer.isOnline() ) {
            Economy economy = HeadsBounty.getEconomy();
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta meta = (SkullMeta) skull.getItemMeta();
                assert meta != null;
                meta.setOwningPlayer(victim);
                String displayname = headsBounty.getConfig().getString("skull-item-name");
            assert displayname != null;
            displayname = displayname.replace("%victim%", victim.getName());
            displayname = ChatColor.translateAlternateColorCodes('&', displayname);
            meta.setDisplayName(displayname);
            double percentage = headsBounty.getConfig().getDouble("percentage");
            double amount = economy.getBalance(victim) * percentage;
            economy.format(amount);
            List<String> lore = headsBounty.getConfig().getStringList("skull-item-lore");
            List<String> newlore = new ArrayList<>();
            for (String line : lore) {
                line = line.replace("%victim%", victim.getName());
                line = line.replace("%player%", killer.getName());
                line = line.replace("%amount%", economy.format(Double.parseDouble(String.valueOf(amount))));
                line = ChatColor.translateAlternateColorCodes('&', line);
                newlore.add(line);
            }
            meta.setLore(newlore);
                skull.setItemMeta(meta); 
                killer.getInventory().addItem(skull);
            }}}

