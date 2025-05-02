package fr.zeloxeuh.artasiaHikabrain.Timer;

import fr.zeloxeuh.artasiaHikabrain.GameState;
import fr.zeloxeuh.artasiaHikabrain.Manager.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoStart extends BukkitRunnable {
    private int timer = 10;
    private GameManager gameManager;
    private Player player;

    public AutoStart(GameManager gameManager, Player player) {
        this.gameManager = gameManager;
        this.player = player;
    }

    @Override
    public void run() {
        if (gameManager.getPlayers().size() < 2) {
            gameManager.setState(GameState.WAITING);
            for (Player p : gameManager.getPlayers()){
                p.setLevel(0);
            }
            cancel();
            return;
        }
        for (Player p : gameManager.getPlayers()){
            p.setLevel(timer);
        }

        if (timer == 10 || timer == 5 || timer == 4 || timer == 3 || timer == 2 || timer == 1){
            for (Player p : gameManager.getPlayers()){
                p.sendTitle(String.valueOf(timer), null, 2, 18, 8);
            }
            Bukkit.broadcastMessage("Starting game in §e" + timer + "s");
        }

        if (timer == 0) {
            for (Player p : gameManager.getPlayers()){
                p.sendTitle("STARTING...", "§eGood Luck", 2,18, 8);
                p.setLevel(0);
                p.getInventory().clear();
                p.updateInventory();
            }
            gameManager.teleportPlayersInArena();
            Bukkit.broadcastMessage("§7[§eHikaBrain§7]§r STARTING...") ;
            gameManager.setState(GameState.STARTING);
            cancel();
        }
        timer--;
    }
}
