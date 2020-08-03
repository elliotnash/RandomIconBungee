package com.randomiconbungee;

import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;

public final class Main extends Plugin {

    private static Main instance;
    PingListener pingListener = new PingListener();
    Configuration configuration;

    @Override
    public void onEnable() {

        //set instance
        instance = this;

        //load config

        //create plugin folder
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        File file = new File(getDataFolder(), "config.yml");

        //copies the default config if doesn't exist
        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //loads configuration
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
            getLogger().severe("Configuration could not be loaded, RandomIconBungee is disabling");
            return;
        }

        //create serverIcons folder if not exists
        File newFolder = new File(getDataFolder() + File.separator + "serverIcons");
        if(!newFolder.exists()){
            newFolder.mkdir();
        }

        //set favicon list in PingListener
        pingListener.setFavicons(getFavicons());

        //register ping listener
        getProxy().getPluginManager().registerListener(this, pingListener);
        //register command listener
        getProxy().getPluginManager().registerCommand(this, new CommandListener());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getInstance(){
        return instance;
    }

    public LinkedList<Favicon> getFavicons(){
        File dir = new File(getDataFolder() + File.separator + "serverIcons");
        File[] directoryListing = dir.listFiles();
        LinkedList<Favicon> favicons = new LinkedList<>();
        if (directoryListing != null) {
            for (File f : directoryListing) {
                if(f.getName().contains(".png")){
                    try {
                        BufferedImage i = ImageIO.read(new File(dir + File.separator + f.getName()));
                        int width = i.getWidth();
                        int height = i.getHeight();
                        if(width == 64 && height == 64){
                            favicons.add(Favicon.create(i));
                        }else{
                            if(!f.isHidden()){
                                getLogger().info("One of your server icons is not 64x64! It will not be used!");
                            }
                        }
                    }catch(IOException e){
                        getLogger().info("Something bad occured! Please report this to Ethemoose (RandomIconBungee dev)");
                        getLogger().info("Include this, and the error below with the report **Checking info");
                        e.printStackTrace();
                    }
                }else{
                    if(!f.isHidden()){
                        getLogger().info("One of your server icons does not end in .png! It will not be used!");
                    }
                }
            }
        }else{
            getLogger().info("The serverIcons directory is missing! Trying to create one now..");
            dir.mkdir();
        }
        return favicons;
    }
}
