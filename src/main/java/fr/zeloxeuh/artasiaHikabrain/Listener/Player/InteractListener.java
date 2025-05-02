package fr.zeloxeuh.artasiaHikabrain.Listener.Player;

import fr.zeloxeuh.artasiaHikabrain.ArtasiaHikabrain;
import fr.zeloxeuh.artasiaHikabrain.Manager.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class InteractListener implements Listener {

    private ArtasiaHikabrain instance;
    private GameManager gameManager;

    public InteractListener(ArtasiaHikabrain instance, GameManager gameManager) {
        this.instance = instance;
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        if (item == null) {
            return;
        }
        if (item.getType().equals(Material.COMPASS) && item.hasItemMeta() && item.getItemMeta().getDisplayName().equals("§6Teams Selection")) {
            if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
                Inventory inv = Bukkit.createInventory(null, 27, "§8Teams");

                // Red Team item
                ItemStack redTeam = new ItemStack(Material.RED_WOOL);
                ItemMeta redMeta = redTeam.getItemMeta();
                assert redMeta != null;
                redMeta.setDisplayName("§cRed Team");
                redMeta.setLore(List.of("§7This team will be red."));
                redTeam.setItemMeta(redMeta);

                // Blue Team item
                ItemStack blueTeam = new ItemStack(Material.BLUE_WOOL);
                ItemMeta blueMeta = blueTeam.getItemMeta();
                assert blueMeta != null;
                blueMeta.setDisplayName("§9Blue Team");
                blueMeta.setLore(List.of("§7This team will be blue."));
                blueTeam.setItemMeta(blueMeta);

                // Set Team item in inventory
                inv.setItem(12, new ItemStack(redTeam));
                inv.setItem(14, new ItemStack(blueTeam));
                player.openInventory(inv);
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inv = event.getInventory();
        String invTitle = event.getView().getTitle();

        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (item == null) return;

        if (invTitle.equals("§8Teams")) {
            event.setCancelled(true);
            player.closeInventory();

            switch (item.getType()) {
                case RED_WOOL:
                    break;
                case BLUE_WOOL:
                    break;
                default:
                    break;
            }
        }
    }

}
