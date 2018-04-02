package com.gmail.zendarva.command;

import com.gmail.zendarva.capabilities.ILearnedRecipes;
import com.gmail.zendarva.capabilities.LearnedRecipeProvider;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

/**
 * Created by James on 4/1/2018.
 */
public class LearnedCommand extends CommandBase {
    @Override
    public String getName() {
        return "learned";
    }

    @Override
    public String getUsage(ICommandSender iCommandSender) {
        return "/learned clear <player>";
    }

    @Override
    public void execute(MinecraftServer minecraftServer, ICommandSender iCommandSender, String[] strings) throws CommandException {
        if (!strings[0].equals("clear")) {
            iCommandSender.sendMessage(new TextComponentString("Syntax: /learned clear <player name>"));;
            return;
        }
        if (strings.length != 2) {
            iCommandSender.sendMessage(new TextComponentString("Syntax: /learned clear <player name>"));;
            return;
        }
        EntityPlayer victim = iCommandSender.getEntityWorld().getPlayerEntityByName(strings[1]);
        if (victim == null) {
            iCommandSender.sendMessage(new TextComponentString("Player not found."));;
            return;
        }

        ILearnedRecipes learned = victim.getCapability(LearnedRecipeProvider.learnedRecipesCapability,null);
        learned.clear();

        if (iCommandSender.getName().equals(victim.getName())){
            iCommandSender.sendMessage(new TextComponentString("Your learned recipes have been cleared."));;
        }
        else{
            iCommandSender.sendMessage(new TextComponentString("Their learned recipes have been cleared."));;
            victim.sendMessage(new TextComponentString(iCommandSender.getDisplayName() + " has cleared your learned recipes."));;
        }

    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        if (index == 1)
                return true;
        return false;
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
         return sender.canUseCommand(2,"");
    }
}
