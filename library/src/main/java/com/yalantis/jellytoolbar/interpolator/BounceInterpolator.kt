package com.yalantis.jellytoolbar.interpolator

import android.view.animation.Interpolator

/**
 * Created by irinagalata on 11/23/16.
 */
class BounceInterpolator : Interpolator {
    val MOVE_TIME = 0.46667f
    val FIRST_BOUNCE_TIME = 0.26666f

    override fun getInterpolation(t: Float): Float = when {
        t < MOVE_TIME ->
            move(t)
        t < MOVE_TIME + FIRST_BOUNCE_TIME ->
            firstBounce(t)
        else ->
            secondBounce(t)
    }

    private fun move(t: Float): Float {
        return 4.592f * t * t
    }

    private fun firstBounce(t: Float): Float {
        return 2.5f * t * t - 3f * t + 1.85556f
    }

    private fun secondBounce(t: Float): Float {
        return 0.625f * t * t - 1.083f * t + 1.458f
    }

}