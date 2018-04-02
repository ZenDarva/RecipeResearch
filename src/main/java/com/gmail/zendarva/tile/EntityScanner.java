package com.gmail.zendarva.tile;

import com.gmail.zendarva.ModBlocks;
import com.gmail.zendarva.RecipeManager;
import com.gmail.zendarva.domain.Research;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

/**
 * Created by James on 4/1/2018.
 */
public class EntityScanner extends TileEntity implements ITickable {
    public static final int SIZE = 2;
    public int progress = 0;
    public int maxProgress = 1000;
    public Research currentResearch;
    public ItemStack workingOn = ItemStack.EMPTY;

    ItemStackHandler inventory = new ItemStackHandler(SIZE){
        @Override
        protected void onContentsChanged(int slot) {
            EntityScanner.this.markDirty();
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("items")) {
            inventory.deserializeNBT((NBTTagCompound) compound.getTag("items"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("items", inventory.serializeNBT());
        return compound;
    }

    @Override
    public void update() {
        if (!workingOn.isEmpty() && progress < maxProgress){
            progress++;
            return;
        }
        if (progress >= maxProgress && !workingOn.isEmpty() && inventory.getStackInSlot(1).isEmpty()){
            progress = 0;
            maxProgress=0;
            if (this.getWorld().isRemote) {
                workingOn=ItemStack.EMPTY;
                return;
            }
            inventory.setStackInSlot(1,getResearchOutput());
            workingOn=ItemStack.EMPTY;
            return;
        }
        if (workingOn.isEmpty() && !inventory.getStackInSlot(0).isEmpty()){
            workingOn = inventory.getStackInSlot(0).copy();
            inventory.setStackInSlot(0, ItemStack.EMPTY);
            //This should be derived from Research, but that can't be done yet.
            Research research = RecipeManager.getResearchForItem(workingOn);
            if (research != null)
                maxProgress = research.requiredPower;
            else
                maxProgress = 1000;
        }
    }

    private ItemStack getResearchOutput() {
        Research research = RecipeManager.getResearchForItem(workingOn);
        if (research == null){
            return ItemStack.EMPTY;
        }
        ItemStack stack = new ItemStack(ModBlocks.researchItem);
        ModBlocks.researchItem.setResearch(research,stack);
        return stack;
    }
}
