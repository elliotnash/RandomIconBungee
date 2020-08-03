package com.randomiconbungee;

import javafx.scene.control.Tab;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class CommandListener extends Command{
    public CommandListener() {
        super("ribreload");
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender.hasPermission("rib.reload")){
            if (args.length==0){
                //run reload command
                Main.getInstance().pingListener.setFavicons(Main.getInstance().getFavicons());
                commandSender.sendMessage(new TextComponent(ChatColor.DARK_RED+"Reload complete"));
            }
        }
    }
}
