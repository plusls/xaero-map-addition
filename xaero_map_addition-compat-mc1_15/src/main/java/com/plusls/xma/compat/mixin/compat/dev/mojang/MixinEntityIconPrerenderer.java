package com.plusls.xma.compat.mixin.compat.dev.mojang;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import top.hendrixshen.magiclib.dependency.Predicates;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import xaero.common.minimap.render.radar.EntityIconPrerenderer;

@Dependencies(or = {@Dependency("xaerominimap"), @Dependency("xaerobetterpvp")}, predicate = Predicates.DevMojangMixinPredicate.class)
@Mixin(EntityIconPrerenderer.class)
public class MixinEntityIconPrerenderer {

    @ModifyConstant(method = "<init>", constant = @Constant(stringValue = "currentLayer", ordinal = 0))
    private static String getMojangMappingCurrentLayer(String constant) {
        return "lastState";
    }

    @ModifyConstant(method = "<init>", constant = @Constant(stringValue = "net.minecraft.client.render.RenderLayer$MultiPhase", ordinal = 0))
    private static String getMojangMappingMultiPhase(String constant) {
        return "net.minecraft.client.renderer.RenderType$CompositeRenderType";
    }

    @ModifyConstant(method = "<init>", constant = @Constant(stringValue = "phases", ordinal = 0))
    private static String getMojangMappingPhases(String constant) {
        return "state";
    }

    @ModifyConstant(method = "<init>", constant = @Constant(stringValue = "texture", ordinal = 0))
    private static String getMojangMappingTexture(String constant) {
        return "textureState";
    }

    @ModifyConstant(method = "<init>", constant = @Constant(stringValue = "id", ordinal = 0))
    private static String getMojangMappingId(String constant) {
        return "texture";
    }

    @ModifyConstant(method = "<init>", constant = @Constant(stringValue = "com.mojang.blaze3d.platform.GlStateManager$BlendFuncState", ordinal = 0))
    private static String getMojangMappingBlendFuncState(String constant) {
        return "com.mojang.blaze3d.platform.GlStateManager$BlendState";
    }

    @ModifyConstant(method = "<init>", constant = @Constant(stringValue = "srcFactorRGB", ordinal = 0))
    private static String getMojangMappingSrcFactorRGB(String constant) {
        return "srcRgb";
    }

    @ModifyConstant(method = "<init>", constant = @Constant(stringValue = "dstFactorRGB", ordinal = 0))
    private static String getMojangMappingDstFactorRGB(String constant) {
        return "dstRgb";
    }

    @ModifyConstant(method = "<init>", constant = @Constant(stringValue = "srcFactorAlpha", ordinal = 0))
    private static String getMojangMappingSrcFactorAlpha(String constant) {
        return "srcAlpha";
    }

    @ModifyConstant(method = "<init>", constant = @Constant(stringValue = "dstFactorAlpha", ordinal = 0))
    private static String getMojangMappingDstFactorAlpha(String constant) {
        return "dstAlpha";
    }

    @ModifyConstant(method = "<init>", constant = @Constant(stringValue = "com.mojang.blaze3d.platform.GlStateManager$DepthTestState", ordinal = 0))
    private static String getMojangMappingDepthTestState(String constant) {
        return "com.mojang.blaze3d.platform.GlStateManager$DepthState";
    }

    @ModifyConstant(method = "<init>", constant = @Constant(stringValue = "capState", ordinal = 0))
    private static String getMojangMappingDepthTestStateCapState(String constant) {
        return "mode";
    }

    @ModifyConstant(method = "<init>", constant = @Constant(stringValue = "com.mojang.blaze3d.platform.GlStateManager$CullFaceState", ordinal = 0))
    private static String getMojangMappingCullFaceState(String constant) {
        return "com.mojang.blaze3d.platform.GlStateManager$CullState";
    }

    @ModifyConstant(method = "<init>", constant = @Constant(stringValue = "capState", ordinal = 1))
    private static String getMojangMappingCullFaceStateCapState(String constant) {
        return "enable";
    }

    @ModifyConstant(method = "<init>", constant = @Constant(stringValue = "com.mojang.blaze3d.platform.GlStateManager$CapabilityTracker", ordinal = 0))
    private static String getMojangMappingCapabilityTracker(String constant) {
        return "com.mojang.blaze3d.platform.GlStateManager$BooleanState";
    }

    @ModifyConstant(method = "<init>", constant = @Constant(stringValue = "state", ordinal = 0))
    private static String getMojangMappingState(String constant) {
        return "enabled";
    }
}
