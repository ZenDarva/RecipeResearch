package com.gmail.zendarva.container;

import com.gmail.zendarva.tile.EntityScanner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Created by James on 4/1/2018.
 */
public class ScannerContainer extends Container {
    private final EntityScanner scanner;

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return true;
    }

    public ScannerContainer(IInventory playerInventory, EntityScanner scanner) {
        this.scanner = scanner;

        addOwnSlots();
        addPlayerSlots(playerInventory);
    }


    private void addPlayerSlots(IInventory playerInventory){
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = 8 + col * 18;
                int y = row * 18 + 84;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }
        for (int row = 0; row < 9; ++row) {
            int x = 8 + row * 18;
            int y = 58 + 84;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }

    private void addOwnSlots() {
        IItemHandler itemHandler = this.scanner.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, (EnumFacing) null);

        this.addSlotToContainer(new SlotItemHandler(itemHandler, 0,10,14));

        this.addSlotToContainer(new SlotOutput(itemHandler, 1,148,14));
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < EntityScanner.SIZE-1) {
                if (!this.mergeItemStack(itemstack1, EntityScanner.SIZE-1, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, EntityScanner.SIZE-1, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }
}
