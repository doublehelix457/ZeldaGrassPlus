package com.doublehelix;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class ZeldaGrassListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Material[] SwordArr = {
                Material.WOODEN_SWORD,
                Material.STONE_SWORD,
                Material.IRON_SWORD,
                Material.GOLDEN_SWORD,
                Material.DIAMOND_SWORD};
        Material b = event.getBlock().getType(); //get broken block
        Location bLoc =  event.getBlock().getLocation(); //get broken block location
        Player link = event.getPlayer(); //get player
        Material linkSwordr = link.getInventory().getItemInMainHand().getType();
        //get item in hand.
        if(b == Material.GRASS || b == Material.TALL_GRASS ){ //double and normal grass added by doublehelix457
            for(int x = 0;x<SwordArr.length;x++){ //original checker was a bunch of 'ifs'
                if(SwordArr[x] == linkSwordr){
                    RewardPlayer(bLoc, event);
                }
            }
        }

    }

    @EventHandler
    public void onMobKill(EntityDeathEvent e){
        if(ZeldaGrass.inst().getConfig().getBoolean("Drops.Health.Drop-From-Mobs")
                && e.getEntity().getType() != EntityType.PLAYER
                && e.getEntity() instanceof Monster) GiveHealthFromMob(e.getEntity().getKiller());
    }

    public void RewardPlayer(Location broken, BlockBreakEvent event){

        int EmeraldChance = (int)Math.round((100*Math.random()));
        int getChance = ZeldaGrass.inst().getConfig().getInt("Drops.Item-1.Drop-Rate");
        if (EmeraldChance < getChance) {
            Material mat = Material.getMaterial(ZeldaGrass.inst().getConfig().getString("Drops.Item-1.Type"));

            ItemStack emerald = new ItemStack(mat);
            event.getPlayer().getWorld().dropItem(broken,emerald);
            event.getPlayer().sendMessage(ChatColor.GREEN + ZeldaGrass.inst().getConfig().getString("Drops.Item-1.Drop-Message"));
        }
        int HealthChance = (int)Math.round((1000*Math.random()));
        int getHealthChance = ZeldaGrass.inst().getConfig().getInt("Drops.Health.Drop-Rate");

        if (HealthChance < getHealthChance) {
            double maxHealth = event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            double currentHealth = event.getPlayer().getHealth();
            if (currentHealth < maxHealth) {
                double newHealth = (currentHealth % 1 == 0) ? (currentHealth + 2) : (currentHealth + 1);
                if(event.getPlayer().getHealth() < 20) event.getPlayer().setHealth(newHealth);
                event.getPlayer().sendMessage(ChatColor.GREEN + ZeldaGrass.inst().getConfig().getString("Drops.Health.Drop-Message"));
            }
        }
        //the following added by doublehelix457
        int blueRupeeChance = (int)Math.round((1000*Math.random()));
        int getBlueChance = ZeldaGrass.inst().getConfig().getInt("Drops.Item-2.Drop-Rate");
        if(blueRupeeChance < getBlueChance){
            Material mat2 = Material.getMaterial(ZeldaGrass.inst().getConfig().getString("Drops.Item-2.Type"));
            ItemStack diamond = new ItemStack(mat2);
            event.getPlayer().getWorld().dropItem(broken, diamond);
            event.getPlayer().sendMessage(ChatColor.GREEN + ZeldaGrass.inst().getConfig().getString("Drops.Item-2.Drop-Message"));
        }
    }

    public void GiveHealthFromMob(Player p){
        int HealthChance = (int)Math.round((1000*Math.random()));
        int getHealthChance = ZeldaGrass.inst().getConfig().getInt("Drops.Health.Drop-Rate-Mobs");
        if (HealthChance < getHealthChance) {
            double maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            double currentHealth = p.getHealth();
            if (currentHealth < maxHealth) {
                // increase current health by two (since one is only half a heart)
                double newHealth = (currentHealth % 1 == 0) ? (currentHealth + 2) : (currentHealth + 1);
                // set new health 'one' new heart
                if(p.getHealth() < maxHealth) p.setHealth(newHealth);
                p.sendMessage(ChatColor.GREEN + ZeldaGrass.inst().getConfig().getString("Drops.Health.Drop-Mobs-Message"));
            }
        }
    }
}
