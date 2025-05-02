package fr.zeloxeuh.artasiaHikabrain.Timer;

import fr.zeloxeuh.artasiaHikabrain.GameState;
import fr.zeloxeuh.artasiaHikabrain.Manager.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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

                p.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD, 1));
                p.getInventory().setItem(1, new ItemStack(Material.IRON_PICKAXE, 1));
                p.getInventory().setItem(2, new ItemStack(Material.GOLDEN_APPLE, 64));
                for (int i = 3; i < 8; i++) {
                    p.getInventory().setItem(i, new ItemStack(Material.SANDSTONE, 64));
                }
                p.getInventory().setItemInOffHand(new ItemStack(Material.SANDSTONE, 64));


            }
            gameManager.teleportPlayersInArena();
            Bukkit.broadcastMessage("§7[§eHikaBrain§7]§r STARTING...") ;
            gameManager.setState(GameState.STARTING);
            cancel();
        }
        timer--;
    }
}
