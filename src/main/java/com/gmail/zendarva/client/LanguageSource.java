package com.gmail.zendarva.client;

import com.gmail.zendarva.RecipeManager;
import com.gmail.zendarva.RecipeResearch;
import com.gmail.zendarva.domain.LanguageData;
import com.gmail.zendarva.domain.Research;
import com.google.common.collect.ImmutableSet;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by James on 4/7/2018.
 */
public class LanguageSource implements IResourcePack, IResourceManagerReloadListener {

    private HashMap<String, StringBuilder> languageSources;


    @Override
    public InputStream getInputStream(ResourceLocation resourceLocation) throws IOException {
        String temp = resourceLocation.getResourcePath().substring(resourceLocation.getResourcePath().indexOf("/")+1);
        String index = temp.substring(0,temp.indexOf("."));
        if (languageSources != null && languageSources.containsKey(index)){
            return new ByteArrayInputStream(languageSources.get(index).toString().getBytes());
        }
        return new ByteArrayInputStream("".getBytes());
    }

    @Override
    public boolean resourceExists(ResourceLocation resourceLocation) {
        return resourceLocation.getResourcePath().endsWith(".lang");
    }

    @Override
    public Set<String> getResourceDomains() {
        return ImmutableSet.of("reciperesearch");
    }

    @Nullable
    @Override
    public <T extends IMetadataSection> T getPackMetadata(MetadataSerializer metadataSerializer, String s) throws IOException {
        return null;
    }

    @Override
    public BufferedImage getPackImage() throws IOException {
        return null;
    }

    @Override
    public String getPackName() {
        return "test";
    }

    @Override
    public void onResourceManagerReload(IResourceManager iResourceManager) {
        languageSources = new HashMap<>();
        RecipeResearch.configManager.researchList.forEach(this::plunderResearch);
        System.out.println("Wow.");

    }

    private void plunderResearch(Research research) {
        if (research.displayName == null)
            return;
        for (LanguageData languageData : research.displayName) {
            if (!languageSources.containsKey(languageData.language))
                languageSources.put(languageData.language,new StringBuilder());
            languageSources.get(languageData.language).append(String.format("%s=%s\n\r",research.researchName,languageData.displayName));
            languageSources.get(languageData.language).append(String.format("%s=%s\n\r",research.getDiscoveryString(),languageData.discoveryString));
        }
    }
}
