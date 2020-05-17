package xyz.nyroma.Capitalism.bourse;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class TradeManager implements InventoryHolder {
    private ItemStack is;

    public TradeManager(ItemStack is){
        this.is = is;
    }

    @Override
    public Inventory getInventory() {
        Inventory inv = Bukkit.createInventory(this, 9, "Trade");
        inv.setItem(4, is);
        return inv;
    }
}
