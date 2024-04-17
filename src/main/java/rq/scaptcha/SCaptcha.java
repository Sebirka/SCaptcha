package rq.scaptcha;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import rq.scaptcha.Utils.Config;
import rq.scaptcha.Utils.Events;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import java.io.File;
import java.util.*;

import static org.bukkit.ChatColor.*;

public class SCaptcha extends JavaPlugin implements Listener {
    public static HashMap<UUID, String> captchaPlayers = new HashMap<>();
    //  public static String mesagge = GRAY + "[" + GOLD + "Protect" + GRAY + "] ";
    public static HashSet<UUID> verifiedPlayers = new HashSet<>();
    public static Map<UUID, ItemStack[]> playerInventories = new HashMap<>();
    public static FileConfiguration config;
    public static Config configFile;

    @Override
    public void onEnable() {
        config = this.getConfig();
        config.options().copyDefaults(true);
        this.saveConfig();
      //  getCommand("sc").setExecutor(new Commands());
        Bukkit.getPluginManager().registerEvents(new cap(), this);
        Bukkit.getPluginManager().registerEvents(new Events(), this);
        getServer().getLogger().info(GRAY + "Плагин SCaptcha успешно включился");
        getServer().getLogger().info(GRAY + "Спасибо за использование");
        getServer().getLogger().info(GRAY + "Сделано пользователем:");
        getServer().getLogger().info("░██████╗███████╗██████╗░██╗██████╗░██╗░░██╗░█████╗░");
        getServer().getLogger().info("██╔════╝██╔════╝██╔══██╗██║██╔══██╗██║░██╔╝██╔══██╗");
        getServer().getLogger().info("╚█████╗░█████╗░░██████╦╝██║██████╔╝█████═╝░███████║");
        getServer().getLogger().info("░╚═══██╗██╔══╝░░██╔══██╗██║██╔══██╗██╔═██╗░██╔══██║");
        getServer().getLogger().info("██████╔╝███████╗██████╦╝██║██║░░██║██║░╚██╗██║░░██║");
        getServer().getLogger().info("╚═════╝░╚══════╝╚═════╝░╚═╝╚═╝░░╚═╝╚═╝░░╚═╝╚═╝░░╚═╝");
    }

    @Override
    public void onDisable() {
        getServer().getLogger().info(GRAY + "Плагин SCaptcha выключен");
        getServer().getLogger().info(GRAY + "Сделано пользователем:");
        getServer().getLogger().info("░██████╗███████╗██████╗░██╗██████╗░██╗░░██╗░█████╗░");
        getServer().getLogger().info("██╔════╝██╔════╝██╔══██╗██║██╔══██╗██║░██╔╝██╔══██╗");
        getServer().getLogger().info("╚█████╗░█████╗░░██████╦╝██║██████╔╝█████═╝░███████║");
        getServer().getLogger().info("░╚═══██╗██╔══╝░░██╔══██╗██║██╔══██╗██╔═██╗░██╔══██║");
        getServer().getLogger().info("██████╔╝███████╗██████╦╝██║██║░░██║██║░╚██╗██║░░██║");
        getServer().getLogger().info("╚═════╝░╚══════╝╚═════╝░╚═╝╚═╝░░╚═╝╚═╝░░╚═╝╚═╝░░╚═╝");
    }
}