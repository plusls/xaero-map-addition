package com.plusls.xma.compat.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

public abstract class PlayerCompatApi {

    protected static PlayerCompatApi INSTANCE;

    public static PlayerCompatApi getInstance() {
        return INSTANCE;
    }

    public abstract BlockPos getBlockPos(Player player);

}
