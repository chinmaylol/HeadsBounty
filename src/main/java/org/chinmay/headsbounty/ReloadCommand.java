package org.chinmay.headsbounty;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {
    private final HeadsBounty headsBounty;
    public ReloadCommand(HeadsBounty headsBounty) {
        this.headsBounty = headsBounty;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        if (!player.hasPermission("headsbounty.reload")) {
            player.sendMessage(CC.translate("&c&l(!) &cYou do not have permission to use this command"));
        } else {
            headsBounty.reloadConfig();
            player.sendMessage(CC.translate("&a&l(!) &aBountyHeads Configuration reloaded"));
        }
        return false;
    }
}
