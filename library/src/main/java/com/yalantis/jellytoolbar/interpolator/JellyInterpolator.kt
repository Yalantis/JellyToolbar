package com.yalantis.jellytoolbar.interpolator

import android.view.animation.Interpolator

/**
 * Created by irinagalata on 11/8/16.
 */
class JellyInterpolator : Interpolator {

    override fun getInterpolation(t: Float): Float {
        return (Math.min(1.0, Math.sin(28 * (t - 0.22)) / (t - 0.22) / 5)).toFloat()
    }

    private fun interpolateV2(t: Float): Float {
        val value = -0.00958447242129099f - 1.9026052199124508f * t - 291.55511323513304f * t * t +
                9758.603360080851f * t.pow(3) - 149307.19421910343f * t.pow(4) + 1319428.0106536655f * t.pow(5) -
                7312821.186780962f * t.pow(6) + 26819367.11802626f * t.pow(7) - 67247889.93552417f * t.pow(8) +
                117032893.62876818f * t.pow(9) - 141195405.7748263f * t.pow(10) + 115789294.78382991f * t.pow(11) -
                61551947.2334084f * t.pow(12) + 19121923.470667843f * t.pow(13) - 2635000.8624450294f * t.pow(14)
        return value.toFloat()
    }

    private fun interpolateV1(t: Float): Float {
        val value = -0.13002453137849412f + 24.245766697750938f * t - 1477.475227438854f * t * t +
                35116.61000638459f * t.pow(3) - 449442.9818253831f * t.pow(4) +
                3521908.5547679258 * t.pow(5) - 18121854.22112403 * t.pow(6) +
                63893457.28516045 * t.pow(7) - 158097323.89939904 * t.pow(8) +
                276988804.50985 * t.pow(9) - 341719652.1841202 * t.pow(10) +
                290138192.95748514 * t.pow(11) - 161276957.77605718 * t.pow(12) +
                52806926.891858205 * t.pow(13) - 7717722.585042693 * t.pow(14)
        return value.toFloat()
    }

    private fun Float.pow(n: Int) = Math.pow(this.toDouble(), n.toDouble())

}