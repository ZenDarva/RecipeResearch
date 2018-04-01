package com.gmail.zendarva.handlers;

import com.gmail.zendarva.container.ScannerContainer;
import com.gmail.zendarva.container.ScannerGui;
import com.gmail.zendarva.tile.EntityScanner;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

/**
 * Created by James on 4/1/2018.
 */
public class GuiHandler implements IGuiHandler{
    @Nullable
    @Override
    public Object getServerGuiElement(int i, EntityPlayer entityPlayer, World world, int i1, int i2, int i3) {
        BlockPos pos = new BlockPos(i1,i2,i3);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof EntityScanner){
            return new ScannerContainer(entityPlayer.inventory, (EntityScanner) te);
        }
        return null;

    }

    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int i, EntityPlayer entityPlayer, World world, int i1, int i2, int i3) {
        BlockPos pos = new BlockPos(i1,i2,i3);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof EntityScanner){
            return new ScannerGui(new ScannerContainer(entityPlayer.inventory, (EntityScanner) te), (EntityScanner)te);
        }
        return null;
    }
}
