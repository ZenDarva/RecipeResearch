package com.gmail.zendarva.item;

import com.gmail.zendarva.RecipeResearch;
import com.gmail.zendarva.domain.Research;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by James on 4/1/2018.
 */
public class ResearchItem extends Item {

    public ResearchItem() {
        setRegistryName("research");
        setUnlocalizedName(RecipeResearch.MODID +".research");
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return super.getItemStackDisplayName(stack) + " " + getMyResearch(stack);
    }

    private String getMyResearch(ItemStack stack){
        if (stack.getTagCompound() == null){
            return "Unknown";
        }
        if (stack.getTagCompound().hasKey("required"))
            return stack.getTagCompound().getString("required");
        return "Unknown";
    }

    public void setResearch(Research research, ItemStack stack){
        NBTTagCompound tag;
        if (stack.getTagCompound() == null){
            tag = new NBTTagCompound();
        }
        else
            tag = stack.getTagCompound();

        tag.setString("required", research.itemToScan);
        stack.setTagCompound(tag);
    }
}
