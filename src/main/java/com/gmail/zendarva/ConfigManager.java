package com.gmail.zendarva;

import com.gmail.zendarva.domain.Research;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import sun.misc.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by James on 3/31/2018.
 */
public class ConfigManager {

    List<Research> researchList = new LinkedList<>();
    Gson gson = new Gson();
    public ConfigManager(File minecraftConfigDir){
        File myConfigDir = new File(minecraftConfigDir,"recipeResearch");
        if (!myConfigDir.exists())
            try {
                RecipeResearch.logger.info("Creating configuration directory and example json");
                setupDefaults(myConfigDir);

            } catch (IOException e) {
                RecipeResearch.logger.error("Unable to create configuration directory. {}", e);
            }
        try {
            readFiles(myConfigDir);
        } catch (IOException e) {
            RecipeResearch.logger.error("Unable to load json config files. {}", e);
        }
    }

    public Optional<Research> getResearch(String lockedBehind){
        return researchList.stream().filter(f->f.itemToScan.toLowerCase().equals(lockedBehind.toLowerCase())).findFirst();
    }


    private void setupDefaults(File myConfigDir) throws IOException {
        Files.createDirectories(myConfigDir.toPath());
        File exampleJson = new File(myConfigDir,"example.json");
        InputStream inStream =ConfigManager.class.getResourceAsStream("/example.json");
        OutputStream outStream = new FileOutputStream(exampleJson);
        byte[] fileContent = IOUtils.readFully(inStream,-1,true);
        outStream.write(fileContent);
        outStream.close();
    }

    private void readFiles(File myConfigDir) throws IOException {
        Files.list(myConfigDir.toPath()).filter(f->f.toString().toLowerCase().endsWith(".json")).forEach(this::readJson);
    }
    private void readJson(Path path){
        File targFile = path.toFile();
        if (targFile.exists() && !targFile.isDirectory()){
            try {
                List<Research> toAdd = gson.fromJson(new FileReader(targFile), new TypeToken<List<Research>>(){}.getType());
                if (toAdd != null)
                    researchList.addAll(toAdd);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
