package com.plusls.xma;

import com.mojang.blaze3d.systems.RenderSystem;
import com.plusls.ommc.feature.highlithtWaypoint.HighlightWaypointResourceLoader;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.math.Matrix4f;

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


    public static void drawHighlightWaypointPTC(MatrixStack matrixStack) {
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);

        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();

        Sprite icon = HighlightWaypointResourceLoader.targetIdSprite;
        Matrix4f matrix4f = matrixStack.peek().getModel();

        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        bufferBuilder.vertex(matrix4f, -xWidth, -yWidth, 0.0f).texture(icon.getMinU(), icon.getMinV()).color(iconR, iconG, iconB, fade).next();
        bufferBuilder.vertex(matrix4f, -xWidth, yWidth, 0.0f).texture(icon.getMinU(), icon.getMaxV()).color(iconR, iconG, iconB, fade).next();
        bufferBuilder.vertex(matrix4f, xWidth, yWidth, 0.0f).texture(icon.getMaxU(), icon.getMaxV()).color(iconR, iconG, iconB, fade).next();
        bufferBuilder.vertex(matrix4f, xWidth, -yWidth, 0.0f).texture(icon.getMaxU(), icon.getMinV()).color(iconR, iconG, iconB, fade).next();

        Tessellator.getInstance().draw();
    }
}
