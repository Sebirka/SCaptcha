package rq.scaptcha.Utils;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import rq.scaptcha.SCaptcha;

import java.io.File;

public class Config {

    private final SCaptcha plugin;
    private File file;
    @Getter
    private FileConfiguration config;

    public Config(SCaptcha plugin){
        this.plugin = plugin;

        loadConfig();
    }



    public void loadConfig(){
        file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()){
            plugin.saveDefaultConfig();
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public void reloadConfig(){
        plugin.saveDefaultConfig();

        try{
            config.load(file);
        } catch (Exception e){
            plugin.getLogger().severe(String.valueOf(e));
        }
    }

}