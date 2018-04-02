package com.gmail.zendarva;

import com.mojang.authlib.GameProfile;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;

/**
 * Created by James on 4/1/2018.
 */
public class CommandableFakePlayer extends FakePlayer {
    public CommandableFakePlayer(WorldServer world, GameProfile name) {
        super(world, name);
    }

    @Override
    public boolean canUseCommand(int i, String s) {
        return true;
    }

}
