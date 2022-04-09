package com.plusls.xma.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundChatPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientboundChatPacket.class)
public interface AccessorClientboundChatPacket {
    @Accessor
    @Mutable
    void setMessage(Component var1);
}