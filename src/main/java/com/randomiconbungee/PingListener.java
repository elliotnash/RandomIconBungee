package com.randomiconbungee;

import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PingListener implements Listener {
    
    List<Favicon> icons = new ArrayList<>();
    
    public void setFavicons(LinkedList<Favicon> favicons){
        icons = favicons;
    }
    
    @EventHandler
    public void onPing(ProxyPingEvent event) {

        if (!icons.isEmpty()) {
            Favicon favicon = randomIcon();
            //set ping favicon to random icon
            event.getResponse().setFavicon(favicon);
        }
    }

    Favicon randomIcon(){
        //create new random and return a random favicon
        Random r = new Random();
        return icons.get(r.nextInt(icons.size()));
    }
    
}
