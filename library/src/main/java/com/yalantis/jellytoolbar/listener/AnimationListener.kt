package com.yalantis.jellytoolbar.listener

import android.animation.Animator

/**
 * Created by irinagalata on 11/8/16.
 */
abstract class AnimationListener : Animator.AnimatorListener {

    override fun onAnimationRepeat(animation: Animator?) = Unit

    override fun onAnimationStart(animation: Animator?) = Unit

    override fun onAnimationCancel(animation: Animator?) = Unit

    override abstract fun onAnimationEnd(animation: Animator?)

}