package fr.zeloxeuh.artasiaHikabrain.Manager;

import fr.zeloxeuh.artasiaHikabrain.ArtasiaHikabrain;
import fr.zeloxeuh.artasiaHikabrain.GameState;
import fr.zeloxeuh.artasiaHikabrain.Models.Team;
import fr.zeloxeuh.artasiaHikabrain.Timer.RoundStartTimer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GameManager {
    private ArtasiaHikabrain instance = ArtasiaHikabrain.getInstance();
    private ArrayList<Player> players;
    private GameState state;
    private Location redSpawn;
    private Location blueSpawn;
    
    // Lits (chaque lit occupe 2 blocs)
    private Location redBedHead;    // Tête du lit rouge
    private Location redBedFoot;    // Pied du lit rouge
    private Location blueBedHead;   // Tête du lit bleu
    private Location blueBedFoot;   // Pied du lit bleu
    
    int maxPlayer;
    
    private Team redTeam;
    private Team blueTeam;
    private World arenaWorld;
    private int currentRound = 0;
    private final int MAX_ROUNDS = 9;

    public GameManager() {
        this.maxPlayer = Bukkit.getMaxPlayers();
        this.players = new ArrayList<>();
        this.redTeam = new Team("Rouge", "§c");
        this.blueTeam = new Team("Bleu", "§9");
        this.arenaWorld = Bukkit.getWorld("arene");
        // Initialisation des emplacements
        initializeLocations();
    }
    
    /**
     * Initialise les emplacements de spawn et de lits
     * Cette méthode peut être appelée à nouveau si le monde n'était pas chargé initialement
     */
    private void initializeLocations() {
        if (arenaWorld == null) {
            Bukkit.getLogger().warning("[GameManager] Le monde 'arene' n'est pas chargé ! Les coordonnées ne seront pas initialisées.");
            return;
        }
        
        // Points de spawn des équipes
        redSpawn = new Location(arenaWorld, -19.694, 71, 0.502, -89.9f, 0.0f);
        blueSpawn = new Location(arenaWorld, 20.700, 71, 0.508, 90.5f, 0.3f);
        
        // Position des lits
        // Lit rouge (côté négatif en X)
        redBedHead = new Location(arenaWorld, -19, 65.56250, 0.510);     // Tête du lit
        redBedFoot = new Location(arenaWorld, -18.517, 65.56250, 0.506);     // Pied du lit
        
        // Lit bleu (côté positif en X)
        blueBedHead = new Location(arenaWorld, 20, 65.56250, 0.470);    // Tête du lit
        blueBedFoot = new Location(arenaWorld, 19.442, 65.56250, 0.511);    // Pied du lit
        
        Bukkit.getLogger().info("[GameManager] Emplacements initialisés avec succès pour le monde 'arene'.");
        Bukkit.getLogger().info("[GameManager] Lit rouge : Tête(" + redBedHead.getX() + "," + redBedHead.getY() + "," + redBedHead.getZ() + 
                               "), Pied(" + redBedFoot.getX() + "," + redBedFoot.getY() + "," + redBedFoot.getZ() + ")");
        Bukkit.getLogger().info("[GameManager] Lit bleu : Tête(" + blueBedHead.getX() + "," + blueBedHead.getY() + "," + blueBedHead.getZ() + 
                               "), Pied(" + blueBedFoot.getX() + "," + blueBedFoot.getY() + "," + blueBedFoot.getZ() + ")");
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
    
    /**
     * Téléporte les joueurs dans l'arène selon leur équipe
     * Conserve les choix d'équipe faits dans le lobby
     */
    public void teleportPlayersInArena() {
        // Créer une liste des joueurs sans équipe
        ArrayList<Player> playersWithoutTeam = new ArrayList<>();
        
        // Identifier les joueurs déjà dans une équipe et les téléporter
        for (Player p : getPlayers()) {
            // Si le joueur est dans l'équipe rouge
            if (redTeam.getPlayers().contains(p)) {
                p.teleport(redSpawn);
                p.sendMessage("§7[§eHikaBrain§7] §fVous êtes dans l'équipe " + redTeam.getColor() + redTeam.getName());
            } 
            // Si le joueur est dans l'équipe bleue
            else if (blueTeam.getPlayers().contains(p)) {
                p.teleport(blueSpawn);
                p.sendMessage("§7[§eHikaBrain§7] §fVous êtes dans l'équipe " + blueTeam.getColor() + blueTeam.getName());
            }
            // Sinon, ajouter à la liste des joueurs sans équipe
            else {
                playersWithoutTeam.add(p);
            }
        }
        
        // Répartir équitablement les joueurs sans équipe
        for (int i = 0; i < playersWithoutTeam.size(); i++) {
            Player p = playersWithoutTeam.get(i);
            
            // Équilibrer les équipes (placer dans l'équipe ayant le moins de joueurs)
            if (redTeam.getPlayers().size() <= blueTeam.getPlayers().size()) {
                p.teleport(redSpawn);
                redTeam.addPlayer(p);
                p.sendMessage("§7[§eHikaBrain§7] §fVous êtes dans l'équipe " + redTeam.getColor() + redTeam.getName());
            } else {
                p.teleport(blueSpawn);
                blueTeam.addPlayer(p);
                p.sendMessage("§7[§eHikaBrain§7] §fVous êtes dans l'équipe " + blueTeam.getColor() + blueTeam.getName());
            }
        }
        
        // Au lieu de passer directement à PLAYING, on passe à COUNTDOWN
        setState(GameState.COUNTDOWN);
        
        // Démarrer le compte à rebours
        new RoundStartTimer(this).runTaskTimer(ArtasiaHikabrain.getInstance(), 0L, 20L);
    }
    
    /**
     * Vérifie si le joueur est sur un lit
     * @param playerLoc La position du joueur
     * @param bedHead Position de la tête du lit
     * @param bedFoot Position du pied du lit
     * @return true si le joueur est sur le lit
     */
    public boolean isOnBed(Location playerLoc, Location bedHead, Location bedFoot) {
        if (playerLoc == null || bedHead == null || bedFoot == null || 
            playerLoc.getWorld() == null || bedHead.getWorld() == null) {
            return false;
        }
        
        // Vérifier si le joueur est sur la tête ou le pied du lit
        boolean onHeadPart = playerLoc.getWorld().equals(bedHead.getWorld()) &&
                Math.abs(playerLoc.getBlockX() - bedHead.getBlockX()) <= 0 &&
                Math.abs(playerLoc.getBlockY() - bedHead.getBlockY()) <= 1 &&
                Math.abs(playerLoc.getBlockZ() - bedHead.getBlockZ()) <= 0;
                
        boolean onFootPart = playerLoc.getWorld().equals(bedFoot.getWorld()) &&
                Math.abs(playerLoc.getBlockX() - bedFoot.getBlockX()) <= 0 &&
                Math.abs(playerLoc.getBlockY() - bedFoot.getBlockY()) <= 1 &&
                Math.abs(playerLoc.getBlockZ() - bedFoot.getBlockZ()) <= 0;
                
        return onHeadPart || onFootPart;
    }
    
    /**
     * Vérifie si le joueur est sur le lit rouge
     * @param playerLoc La position du joueur
     * @return true si le joueur est sur le lit rouge
     */
    public boolean isOnRedBed(Location playerLoc) {
        return isOnBed(playerLoc, redBedHead, redBedFoot);
    }
    
    /**
     * Vérifie si le joueur est sur le lit bleu
     * @param playerLoc La position du joueur
     * @return true si le joueur est sur le lit bleu
     */
    public boolean isOnBlueBed(Location playerLoc) {
        return isOnBed(playerLoc, blueBedHead, blueBedFoot);
    }
    
    // Getters pour les spawns
    public Location getRedSpawn() {
        return redSpawn;
    }
    
    public Location getBlueSpawn() {
        return blueSpawn;
    }
    
    // Getters pour les lits
    public Location getRedBedHead() {
        return redBedHead;
    }
    
    public Location getRedBedFoot() {
        return redBedFoot;
    }
    
    public Location getBlueBedHead() {
        return blueBedHead;
    }
    
    public Location getBlueBedFoot() {
        return blueBedFoot;
    }
    
    // Getters pour les équipes
    public Team getRedTeam() {
        return redTeam;
    }
    
    public Team getBlueTeam() {
        return blueTeam;
    }
    
    // Obtenir l'équipe d'un joueur
    public Team getTeamOfPlayer(Player player) {
        if (redTeam.getPlayers().contains(player)) {
            return redTeam;
        } else if (blueTeam.getPlayers().contains(player)) {
            return blueTeam;
        }
        return null;
    }
    
    /**
     * Définit l'équipe d'un joueur
     * @param player Le joueur
     * @param team L'équipe à laquelle ajouter le joueur (null pour retirer le joueur de son équipe actuelle)
     */
    public void setPlayerTeam(Player player, Team team) {
        // Retirer le joueur de son équipe actuelle
        Team currentTeam = getTeamOfPlayer(player);
        if (currentTeam != null) {
            currentTeam.removePlayer(player);
        }
        
        // Ajouter le joueur à la nouvelle équipe si spécifiée
        if (team != null) {
            team.addPlayer(player);
        }
    }

    public void startNextRound() {
        for (Player p : getPlayers()) {
            // Téléporter les joueurs à leurs positions de spawn respectives
            if (redTeam.getPlayers().contains(p)) {
                p.teleport(redSpawn);
            } else if (blueTeam.getPlayers().contains(p)) {
                p.teleport(blueSpawn);
            }
            
            // Réinitialiser l'inventaire
            p.getInventory().clear();
            p.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD, 1));
            p.getInventory().setItem(1, new ItemStack(Material.IRON_PICKAXE, 1));
            p.getInventory().setItem(2, new ItemStack(Material.GOLDEN_APPLE, 64));
            for (int i = 3; i < 8; i++) {
                p.getInventory().setItem(i, new ItemStack(Material.SANDSTONE, 64));
            }
            p.getInventory().setItemInOffHand(new ItemStack(Material.SANDSTONE, 64));
        }

        // Démarrer le compte à rebours
        setState(GameState.COUNTDOWN);
        new RoundStartTimer(this).runTaskTimer(ArtasiaHikabrain.getInstance(), 0L, 20L);
    }
}