package org.kylan1940.sambungkata.word;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class WordManager {

    private final JavaPlugin plugin;

    private final Set<String> words = new HashSet<>();

    public WordManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void load(){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(plugin.getResource("words.txt")));
            String line;
            while((line = reader.readLine()) != null){
                words.add(line.trim().toLowerCase());
            }
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean exists(String word){
        return words.contains(word.toLowerCase());
    }

    public int size(){
        return words.size();
    }

}