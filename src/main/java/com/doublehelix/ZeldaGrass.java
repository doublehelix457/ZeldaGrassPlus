package com.doublehelix;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;

import java.io.File;
import java.util.logging.Logger;

/***
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.*/
//Errored (Original Author) only had one Listner class and that was it. Added this class for future useage and modification.

@Plugin(name="ZeldaGrassPlus", version = "2.1-SNAPSHOT")
@ApiVersion(ApiVersion.Target.v1_15)
@Description("Add grass like functions of zelda to drop rupees and other goodies.")
@Commands(@org.bukkit.plugin.java.annotation.command.Command(name="zg"))
@Permission(name="zg.reload", defaultValue = PermissionDefault.OP)
public class ZeldaGrass extends JavaPlugin {

    Logger logger = Logger.getLogger("ZeldaGrass+");
    private static ZeldaGrass instance;

    public void onDisable(){
        PluginDescriptionFile pdfFile = this.getDescription();
        this.logger.info(pdfFile.getName() + " Has Been Disabled!");
        saveConfig();
    }

    public void onEnable() {
        instance = this;
        PluginDescriptionFile pdfFile = this.getDescription();
        this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " Has Been Enabled!");
        createConfig();
        Bukkit.getPluginManager().registerEvents(new ZeldaGrassListener(), this);
    }

    private boolean createConfig() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                getLogger().info("config.yml not found, creating!");
                this.getConfig().options().copyDefaults(true);
            } else {
                getLogger().info("Config File Found, loading!");

            }
            saveConfig();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(commandLabel.equalsIgnoreCase("zg")){
            if(sender.getName() == "console" || sender.isOp() || sender.hasPermission("*") || sender.hasPermission("zg.reload")){
                this.reloadConfig();
                this.logger.info(this.getDescription().getName() + " Reloaded Config.yml");
                if(sender instanceof Player){
                    sender.sendMessage(ChatColor.GREEN + this.getDescription().getName() + " Config Sucessfully Reloaded!");
                }
            }
        }
        return false;
    }

    public static ZeldaGrass inst() { return instance; }
}
