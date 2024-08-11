package org.chinmay.headsbounty;


import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullListener implements Listener {
    private final HeadsBounty headsBounty;

    public SkullListener(HeadsBounty headsBounty) {
        this.headsBounty = headsBounty;
    }

    @EventHandler
    public void SkullRedeem(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (e.getItem() != null && e.getItem().getType() == Material.PLAYER_HEAD) {
                Player p = e.getPlayer();
                ItemStack head = e.getItem();
                SkullMeta meta = (SkullMeta) head.getItemMeta();
                assert meta != null;
                OfflinePlayer victim = meta.getOwningPlayer();

                if (victim != null && victim.hasPlayedBefore()) {
                    String playerName = p.getDisplayName();
                    String victimName = victim.getName() != null ? victim.getName() : "";

                    double amount = headsBounty.getEconomy().getBalance(victim) *
                            headsBounty.getConfig().getDouble("percentage");

                    String message = headsBounty.getConfig().getString("skull-redeem-message", "");

                    message = CC.translate(message
                            .replace("%player%", playerName)
                            .replace("%victim%", victimName)
                            .replace("%amount%", headsBounty.getEconomy().format(amount))
                    );

                    p.sendMessage(message);
                    headsBounty.getEconomy().depositPlayer(p, amount);
                    if (victim.getPlayer() != null) {
                        String victimMessage = CC.translate(
                                headsBounty.getConfig().getString("victim-message", "")
                                .replace("%player%", playerName)
                                .replace("%victim%", victimName)
                                .replace("%amount%", headsBounty.getEconomy().format(amount))
                        );

                        victim.getPlayer().sendMessage(victimMessage);
                    }
                    headsBounty.getEconomy().withdrawPlayer(victim, amount);
                    p.getInventory().remove(head);
                }
            }
        }
    }
}