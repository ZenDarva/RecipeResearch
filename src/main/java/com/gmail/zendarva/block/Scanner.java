package com.gmail.zendarva.block;

import com.gmail.zendarva.RecipeResearch;
import com.gmail.zendarva.tile.EntityScanner;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Created by James on 4/1/2018.
 */
public class Scanner extends Block implements ITileEntityProvider {

    public Scanner() {
        super(Material.IRON);
        String name = "scanner";
        this.setRegistryName(name);
        this.setUnlocalizedName(RecipeResearch.MODID+"."+name);
        this.setCreativeTab(CreativeTabs.MISC);
        this.needsRandomTick=true;
        GameRegistry.registerTileEntity(EntityScanner.class, name + "_te");
    }


    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new EntityScanner();
    }



    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock((Block) this), 0, new ModelResourceLocation(((Block)this).getRegistryName(), "inventory"));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote){
            return true;
        }

        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof EntityScanner){
            playerIn.openGui(RecipeResearch.instance, 1, worldIn, pos.getX(),pos.getY(),pos.getZ() );
            return true;
        }
        return false;
    }
}
