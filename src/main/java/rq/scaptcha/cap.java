package rq.scaptcha;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import rq.scaptcha.Utils.ColorUtil;
import rq.scaptcha.Utils.Config;


import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.GRAY;
import static rq.scaptcha.SCaptcha.*;

public class cap implements Listener {
    SCaptcha plugin = SCaptcha.getPlugin(SCaptcha.class);


    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (plugin.getConfig().getBoolean("cfg.teleport_on_join")) {
            String worldName = plugin.getConfig().getString("cfg.world");
            World world = Bukkit.getWorld(worldName);
            double x = plugin.getConfig().getDouble("cfg.x");
            double y = plugin.getConfig().getDouble("cfg.y");
            double z = plugin.getConfig().getDouble("cfg.z");
            Location location = new Location(world, x, y, z);
            player.teleport(location);
        }
        if (!verifiedPlayers.contains(event.getPlayer().getUniqueId())) {
            playerInventories.put(player.getUniqueId(), player.getInventory().getContents());
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                player.getInventory().clear();
            }
        }.runTaskLater(plugin, 20L);
        List<String> messages = plugin.getConfig().getStringList("chat.join");
        for (String message : messages) {
            player.sendMessage((ColorUtil.setColor(message)));
            player.setInvulnerable(true);
        }

        String symbols = plugin.getConfig().getString("cfg.captcha_symbols");
        int length = plugin.getConfig().getInt("cfg.captcha_length");

        Random rand = new Random();
        StringBuilder captcha = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            char symbol = symbols.charAt(rand.nextInt(symbols.length()));
            captcha.append(symbol);
        }
        captchaPlayers.put(player.getUniqueId(), captcha.toString());
        List<String> instructions = plugin.getConfig().getStringList("chat.instructions");
        for (String instruction : instructions) {
            player.sendMessage(ColorUtil.setColor(instruction));

            new BukkitRunnable() {
                int counter = 0;

                @Override
                public void run() {
                    if (counter < 60) {
                        if (captchaPlayers.containsKey(event.getPlayer().getUniqueId())) {
                            String captcha = captchaPlayers.get(event.getPlayer().getUniqueId());
                            String displayedCaptcha = captcha.substring(0, Math.min(counter / 1, captcha.length()));
                            String ins = plugin.getConfig().getString("chat.ins");
                            String color = plugin.getConfig().getString("cfg.color");
                            int i = plugin.getConfig().getInt("title.i");
                            int i2 = plugin.getConfig().getInt("title.i2");
                            event.getPlayer().sendTitle((ColorUtil.setColor(color + displayedCaptcha + ChatColor.COLOR_CHAR + "k" + captcha.substring(displayedCaptcha.length()))), ColorUtil.setColor(ins), i, 40, i2) ;
                            counter++;
                        } else {
                            this.cancel();
                        }
                    } else {
                        if (captchaPlayers.containsKey(event.getPlayer().getUniqueId())) {
                            String kickMessage = plugin.getConfig().getString("chat.kickMessage");
                            event.getPlayer().kickPlayer((ColorUtil.setColor(kickMessage)));
                            this.cancel();
                        }
                    }
                }
            }.runTaskTimer(plugin, 0, 20);
        }
    }


    @EventHandler
    public void PlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (playerInventories.containsKey(player.getUniqueId())) {
            player.getInventory().setContents(playerInventories.get(player.getUniqueId()));
        }
    }
    @EventHandler
    public void PlayerChat(AsyncPlayerChatEvent event) {
        UUID playerUUID = event.getPlayer().getUniqueId();
        Player player = event.getPlayer();
        SCaptcha plugin = SCaptcha.getPlugin(SCaptcha.class);

        if (captchaPlayers.containsKey(playerUUID)) {
            String enteredCaptcha = event.getMessage();
            if (enteredCaptcha.equals(captchaPlayers.get(playerUUID))) {
                captchaPlayers.remove(playerUUID);
                player.setInvulnerable(false);
                String successMessage = plugin.getConfig().getString("chat.successMessage");
                event.getPlayer().sendMessage((ColorUtil.setColor(successMessage)));
                PlayerVerified(player);
            } else {
                String errorMessage = plugin.getConfig().getString("chat.errorMessage");
                event.getPlayer().sendMessage((ColorUtil.setColor(errorMessage)));
            }
            event.setCancelled(true);
        }
    }


    public void PlayerVerified(Player player) {
        if (playerInventories.containsKey(player.getUniqueId())) {
            player.getInventory().setContents(playerInventories.get(player.getUniqueId()));
            playerInventories.remove(player.getUniqueId());
        }
    }
}