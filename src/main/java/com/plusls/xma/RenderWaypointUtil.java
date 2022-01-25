package com.plusls.xma;

import com.mojang.blaze3d.systems.RenderSystem;
import com.plusls.ommc.feature.highlithtWaypoint.HighlightWaypointResourceLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.Matrix4f;
import net.minecraft.client.util.math.Rotation3;
import net.minecraft.container.PlayerContainer;

import java.util.Objects;

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


    public static void drawHighlightWaypointPTC() {
        RenderSystem.bindTexture(Objects.requireNonNull(MinecraftClient.getInstance().getTextureManager().getTexture(PlayerContainer.BLOCK_ATLAS_TEXTURE)).getGlId());
        RenderSystem.enableBlend();

        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();

        Sprite icon = HighlightWaypointResourceLoader.targetIdSprite;
        Matrix4f matrix4f = Rotation3.identity().getMatrix();

        bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
        bufferBuilder.vertex(matrix4f, -xWidth, -yWidth, 0.0f).texture(icon.getMinU(), icon.getMinV()).color(iconR, iconG, iconB, fade).next();
        bufferBuilder.vertex(matrix4f, -xWidth, yWidth, 0.0f).texture(icon.getMinU(), icon.getMaxV()).color(iconR, iconG, iconB, fade).next();
        bufferBuilder.vertex(matrix4f, xWidth, yWidth, 0.0f).texture(icon.getMaxU(), icon.getMaxV()).color(iconR, iconG, iconB, fade).next();
        bufferBuilder.vertex(matrix4f, xWidth, -yWidth, 0.0f).texture(icon.getMaxU(), icon.getMinV()).color(iconR, iconG, iconB, fade).next();

        Tessellator.getInstance().draw();
    }
}
