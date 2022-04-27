package com.plusls.xma.compat.modmenu;

import com.plusls.xma.ModInfo;

public class WrapperModMenuApiImpl extends ModMenuApiImpl {

    @Override
    public String getModIdCompat() {
        return ModInfo.MOD_ID;
    }

}