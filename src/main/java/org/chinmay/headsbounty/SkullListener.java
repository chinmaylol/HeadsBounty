package org.chinmay.headsbounty;


import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Objects;

public class SkullListener implements Listener {
    private final HeadsBounty headsBounty;
    public SkullListener(HeadsBounty headsBounty) {
        this.headsBounty = headsBounty;
    }
    @EventHandler
    public void SkullRedeem(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (e.getItem() != null && e.getItem().getType() == Material.PLAYER_HEAD) {
                Economy economy = HeadsBounty.getEconomy();
                Player p = e.getPlayer();
                ItemStack head = e.getItem();
                SkullMeta meta = (SkullMeta) head.getItemMeta();
                assert meta != null;
                if (meta.hasOwner()) {
                    Player victim = (Player) meta.getOwningPlayer();
                    assert victim != null;
                    double amount = economy.getBalance(victim) * headsBounty.getConfig().getDouble("percentage");
                    economy.format(amount);
                    String message = headsBounty.getConfig().getString("skull-redeem-message");
                        assert message != null;
                        message = message.replace("%amount%", economy.format(Double.parseDouble(String.valueOf(amount))));
                        message = message.replace("%player%", p.getDisplayName());
                        message = message.replace("%victim%", Objects.requireNonNull(victim.getName()));
                        message= ChatColor.translateAlternateColorCodes('&', message);
                    p.sendMessage(message);
                        economy.depositPlayer(p, amount);
                        if (victim.isOnline()) {
                            String victimmessage = headsBounty.getConfig().getString("victim-message");
                            assert victimmessage != null;
                            victimmessage = victimmessage.replace("%amount%", economy.format(Double.parseDouble(String.valueOf(amount))));
                            victimmessage = victimmessage.replace("%player%", p.getDisplayName());
                            victimmessage = victimmessage.replace("%victim%", Objects.requireNonNull(victim.getName()));
                            victimmessage= ChatColor.translateAlternateColorCodes('&', victimmessage);
                            victim.sendMessage(victimmessage);
                        }
                        economy.withdrawPlayer(victim, amount);
                        p.getInventory().remove(head);
                    }
                }
            }}
    }
