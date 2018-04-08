package com.gmail.zendarva.item;

import com.gmail.zendarva.ConfigManager;
import com.gmail.zendarva.RecipeManager;
import com.gmail.zendarva.RecipeResearch;
import com.gmail.zendarva.capabilities.ILearnedRecipes;
import com.gmail.zendarva.capabilities.LearnedRecipeProvider;
import com.gmail.zendarva.domain.Research;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Optional;

/**
 * Created by James on 4/1/2018.
 */
public class ResearchItem extends Item {

    public ResearchItem() {
        setRegistryName("research");
        setUnlocalizedName(RecipeResearch.MODID +".research");
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0,
                new ModelResourceLocation(getRegistryName(), "inventory"));

    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        Research research = RecipeManager.getResearchByName(getMyResearch(stack));
        if (research == null){
            return "Damaged Research";
        }
        //TODO: Replace with client side translator.  Rather stupid.
        return I18n.translateToLocal(research.researchName).trim();
    }


    private String getMyResearch(ItemStack stack){
        if (stack.getTagCompound() == null){
            return "Unknown";
        }
        if (stack.getTagCompound().hasKey("research")){
            return stack.getTagCompound().getString("research");
        }

        return "Unknown";
    }

    private String getMyResearchItemName(ItemStack stack) {
        ItemStack targ = RecipeManager.getStackForName(getMyResearch(stack));
        return targ.getDisplayName();
    }

    public void setResearch(Research research, ItemStack stack){
        NBTTagCompound tag;
        if (stack.getTagCompound() == null){
            tag = new NBTTagCompound();
        }
        else
            tag = stack.getTagCompound();

        tag.setString("research", research.researchName);
        stack.setTagCompound(tag);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote)
            return EnumActionResult.SUCCESS;
        ItemStack stack = player.getHeldItem(hand);
        if (learnResearch(stack,player)){
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (worldIn.isRemote)
            return ActionResult.newResult(EnumActionResult.PASS, stack);
        if (learnResearch(stack,player)){
            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
        }
        return ActionResult.newResult(EnumActionResult.FAIL, stack);
    }

    private boolean learnResearch(ItemStack stack, EntityPlayer player){
        Research research = RecipeManager.getResearchByName(getMyResearch(stack));
        if (research != null) {
            ILearnedRecipes learnedRecipes = player.getCapability(LearnedRecipeProvider.learnedRecipesCapability, null);

            if (learnedRecipes.knowsResearch(research)){
                player.sendMessage(new TextComponentString("You already know about that."));
                return false;
            }
            learnedRecipes.learnResearch(research);
            stack.shrink(1);
            player.sendMessage(new TextComponentTranslation(research.getDiscoveryString()));
            if (research.commandToRun != null && !player.world.isRemote){
                String modifiedCommandToRun = research.commandToRun.replace("%player%", player.getName());
                player.world.getMinecraftServer().commandManager.executeCommand(RecipeResearch.fakePlayer,modifiedCommandToRun);
            }
            return true;
        }
        player.sendMessage(new TextComponentString("Something doesn't seem right."));
        return false;
    }
}
