package fr.zeloxeuh.artasiaHikabrain.Manager;

import fr.zeloxeuh.artasiaHikabrain.ArtasiaHikabrain;
import fr.zeloxeuh.artasiaHikabrain.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GameManager {
    private ArtasiaHikabrain instance = ArtasiaHikabrain.getInstance();
    private ArrayList<Player> players;
    private GameState state;
    private Location redSpawn;
    private Location blueSpawn;
    int maxPlayer;

    public GameManager() {
        this.maxPlayer = Bukkit.getMaxPlayers();
        this.players = new ArrayList<>();

        World arenaWorld = Bukkit.getWorld("arene");
        if (arenaWorld == null) {
            Bukkit.getLogger().warning("[GameManager] Le monde 'arene' n'est pas chargé !");
        } else {
            redSpawn = new Location(arenaWorld, -19.694, 71, 0.502, -89.9f, 0.0f);
            blueSpawn = new Location(arenaWorld, 20.700, 71, 0.508, 90.5f, 0.3f);
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public boolean isState(GameState state) {
        return this.state == state;
    }
    public void setState(GameState state) {
        this.state = state;
    }

    public void teleportPlayersInArena() {
        for (int i = 0; i < getPlayers().size(); i++) {
            Player p = getPlayers().get(i);
            if (i % 2 == 0) {
                p.teleport(redSpawn);
                Bukkit.getLogger().info("Téléportation de " + p.getName() + " au redSpawn");
            } else {
                p.teleport(blueSpawn);
                Bukkit.getLogger().info("Téléportation de " + p.getName() + " au blueSpawn");
            }
        }
    }

}