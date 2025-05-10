package fr.zeloxeuh.artasiaHikabrain.listener.player;

import fr.zeloxeuh.artasiaHikabrain.ArtasiaHikabrain;
import fr.zeloxeuh.artasiaHikabrain.GameState;
import fr.zeloxeuh.artasiaHikabrain.manager.GameManager;
import fr.zeloxeuh.artasiaHikabrain.timer.AutoStart;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class JoinListener implements Listener {
    private final ArtasiaHikabrain instance;
    private final GameManager gameManager;

    public JoinListener(ArtasiaHikabrain instance, GameManager gameManager) {
        this.instance = instance;
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        Location hub = new Location(Bukkit.getWorld("hikabrain"), 0.476, 65, 0.493, 18.7f, -5.1f);
        player.teleport(hub);
        player.getInventory().clear();
        player.setFoodLevel(20);
        player.setHealth(20);
        player.setLevel(0);

        ItemStack compass = new ItemStack(Material.COMPASS, 1);
        ItemMeta compassItemMeta = compass.getItemMeta();
        assert compassItemMeta != null;
        compassItemMeta.setDisplayName("§6Teams Selection");
        compassItemMeta.setLore(List.of("Right click to select your team."));
        compass.setItemMeta(compassItemMeta);

        player.getInventory().setItem(4, new ItemStack(compass));
        player.updateInventory();

        if (!gameManager.isState(GameState.WAITING)){
            player.setGameMode(GameMode.SPECTATOR);
            player.setAllowFlight(true);
            player.setFlying(true);
            event.setJoinMessage(null);

            player.sendMessage("§cThe game has already started!");
        }

        if (!gameManager.getPlayers().contains(player)) {
            gameManager.addPlayer(player);
        }

        player.setGameMode(GameMode.ADVENTURE);
        event.setJoinMessage("§7[§eHikaBrain§7]§r " + player.getName() + " Joined the game <" + gameManager.getPlayers().size() + "/" + Bukkit.getMaxPlayers() + ">");

        if (gameManager.isState(GameState.WAITING) && gameManager.getPlayers().size() == 2){
            AutoStart start = new AutoStart(gameManager);
            start.runTaskTimer(instance, 0, 20);
            gameManager.setState(GameState.STARTING);
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        
        // Retirer le joueur de son équipe s'il en a une
        if (gameManager.getRedTeam().getPlayers().contains(player)) {
            gameManager.getRedTeam().removePlayer(player);
            Bukkit.getLogger().info("[JoinListener] " + player.getName() + " a été retiré de l'équipe rouge");
        } else if (gameManager.getBlueTeam().getPlayers().contains(player)) {
            gameManager.getBlueTeam().removePlayer(player);
            Bukkit.getLogger().info("[JoinListener] " + player.getName() + " a été retiré de l'équipe bleue");
        }
        
        // Retirer le joueur de la liste des joueurs
        if (gameManager.getPlayers().contains(player)) {
            gameManager.removePlayer(player);
        }
        
        event.setQuitMessage("§7[§eHikaBrain§7]§r " + player.getName() + " left the game <" + gameManager.getPlayers().size() + "/" + Bukkit.getMaxPlayers() + ">");
    }
}
