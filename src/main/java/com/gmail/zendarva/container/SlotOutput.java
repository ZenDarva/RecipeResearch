package com.gmail.zendarva.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * Created by James on 4/1/2018.
 */
public class SlotOutput extends SlotItemHandler {
    public SlotOutput(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return false;
    }


    @Override
    public boolean canTakeStack(EntityPlayer playerIn) {
        return true;
    }


}
