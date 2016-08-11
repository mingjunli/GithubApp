package com.anly.githubapp.common.util;

import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;

/**
 * Created by mikepenz on 18.07.15.
 */
public class CrossfaderWrapper implements ICrossfader {
    private Crossfader mCrossfader;

    public CrossfaderWrapper(Crossfader crossfader) {
        this.mCrossfader = crossfader;
    }

    @Override
    public void crossfade() {
        mCrossfader.crossFade();
    }

    @Override
    public boolean isCrossfaded() {
        return mCrossfader.isCrossFaded();
    }
}
