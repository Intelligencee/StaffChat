package me.intelligence.staffchat.commands;

import me.intelligence.intelapi.command.CommandInfo;
import me.intelligence.intelapi.command.IntelCommand;
import me.intelligence.intelapi.util.CC;
import me.intelligence.intelapi.util.TextUtil;
import me.intelligence.staffchat.StaffChat;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CommandInfo(name = "staffchat", aliases = { "sc" })
public class StaffChatCommand extends IntelCommand implements Listener {

    private List<UUID> toggled;

    public StaffChatCommand() {
        this.toggled = new ArrayList<UUID>();
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin) StaffChat.getInstance());
    }

    protected void onCommand(final CommandSender sender, final String[] args) {
        if (!sender.hasPermission("staffchat.staff")) {
            sender.sendMessage(CC.translate("&c&l[!] &cYou don't have permission to execute this command!"));
            return;
        }
        if (args.length == 0 && sender instanceof Player) {
            if (this.toggled.remove(((Player)sender).getUniqueId())) {
                sender.sendMessage(CC.translate("&b&l[!] &cStaff chat toggled off."));
            }
            else {
                this.toggled.add(((Player)sender).getUniqueId());
                sender.sendMessage(CC.translate("&b&l[!] &aStaff chat toggled on."));
            }
            return;
        }
        this.checkArgs(args, 1, "/sc <msg>");
        this.send(sender.getName(), TextUtil.combine(args, 0, -1));
    }

    private void send(final String name, final String m) {
        final String msg = CC.translate("&b&l[SC] &b" + name + "&7: &f" + m);
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("staffchat.staff")) {
                player.sendMessage(msg);
            }
        }
    }

    @EventHandler
    public void onChat(final AsyncPlayerChatEvent ev) {
        if (this.toggled.contains(ev.getPlayer().getUniqueId())) {
            ev.setCancelled(true);
            this.send(ev.getPlayer().getName(), ev.getMessage());
        }
    }
}
