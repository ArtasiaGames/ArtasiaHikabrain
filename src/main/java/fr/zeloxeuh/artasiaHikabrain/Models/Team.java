package fr.zeloxeuh.artasiaHikabrain.Models;

import org.bukkit.entity.Player;
import java.util.ArrayList;

public class Team {
    private String name;
    private String color;
    private ArrayList<Player> players;
    private int points;

    public Team(String name, String color) {
        this.name = name;
        this.color = color;
        this.players = new ArrayList<>();
        this.points = 0;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        if (!players.contains(player)) {
            players.add(player);
        }
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void addPoint() {
        this.points++;
    }

    public int getPoints() {
        return points;
    }

    public void resetPoints() {
        this.points = 0;
    }

    public boolean containsPlayer(Player player) {
        return players.contains(player);
    }
}