package com.plusls.xma.mixin;

import org.spongepowered.asm.mixin.Mixin;

//#if MC > 11802
import net.minecraft.client.Minecraft;
//#else
//$$ import net.minecraft.network.chat.Component;
//$$ import net.minecraft.network.protocol.game.ClientboundChatPacket;
//$$ import org.spongepowered.asm.mixin.Mutable;
//$$ import org.spongepowered.asm.mixin.gen.Accessor;
//#endif

//#if MC > 11802
@Mixin(Minecraft.class)
//#else
//$$ @Mixin(ClientboundChatPacket.class)
//#endif
public interface AccessorClientboundChatPacket {
    //#if MC <= 11802
    //$$ @Accessor
    //$$ @Mutable
    //$$ void setMessage(Component var1);
    //#endif
}