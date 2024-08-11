package org.chinmay.headsbounty;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.stream.Collectors;

public class DeathEvent implements Listener {
    private final HeadsBounty headsBounty;

    public DeathEvent(HeadsBounty headsBounty) {
        this.headsBounty = headsBounty;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player victim = e.getEntity();
        Player killer = victim.getKiller();
        if (killer != null && killer.isOnline()) {
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            assert meta != null;
            meta.setOwningPlayer(victim);
            String displayName = CC.translate(
                    headsBounty.getConfig().getString("skull-item-name", "")
                    .replace("%victim%", victim.getDisplayName())
            );

            meta.setDisplayName(displayName);

            double percentage = headsBounty.getConfig().getDouble("percentage");
            double amount = headsBounty.getEconomy().getBalance(victim) * percentage;

            List<String> lore = headsBounty.getConfig().getStringList("skull-item-lore")
                    .stream().map(line -> CC.translate(line
                    .replace("%victim%", victim.getName())
                    .replace("%player%", killer.getDisplayName())
                    .replace("%amount%", headsBounty.getEconomy().format(amount)))).collect(Collectors.toList());
            meta.setLore(lore);
            skull.setItemMeta(meta);
            killer.getInventory().addItem(skull);
        }
    }

}
