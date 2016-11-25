package com.yalantis.jellytoolbar.interpolator

import android.view.animation.Interpolator

/**
 * Created by irinagalata on 11/8/16.
 */
class JellyInterpolator : Interpolator {

    override fun getInterpolation(t: Float): Float {
        return (Math.min(1.0, Math.sin(28 * (t - 0.22)) / (t - 0.22) / 5)).toFloat()
    }

}