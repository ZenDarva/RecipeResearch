package com.gmail.zendarva.handlers;

import com.gmail.zendarva.capabilities.ILearnedRecipes;
import com.gmail.zendarva.capabilities.LearnedRecipeProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by James on 4/1/2018.
 */
public class CapabilityHandler {
    public static final ResourceLocation islandCap = new ResourceLocation("reciperesearch", "learnedRecipes");

    @SubscribeEvent
    public void AttachEntity(AttachCapabilitiesEvent<Entity> event) {
        if ((event.getObject() instanceof EntityPlayer)) {
            event.addCapability(islandCap, new LearnedRecipeProvider());
        }
    }

    @SubscribeEvent
    public void OnPlayerClone(PlayerEvent.Clone event)
    {
        EntityPlayer player = event.getEntityPlayer();
        ILearnedRecipes cap = (ILearnedRecipes) player.getCapability(LearnedRecipeProvider.learnedRecipesCapability, null);
        ILearnedRecipes oldCap = (ILearnedRecipes) event.getOriginal().getCapability(LearnedRecipeProvider.learnedRecipesCapability, null);
        cap.set(oldCap);
    }
}
