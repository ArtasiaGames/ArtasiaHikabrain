package fr.zeloxeuh.artasiaHikabrain.Models;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Team {
    private String name;
    private String color;
    private int score;
    ArrayList<Player> players;

    public Team(String name, String color) {
        this.name = name;
        this.color = color;
        this.score = 0;
        this.players = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getScore() {
        return score;
    }
    public void incrementScore() {
        this.score++;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
