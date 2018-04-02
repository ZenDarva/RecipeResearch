package com.gmail.zendarva.command;

import com.gmail.zendarva.capabilities.ILearnedRecipes;
import com.gmail.zendarva.capabilities.LearnedRecipeProvider;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

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
        if (!strings[0].equals("clear"))
            return;
        if (strings.length != 2)
            return;
        EntityPlayer victim = iCommandSender.getEntityWorld().getPlayerEntityByName(strings[1]);
        if (victim == null)
            return;

        ILearnedRecipes learned = victim.getCapability(LearnedRecipeProvider.learnedRecipesCapability,null);
        learned.clear();

    }


}
