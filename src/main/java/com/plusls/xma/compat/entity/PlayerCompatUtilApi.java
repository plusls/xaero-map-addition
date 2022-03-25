package com.plusls.xma.compat.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

public abstract class PlayerCompatUtilApi {

    protected static PlayerCompatUtilApi INSTANCE;

    public static PlayerCompatUtilApi getInstance() {
        return INSTANCE;
    }

    public abstract BlockPos getBlockPos(Player player);

}
