package com.gmail.zendarva.recipes;

import com.gmail.zendarva.RecipeResearch;
import com.gmail.zendarva.capabilities.ILearnedRecipes;
import com.gmail.zendarva.capabilities.LearnedRecipeProvider;
import com.gmail.zendarva.domain.Research;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Optional;

/**
 * Created by James on 3/31/2018.
 */
public class LockableRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {


    private final IRecipe recipe;
    public final Research lockedBehind;

    public LockableRecipe(IRecipe recipe, Research lockedBehind) {
        this.recipe = recipe;
        this.lockedBehind = lockedBehind;
        this.setRegistryName("reciperesearch", recipe.getRegistryName().getResourcePath());
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipe.getIngredients();
    }

    @Override
    public boolean matches(InventoryCrafting inv, World world) {

        return recipe.matches(inv, world);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            EntityPlayer player = RecipeResearch.proxy.getPlayer();
            if (playerKnows(player))
                return recipe.getCraftingResult(inv);
            return ItemStack.EMPTY;
        }
        else
        {
            MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
            Container container = inv.eventHandler;
            Optional<EntityPlayer> oPlayer = server.getPlayerList().getPlayers().stream().filter(f -> f.openContainer == container).map(f->(EntityPlayer)f).findFirst();
            if (oPlayer.isPresent() && playerKnows(oPlayer.get())) {
                return recipe.getCraftingResult(inv);
            }
            return ItemStack.EMPTY;
        }
    }

    private boolean playerKnows(EntityPlayer player) {
        ILearnedRecipes learnedRecipes = player.getCapability(LearnedRecipeProvider.learnedRecipesCapability, null);
        return learnedRecipes.knowsResearch(lockedBehind);
    }

    @Override
    public boolean canFit(int width, int height) {
        return recipe.canFit(width, height);
    }

    @Override
    public ItemStack getRecipeOutput() {
        return recipe.getRecipeOutput();
    }
}
