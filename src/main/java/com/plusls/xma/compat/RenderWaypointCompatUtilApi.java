package com.plusls.xma.compat;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.resources.ResourceLocation;

public abstract class RenderWaypointCompatUtilApi {
    protected static RenderWaypointCompatUtilApi INSTANCE;

    public static RenderWaypointCompatUtilApi getInstance() {
        return INSTANCE;
    }

    public abstract void setPositionTexColorShader();

    public abstract void setTexture(ResourceLocation resourceLocation);

    public abstract void bufferBuilderBegin(BufferBuilder bufferBuilder, BufferBuilderBeginMode mode, VertexFormat vertexFormat);

    public enum BufferBuilderBeginMode {
        QUADS(7);

        final int value;

        BufferBuilderBeginMode(int value) {
            this.value = value;
        }
    }

}
