package com.yalantis.jellytoolbar.interpolator

import android.view.animation.Interpolator

/**
 * Created by irinagalata on 11/8/16.
 */
class JellyInterpolator : Interpolator {

    override fun getInterpolation(t: Float) = (Math.min(1.0, Math.sin(28 * t - 6.16) / (5 * t - 1.1))).toFloat()

}