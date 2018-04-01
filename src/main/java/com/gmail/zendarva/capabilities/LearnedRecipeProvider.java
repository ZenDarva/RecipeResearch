package com.gmail.zendarva.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by James on 4/1/2018.
 */
public class LearnedRecipeProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(ILearnedRecipes.class)
    public static final Capability<ILearnedRecipes> learnedRecipesCapability = null;
    private ILearnedRecipes instance = (ILearnedRecipes) learnedRecipesCapability.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing enumFacing) {
        return capability == learnedRecipesCapability;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing enumFacing) {
        return (T)(capability == learnedRecipesCapability ? learnedRecipesCapability.cast(this.instance) : null);
    }


    @Override
    public NBTBase serializeNBT() {
        return learnedRecipesCapability.getStorage().writeNBT(learnedRecipesCapability,this.instance,null);
    }

    @Override
    public void deserializeNBT(NBTBase nbtBase) {
        learnedRecipesCapability.getStorage().readNBT(learnedRecipesCapability,this.instance,null,nbtBase);
    }
}
