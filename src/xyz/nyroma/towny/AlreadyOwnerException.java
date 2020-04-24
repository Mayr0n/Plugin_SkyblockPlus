package xyz.nyroma.towny;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AlreadyOwnerException extends Exception {
    public AlreadyOwnerException(){
        super("Tu es déjà owner d'une ville, tu ne peux pas en créer une deuxième !");
    }
}
