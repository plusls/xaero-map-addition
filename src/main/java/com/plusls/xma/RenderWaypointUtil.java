package com.plusls.xma;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Matrix4f;
import com.plusls.ommc.feature.highlithtWaypoint.HighlightWaypointResourceLoader;
import com.plusls.xma.compat.RenderWaypointCompatUtilApi;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class RenderWaypointUtil {

    // 红色
    public static float iconR = 1.0f;
    public static float iconG = 0.0f;
    public static float iconB = 0.0f;

    // 渲染的图标的大小
    public static float xWidth = 10.0f;
    public static float yWidth = 10.0f;

    // 透明度
    public static float fade = 1.0f;


    public static void drawHighlightWaypointPTC(Matrix4f matrix4f) {
        RenderWaypointCompatUtilApi.getInstance().setPositionTexColorShader();
        RenderWaypointCompatUtilApi.getInstance().setTexture(TextureAtlas.LOCATION_BLOCKS);
        RenderSystem.enableBlend();
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();

        TextureAtlasSprite icon = HighlightWaypointResourceLoader.targetIdSprite;
        RenderWaypointCompatUtilApi.getInstance()
                .bufferBuilderBegin(bufferBuilder, RenderWaypointCompatUtilApi.BufferBuilderBeginMode.QUADS,
                        DefaultVertexFormat.POSITION_TEX_COLOR);

        bufferBuilder.vertex(matrix4f, -xWidth, -yWidth, 0.0f).uv(icon.getU0(), icon.getV0()).color(iconR, iconG, iconB, fade).endVertex();
        bufferBuilder.vertex(matrix4f, -xWidth, yWidth, 0.0f).uv(icon.getU0(), icon.getV1()).color(iconR, iconG, iconB, fade).endVertex();
        bufferBuilder.vertex(matrix4f, xWidth, yWidth, 0.0f).uv(icon.getU1(), icon.getV1()).color(iconR, iconG, iconB, fade).endVertex();
        bufferBuilder.vertex(matrix4f, xWidth, -yWidth, 0.0f).uv(icon.getU1(), icon.getV0()).color(iconR, iconG, iconB, fade).endVertex();

        Tesselator.getInstance().end();
    }
}
