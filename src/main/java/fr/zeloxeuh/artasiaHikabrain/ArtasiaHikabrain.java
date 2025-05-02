package fr.zeloxeuh.artasiaHikabrain;

import fr.zeloxeuh.artasiaHikabrain.Listener.ListenerManager;
import fr.zeloxeuh.artasiaHikabrain.Manager.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class ArtasiaHikabrain extends JavaPlugin {

    private static ArtasiaHikabrain instance;
    private PluginDescriptionFile pluginInfos = getDescription();
    public static GameManager gameManager;
    private GameState state;

    @Override
    public void onEnable() {
        System.out.println("§aArtasiaHikabrain v" + pluginInfos.getVersion() + " by Zeloxeuh");
        if (Bukkit.getWorld("arene") == null) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv load arene");
        }
        // Register EVENTS
        new ListenerManager().register();

        gameManager.setState(GameState.WAITING);
    }

    @Override
    public void onLoad() {
        instance = this;
        gameManager = new GameManager();
    }

    public static GameManager getGameManager() {
        return gameManager;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("§aArtasiaHikabrain is disabled");
    }

    public static ArtasiaHikabrain getInstance() {
        return instance;
    }
}
