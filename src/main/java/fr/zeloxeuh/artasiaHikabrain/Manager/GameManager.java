package fr.zeloxeuh.artasiaHikabrain.Manager;

import fr.zeloxeuh.artasiaHikabrain.ArtasiaHikabrain;
import fr.zeloxeuh.artasiaHikabrain.GameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GameManager {
    private ArtasiaHikabrain instance = ArtasiaHikabrain.getInstance();
    private ArrayList<Player> players;
    private GameState state;
    int maxPlayer;

    public GameManager() {
        this.maxPlayer = Bukkit.getMaxPlayers();
        this.players = new ArrayList<>();
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

}