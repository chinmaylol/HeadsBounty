package org.chinmay.headsbounty;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {
    private final HeadsBounty headsBounty;

    public ReloadCommand(HeadsBounty headsBounty) {
        this.headsBounty = headsBounty;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, String[] args) {
        if (!sender.hasPermission("headsbounty.reload")) {
            sender.sendMessage(CC.translate("&c&l(!) &cYou do not have permission to use this command"));
            return true;
        }
        headsBounty.reloadConfig();
        sender.sendMessage(CC.translate("&a&l(!) &aBountyHeads Configuration reloaded"));
        return true;
    }

}