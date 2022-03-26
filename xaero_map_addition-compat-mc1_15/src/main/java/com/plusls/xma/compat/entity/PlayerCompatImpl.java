package com.plusls.xma.compat.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

public class PlayerCompatImpl extends PlayerCompatApi {
    public static void init() {
        INSTANCE = new PlayerCompatImpl();
    }

    @Override
    public BlockPos getBlockPos(Player player) {
        return player.getCommandSenderBlockPosition();
    }
}
