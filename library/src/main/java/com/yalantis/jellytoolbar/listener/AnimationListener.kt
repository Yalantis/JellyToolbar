package com.yalantis.jellytoolbar.listener

import android.animation.Animator

/**
 * Created by irinagalata on 11/8/16.
 */
abstract class AnimationListener : Animator.AnimatorListener {

    override fun onAnimationRepeat(animation: Animator?) {
    }

    override abstract fun onAnimationEnd(animation: Animator?)

    override fun onAnimationStart(animation: Animator?) {
    }

    override fun onAnimationCancel(animation: Animator?) {
    }

}