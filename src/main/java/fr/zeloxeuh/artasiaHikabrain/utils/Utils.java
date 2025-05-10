package fr.zeloxeuh.artasiaHikabrain.utils;

import org.bukkit.event.player.PlayerMoveEvent;

public class Utils {
    public static void freezePlayer(PlayerMoveEvent event) {
        if (event.getFrom().getX() != event.getTo().getX() ||
                event.getFrom().getY() != event.getTo().getY() ||
                event.getFrom().getZ() != event.getTo().getZ()) {
            event.setCancelled(true);
        }
    }
}
