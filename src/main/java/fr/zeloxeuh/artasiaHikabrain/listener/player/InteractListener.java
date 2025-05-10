package fr.zeloxeuh.artasiaHikabrain.listener.player;

import fr.zeloxeuh.artasiaHikabrain.builder.ItemBuilder;
import fr.zeloxeuh.artasiaHikabrain.manager.GameManager;
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
import java.util.Objects;

public class InteractListener implements Listener {
    private final GameManager gameManager;

    public InteractListener( GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        if (item == null) return;

        if (item.getType().equals(Material.COMPASS) && item.hasItemMeta() && Objects.requireNonNull(item.getItemMeta()).getDisplayName().equals("§6Teams Selection")) {
            if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
                Inventory inv = Bukkit.createInventory(null, 27, "§8Teams");

                ItemStack redTeam =  new ItemBuilder(Material.RED_WOOL)
                        .setName("§cRed Team")
                        .setLore("§7This team will be red.")
                        .build();

                ItemStack blueTeam = new ItemBuilder(Material.BLUE_WOOL)
                        .setName("§9Blue Team")
                        .setLore("§7This team will be blue.")
                        .build();

                // Set Team item in inventory
                inv.setItem(12, new ItemStack(redTeam));
                inv.setItem(14, new ItemStack(blueTeam));
                player.openInventory(inv);
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        String invTitle = event.getView().getTitle();
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (item == null) return;

        if (invTitle.equals("§8Teams")) {
            event.setCancelled(true);
            player.closeInventory();

            switch (item.getType()) {
                case RED_WOOL:
                    // Retirer le joueur de son équipe actuelle si nécessaire et l'ajouter à l'équipe rouge
                    if (gameManager.getBlueTeam().getPlayers().contains(player)) {
                        gameManager.getBlueTeam().removePlayer(player);
                    }
                    if (!gameManager.getRedTeam().getPlayers().contains(player)) {
                        gameManager.getRedTeam().addPlayer(player);
                    }

                    // Placer l'item dans la hotbar du joueur
                    ItemStack redWoolItem = new ItemBuilder(Material.RED_WOOL)
                            .setName(gameManager.getRedTeam().getColor() + "Équipe " + gameManager.getRedTeam().getName())
                            .setLore("§7Vous êtes dans l'équipe rouge")
                            .build();
                    player.getInventory().setItem(0, redWoolItem);
                    player.updateInventory();


                    // Message de confirmation
                    player.sendMessage("§7[§eHikaBrain§7] §fVous avez rejoint l'équipe " + 
                            gameManager.getRedTeam().getColor() + gameManager.getRedTeam().getName());
                    break;
                    
                case BLUE_WOOL:
                    // Retirer le joueur de son équipe actuelle si nécessaire et l'ajouter à l'équipe bleu
                    if (gameManager.getRedTeam().getPlayers().contains(player)) {
                        gameManager.getRedTeam().removePlayer(player);
                    }
                    if (!gameManager.getBlueTeam().getPlayers().contains(player)) {
                        gameManager.getBlueTeam().addPlayer(player);
                    }

                    // Placer l'item dans la hotbar du joueur
                    ItemStack blueWoolItem = new ItemBuilder(Material.BLUE_WOOL)
                            .setName(gameManager.getBlueTeam().getColor() + "Équipe " + gameManager.getBlueTeam().getName())
                            .setLore("§7Vous êtes dans l'équipe bleue")
                            .build();
                    player.getInventory().setItem(0, blueWoolItem);
                    player.updateInventory();
                    
                    // Message de confirmation
                    player.sendMessage("§7[§eHikaBrain§7] §fVous avez rejoint l'équipe " + 
                            gameManager.getBlueTeam().getColor() + gameManager.getBlueTeam().getName());
                    break;
                    
                default:
                    break;
            }
        }
    }
}
