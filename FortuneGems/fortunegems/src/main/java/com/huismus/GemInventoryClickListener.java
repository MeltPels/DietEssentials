package com.huismus;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GemInventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(GemInventory.INVENTORY_TITLE)) {
            event.setCancelled(true); // Cancel the event to disable interacting with the GUI
        }
    }
}