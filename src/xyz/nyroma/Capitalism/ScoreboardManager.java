package xyz.nyroma.Capitalism;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;
import xyz.nyroma.Capitalism.bank.Bank;
import xyz.nyroma.Capitalism.bank.BankCache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class ScoreboardManager {
    private Server server;
    private Objective money;
    private Objective pourcentage;
    private Objective deaths;
    private Scoreboard moneyScoreboard;
    private Scoreboard miscScoreboard;
    private Scoreboard deathScoreboard;
    private Hashtable<Scoreboard, Objective> test = new Hashtable<>();
    public static Scoreboard current;

    public ScoreboardManager(Server server){
        this.server = server;
    }

    public void build(){
        org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
        //-------------------------------------
        moneyScoreboard = manager.getNewScoreboard();

        this.money = moneyScoreboard.registerNewObjective("Money", "dummy", ChatColor.DARK_RED + "Money");
        this.money.setDisplaySlot(DisplaySlot.SIDEBAR);

        Objective o = moneyScoreboard.registerNewObjective("health", "health", "health");
        o.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        o.setRenderType(RenderType.HEARTS);

        //---------------------------------
        miscScoreboard = manager.getNewScoreboard();

        this.pourcentage = miscScoreboard.registerNewObjective("Pourcentages", "dummy", ChatColor.DARK_RED + "Pour% richesses");
        this.pourcentage.setDisplaySlot(DisplaySlot.SIDEBAR);

        o = miscScoreboard.registerNewObjective("health", "health", "health");
        o.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        o.setRenderType(RenderType.HEARTS);

        //------------------------------
        this.deathScoreboard = manager.getNewScoreboard();

        this.deaths = deathScoreboard.registerNewObjective("IL_EST_MORT_LOL", "dummy", ChatColor.DARK_RED + "IL EST MORT LOL");
        this.deaths.setDisplaySlot(DisplaySlot.SIDEBAR);

        o = deathScoreboard.registerNewObjective("health", "health", "health");
        o.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        o.setRenderType(RenderType.HEARTS);

        //------------------------------

        current = this.moneyScoreboard;
        setScoreboard(this.server);
    }

    public void refresh(){
        int total = 0;
        for(Bank bank : BankCache.getBanks()){
            Score score = this.money.getScore(net.md_5.bungee.api.ChatColor.BLUE + bank.getPlayer());
            score.setScore((int) bank.getAmount());
            total += bank.getAmount();
        }
        for(Bank bank : BankCache.getBanks()){
            Score score = this.pourcentage.getScore(net.md_5.bungee.api.ChatColor.BLUE + bank.getPlayer());
            score.setScore((int) bank.getAmount()*100/total);
        }
        for(OfflinePlayer p : Bukkit.getServer().getWhitelistedPlayers()){
            Score score = this.deaths.getScore(ChatColor.DARK_AQUA + p.getName());
            if(p.getPlayer() != null){
                score.setScore(p.getPlayer().getStatistic(Statistic.DEATHS));
            }
        }
    }

    public void setScoreboard(Server server){
        List<Scoreboard> sc = Arrays.asList(this.moneyScoreboard, this.miscScoreboard, this.deathScoreboard);
        for(Player p : server.getOnlinePlayers()) {
            int ind = sc.indexOf(p.getScoreboard());
            System.out.println("Index : " + ind);
            if(ind == sc.size()-1 || ind == -1){
                ind = 0;
            } else {
                ind++;
            }
            p.setScoreboard(sc.get(ind));
            current = sc.get(ind);
        }
    }

}
