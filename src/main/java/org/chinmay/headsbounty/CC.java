package org.chinmay.headsbounty;

import org.bukkit.ChatColor;

public class CC
{
    public static String prefix(final char color) {
        return translate("&" + color + "&l<!> &" + color);
    }
    public static String translate(final String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
    public static String strip(final String string) {
        return ChatColor.stripColor(string);
    }}


