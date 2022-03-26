package com.plusls.xma.compat;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public class RenderWaypointCompatImpl extends RenderWaypointCompatApi {
    public static void init() {
        INSTANCE = new RenderWaypointCompatImpl();
    }

    @Override
    public void setPositionTexColorShader() {
        RenderSystem.disableAlphaTest();
        RenderSystem.shadeModel(7425);
    }

    @Override
    public void setTexture(ResourceLocation resourceLocation) {
        RenderSystem.bindTexture(Objects.requireNonNull(Minecraft.getInstance().getTextureManager().getTexture(resourceLocation)).getId());
    }

    @Override
    public void bufferBuilderBegin(BufferBuilder bufferBuilder, BufferBuilderBeginMode mode, VertexFormat vertexFormat) {
        bufferBuilder.begin(mode.value, vertexFormat);
    }


}
