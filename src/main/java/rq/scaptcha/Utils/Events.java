package rq.scaptcha.Utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.TabCompleteEvent;
import rq.scaptcha.SCaptcha;

import java.util.List;

import static rq.scaptcha.SCaptcha.captchaPlayers;
import static rq.scaptcha.SCaptcha.verifiedPlayers;

public class Events implements Listener {
    SCaptcha plugin = SCaptcha.getPlugin(SCaptcha.class);

    @EventHandler
    public void PlayerDropItemEvent(PlayerDropItemEvent event) {
        if (plugin.getConfig().getBoolean("cfg.DropItemEvent")) {
            if (captchaPlayers.containsKey(event.getPlayer().getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onInventoryclick(InventoryClickEvent event) {

        if (captchaPlayers.containsKey(event.getWhoClicked().getUniqueId())) {
            event.getWhoClicked().closeInventory();
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (captchaPlayers.containsKey(event.getPlayer().getUniqueId())) {
            event.getPlayer().closeInventory();

        }
    }
    @EventHandler
    public void InventoryDragEvent (InventoryDragEvent event) {
        if (captchaPlayers.containsKey(event.getWhoClicked().getUniqueId())) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void InventoryInteractEvent(InventoryInteractEvent event) {
        if (captchaPlayers.containsKey(event.getWhoClicked().getUniqueId())) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void InventoryOpenEvent(InventoryOpenEvent event) {
        if (captchaPlayers.containsKey(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerPickupItemEvent (PlayerPickupItemEvent event) {
        if (plugin.getConfig().getBoolean("cfg.PickupItemEvent")) {
    }
        if (captchaPlayers.containsKey(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void PlayerTeleportEvent(PlayerTeleportEvent event) {
        if (plugin.getConfig().getBoolean("cfg.TeleportEvent")) {
            if (captchaPlayers.containsKey(event.getPlayer().getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void PlayerMove(PlayerMoveEvent event) {
        if (plugin.getConfig().getBoolean("cfg.MoveEvent")) {
            if (captchaPlayers.containsKey(event.getPlayer().getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }
        @EventHandler
    public void PlayerCommandEvent(PlayerCommandPreprocessEvent event) {
            if (plugin.getConfig().getBoolean("cfg.CommandEvent")) {
                if (captchaPlayers.containsKey(event.getPlayer().getUniqueId())) {
                    event.setCancelled(true);
                }
            }
        }


    @EventHandler
    public void PlayerItemHeldEvent(PlayerItemHeldEvent event) {
        if (plugin.getConfig().getBoolean("cfg.ItemHeldEvent")) {
            if (captchaPlayers.containsKey(event.getPlayer().getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }
}
