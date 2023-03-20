package com.plusls.xma;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.plusls.ommc.feature.highlithtWaypoint.HighlightWaypointResourceLoader;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.inventory.InventoryMenu;
import org.joml.Matrix4f;
import top.hendrixshen.magiclib.compat.minecraft.api.blaze3d.vertex.VertexFormatCompatApi;

//#if MC <= 11605
//$$ import net.minecraft.client.Minecraft;
//$$ import net.minecraft.client.renderer.texture.TextureAtlas;
//$$ import java.util.Objects;
//#endif
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
        //#if MC > 11605
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
        //#else
        //$$ RenderSystem.disableAlphaTest();
        //$$ RenderSystem.shadeModel(7425);
        //$$ RenderSystem.bindTexture(Objects.requireNonNull(Minecraft.getInstance().getTextureManager().getTexture(InventoryMenu.BLOCK_ATLAS)).getId());
        //#endif

        RenderSystem.enableBlend();
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();

        TextureAtlasSprite icon = HighlightWaypointResourceLoader.targetIdSprite;
        bufferBuilder.begin(VertexFormatCompatApi.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);

        bufferBuilder.vertex(matrix4f, -xWidth, -yWidth, 0.0f).uv(icon.getU0(), icon.getV0()).color(iconR, iconG, iconB, fade).endVertex();
        bufferBuilder.vertex(matrix4f, -xWidth, yWidth, 0.0f).uv(icon.getU0(), icon.getV1()).color(iconR, iconG, iconB, fade).endVertex();
        bufferBuilder.vertex(matrix4f, xWidth, yWidth, 0.0f).uv(icon.getU1(), icon.getV1()).color(iconR, iconG, iconB, fade).endVertex();
        bufferBuilder.vertex(matrix4f, xWidth, -yWidth, 0.0f).uv(icon.getU1(), icon.getV0()).color(iconR, iconG, iconB, fade).endVertex();

        Tesselator.getInstance().end();
    }

    // From WaypointsGuiRenderer.translatePosition
    public static void translatePositionCompat(PoseStack matrixStack, int specW, int specH, double ps,
                                               double pc, double offx, double offy, double zoom, boolean circle) {
        double Y = (pc * offx + ps * offy) * zoom;
        double X = (ps * offx - pc * offy) * zoom;
        double borderedX = X;
        double borderedY = Y;
        if (!circle) {
            if (X > (double) specW) {
                borderedX = specW;
                borderedY = Y * (double) specW / X;
            } else if (X < (double) (-specW)) {
                borderedX = (-specW);
                borderedY = -Y * (double) specW / X;
            }

            if (borderedY > (double) specH) {
                borderedY = specH;
                borderedX = X * (double) specH / Y;
            } else if (borderedY < (double) (-specH)) {
                borderedY = (-specH);
                borderedX = -X * (double) specH / Y;
            }
        } else {
            double distSquared = X * X + Y * Y;
            double maxDistSquared = (specW * specW);
            if (distSquared > maxDistSquared) {
                double scaleDown = Math.sqrt(maxDistSquared / distSquared);
                borderedX = X * scaleDown;
                borderedY = Y * scaleDown;
            }
        }
        matrixStack.translate((double) (Math.round(borderedX) - 1L), (double) (Math.round(borderedY) - 1L), 0.0);
    }
}
