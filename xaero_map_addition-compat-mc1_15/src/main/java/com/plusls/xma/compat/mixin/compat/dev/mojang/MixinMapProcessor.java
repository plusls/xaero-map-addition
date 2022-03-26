package com.plusls.xma.compat.mixin.compat.dev.mojang;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import top.hendrixshen.magiclib.dependency.Predicates;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import xaero.map.MapProcessor;

// fix crash in mojang mapping
@Dependencies(and = @Dependency("xaeroworldmap"), predicate = Predicates.DevMojangMixinPredicate.class)
@Mixin(MapProcessor.class)
public class MixinMapProcessor {
    @ModifyConstant(method = "<init>", constant = @Constant(stringValue = "renderTaskQueue", ordinal = 0))
    private static String getMojangMappingRenderTaskQueue(String constant) {
        return "progressTasks";
    }
}
