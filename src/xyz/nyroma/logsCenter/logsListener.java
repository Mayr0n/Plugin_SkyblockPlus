package xyz.nyroma.logsCenter;

import xyz.nyroma.main.speedy;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class logsListener implements Listener {
    private File folder = new File("data/");
    private File logsBlocksFolder = new File("data/blocksLogs/");
    private File blocksBroken = new File("data/blocksLogs/broken.txt");
    private File blocksPlaced = new File("data/blocksLogs/placed.txt");
    private ItemStack lookTool = logsMain.getLookTool();

    public logsListener() throws FileNotFoundException {
        speedy.testFolderExist(folder);
        speedy.testFolderExist(logsBlocksFolder);
        try {
            speedy.testFileExist(blocksBroken);
            speedy.testFileExist(blocksPlaced);
        } catch (FileNotFoundException e){
            throw new FileNotFoundException();
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Block b = e.getBlockPlaced();
        Player p = e.getPlayer();
        Location loc = b.getLocation();
        String material = b.getType().toString();
        String playerName = p.getName();
        String date = speedy.getDate("all");
        if(b.getType().equals(Material.BEDROCK)){
            sendLogs(p, loc);
            e.setCancelled(true);
        } else {
            speedy.writeInFile(blocksPlaced, loc.toString() + " ; " + playerName + " placed : " + material + " (" + date + ")\n", false);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        ItemStack inHand = p.getInventory().getItemInMainHand();
        if(e.getAction().equals(Action.LEFT_CLICK_BLOCK) && inHand.equals(this.lookTool)){
            sendLogs(p, e.getClickedBlock().getLocation());
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Block b = e.getBlock();
        if(!b.getType().equals(Material.BEDROCK)) {
            Location loc = b.getLocation();
            String material = b.getType().toString();
            String playerName = e.getPlayer().getName();
            String date = speedy.getDate("all");

            speedy.writeInFile(blocksBroken, loc.toString() + " ; " + playerName + " broke : " + material + " (" + date + ")\n", false);
        }
        else {
            e.setCancelled(true);
        }
    }

    private void sendLogs(Player p, Location loc){
        List<String> broke = speedy.getFileContent(blocksBroken);
        List<String> placed = speedy.getFileContent(blocksPlaced);
        int max = 10;
        int current = 0;
        if(broke.size() != 0 && placed.size() != 0){
            p.sendMessage(ChatColor.RED + "----- Logs du block -----");
            p.sendMessage(ChatColor.DARK_RED + "<<< Blocks cassés >>>");
            for(String line : broke){
                if(line.contains(loc.toString()) && current < 10){
                    p.sendMessage(ChatColor.DARK_GREEN + getLine(line));
                    current++;
                }
            }
            current = 0;
            p.sendMessage(ChatColor.DARK_RED + "<<< Blocks placés >>>");
            for(String line : placed){
                if(line.contains(loc.toString()) && current < 10){
                    p.sendMessage(ChatColor.GREEN + getLine(line));
                    current++;
                }
            }
            p.sendMessage(ChatColor.RED + "-------------------------");
        } else {
            p.sendMessage(ChatColor.RED + "Il n'y a pas de logs pour ce block...");
        }
    }

    private String getLine(String completeLine){
        StringBuilder sb = new StringBuilder();
        String[] words = completeLine.split(" ");
        for(int i = 2 ; i < words.length ; i++){
            sb.append(words[i]).append(" ");
        }
        return sb.toString();
    }

}
