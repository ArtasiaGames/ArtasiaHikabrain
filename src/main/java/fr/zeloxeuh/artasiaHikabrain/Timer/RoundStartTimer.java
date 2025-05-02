package fr.zeloxeuh.artasiaHikabrain.Timer;

import fr.zeloxeuh.artasiaHikabrain.GameState;
import fr.zeloxeuh.artasiaHikabrain.Manager.GameManager;
import org.bukkit.scheduler.BukkitRunnable;

public class RoundStartTimer extends BukkitRunnable {
    private int timer = 3;
    private GameManager gameManager;

    public RoundStartTimer(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        if (timer > 0) {
            // Afficher le décompte à tous les joueurs
            for (var player : gameManager.getPlayers()) {
                player.sendTitle("§e" + timer, "", 0, 20, 0);
                player.playSound(player.getLocation(), "block.note_block.pling", 1.0f, 1.0f);
            }
        } else if (timer == 0) {
            // Afficher "YEAH" et commencer le round
            for (var player : gameManager.getPlayers()) {
                player.sendTitle("§a§lYEAH!", "", 0, 20, 10);
                player.playSound(player.getLocation(), "entity.player.levelup", 1.0f, 1.0f);
            }
            gameManager.setState(GameState.PLAYING);
            cancel();
            return;
        }
        
        timer--;
    }
}