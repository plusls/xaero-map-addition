package com.plusls.xma.compat;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class RenderWaypointCompatUtilImpl extends RenderWaypointCompatUtilApi {
    private static final Map<BufferBuilderBeginMode, VertexFormat.Mode> bufferBuilderBeginModeMap = new HashMap<>();

    static {
        bufferBuilderBeginModeMap.put(BufferBuilderBeginMode.QUADS, VertexFormat.Mode.QUADS);
    }

    public static void init() {
        INSTANCE = new RenderWaypointCompatUtilImpl();
    }

    @Override
    public void setPositionTexColorShader() {
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
    }

    @Override
    public void setTexture(ResourceLocation resourceLocation) {
        RenderSystem.setShaderTexture(0, resourceLocation);
    }

    @Override
    public void bufferBuilderBegin(BufferBuilder bufferBuilder, BufferBuilderBeginMode mode, VertexFormat vertexFormat) {
        bufferBuilder.begin(bufferBuilderBeginModeMap.get(mode), vertexFormat);
    }
}
