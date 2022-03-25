package com.plusls.xma.compat.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

public class PlayerCompatUtiImpl extends PlayerCompatUtilApi {
    public static void init() {
        INSTANCE = new PlayerCompatUtiImpl();
    }

    @Override
    public BlockPos getBlockPos(Player player) {
        return player.getCommandSenderBlockPosition();
    }
}
