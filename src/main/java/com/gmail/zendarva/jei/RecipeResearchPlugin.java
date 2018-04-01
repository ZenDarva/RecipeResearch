package com.gmail.zendarva.jei;

import com.gmail.zendarva.RecipeManager;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by James on 4/1/2018.
 */
@JEIPlugin
public class RecipeResearchPlugin implements IModPlugin {
    @Override
    public void register(IModRegistry registry) {
        List<LockableWrapper> wrappedLockables = RecipeManager.lockedRecipes.stream().map(f->new LockableWrapper(f)).collect(Collectors.toList());
        registry.addRecipes(wrappedLockables, VanillaRecipeCategoryUid.CRAFTING);
    }


}
