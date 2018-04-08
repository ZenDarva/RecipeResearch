package com.gmail.zendarva.capabilities;

import com.gmail.zendarva.RecipeResearch;
import com.gmail.zendarva.domain.Research;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by James on 4/1/2018.
 */
public class LearnedRecipesStorage implements Capability.IStorage<ILearnedRecipes> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<ILearnedRecipes> capability, ILearnedRecipes iLearnedRecipes, EnumFacing enumFacing) {
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagList list = new NBTTagList();

        iLearnedRecipes.learnedResearches().stream().map(NBTTagString::new).forEach(list::appendTag);
        tag.setTag("learned",list);
        return tag;
    }

    @Override
    public void readNBT(Capability<ILearnedRecipes> capability, ILearnedRecipes iLearnedRecipes, EnumFacing enumFacing, NBTBase nbtBase) {
        if (!(nbtBase instanceof NBTTagCompound))
            return;
        NBTTagCompound compound = (NBTTagCompound) nbtBase;
        NBTTagList list = (NBTTagList) compound.getTag("learned");

        Iterator<NBTBase> itr = list.iterator();

        while(itr.hasNext()){
            NBTBase base = itr.next();
            if (base instanceof NBTTagString){
                NBTTagString str = (NBTTagString) base;
                Optional<Research> research = RecipeResearch.configManager.getResearch(str.getString());
                research.ifPresent(iLearnedRecipes::learnResearch);
            }
        }
    }
}
