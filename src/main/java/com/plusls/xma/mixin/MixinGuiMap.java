package com.plusls.xma.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xaero.map.gui.GuiMap;
import xaero.map.gui.RightClickOption;

import java.util.ArrayList;

@Mixin(value = GuiMap.class, remap = false)
public class MixinGuiMap {
    @Inject(method = "getRightClickOptions", at = @At(value = "RETURN"))
    private void addHighlightOption(CallbackInfoReturnable<ArrayList<RightClickOption>> cir) {

    }
}
