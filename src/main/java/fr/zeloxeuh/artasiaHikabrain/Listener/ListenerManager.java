package fr.zeloxeuh.artasiaHikabrain.Listener;

import fr.zeloxeuh.artasiaHikabrain.ArtasiaHikabrain;
import fr.zeloxeuh.artasiaHikabrain.Listener.Player.JoinListener;
import fr.zeloxeuh.artasiaHikabrain.Manager.GameManager;
import org.bukkit.plugin.PluginManager;

public class ListenerManager {
    private ArtasiaHikabrain instance = ArtasiaHikabrain.getInstance();
    private GameManager gameManager = ArtasiaHikabrain.getGameManager();

    public void register(){
        PluginManager pm = instance.getServer().getPluginManager();
        pm.registerEvents(new JoinListener(instance, gameManager), instance);
    }
}
