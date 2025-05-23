package fr.zeloxeuh.artasiaHikabrain.listener.player;

import fr.zeloxeuh.artasiaHikabrain.ArtasiaHikabrain;
import fr.zeloxeuh.artasiaHikabrain.GameState;
import fr.zeloxeuh.artasiaHikabrain.manager.GameManager;
import fr.zeloxeuh.artasiaHikabrain.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMovementListener implements Listener {
    private final GameManager gameManager;

    public PlayerMovementListener() {
        this.gameManager = ArtasiaHikabrain.getGameManager();
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        
        // Si le jeu est terminé, on ne traite pas les mouvements
        if (gameManager.isState(GameState.FINISHED)) {
            return;
        }
        
        if (gameManager.isState(GameState.COUNTDOWN)) {
            Utils.freezePlayer(event);
        }

        if (gameManager.isState(GameState.PLAYING)) {
            // Vérifie si le joueur est dans l'équipe bleue et marche sur le lit rouge
            if (gameManager.getBlueTeam().containsPlayer(player) && 
                gameManager.isOnRedBed(player.getLocation())) {
                // Point pour l'équipe bleue
                gameManager.getBlueTeam().addPoint();
                Bukkit.broadcastMessage("§7[§eHikaBrain§7] §9L'équipe Bleue §fmarque un point !");
                gameManager.startNextRound();
            }
            // Vérifie si le joueur est dans l'équipe rouge et marche sur le lit bleu
            else if (gameManager.getRedTeam().containsPlayer(player) &&
                     gameManager.isOnBlueBed(player.getLocation())) {
                // Point pour l'équipe rouge
                gameManager.getRedTeam().addPoint();
                Bukkit.broadcastMessage("§7[§eHikaBrain§7] §cL'équipe Rouge §fmarque un point !");
                gameManager.startNextRound();
            }
        }
    }
}