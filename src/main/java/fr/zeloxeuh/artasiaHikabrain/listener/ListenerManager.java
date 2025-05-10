package fr.zeloxeuh.artasiaHikabrain.listener;

import fr.zeloxeuh.artasiaHikabrain.ArtasiaHikabrain;
import fr.zeloxeuh.artasiaHikabrain.listener.player.InteractListener;
import fr.zeloxeuh.artasiaHikabrain.listener.player.JoinListener;
import fr.zeloxeuh.artasiaHikabrain.listener.player.PlayerMovementListener;
import fr.zeloxeuh.artasiaHikabrain.manager.GameManager;
import org.bukkit.plugin.PluginManager;

public class ListenerManager {
    private final ArtasiaHikabrain instance = ArtasiaHikabrain.getInstance();
    private final GameManager gameManager = ArtasiaHikabrain.getGameManager();

    public void register(){
        PluginManager pm = instance.getServer().getPluginManager();
        pm.registerEvents(new JoinListener(instance, gameManager), instance);
        pm.registerEvents(new InteractListener(gameManager), instance);
        pm.registerEvents(new PlayerMovementListener(), instance);
    }
}
