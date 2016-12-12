package com.yalantis.jellytoolbar.widget

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.BounceInterpolator
import android.widget.FrameLayout
import com.yalantis.jellytoolbar.Constant
import com.yalantis.jellytoolbar.R
import com.yalantis.jellytoolbar.getDimen
import com.yalantis.jellytoolbar.interpolator.JellyInterpolator
import com.yalantis.jellytoolbar.listener.AnimationListener

/**
 * Created by irinagalata on 11/15/16.
 */
class JellyView : View, JellyWidget {

    var isExpanded = false
    var isBusy = false
    var startColor: Int = android.R.color.transparent
    var endColor: Int = android.R.color.transparent

    private var mIsInitialized = false
    private var mDifference = 0f
    private var mStartPosition = 0f
    private var mEndPosition = 0f
    private var mVisibleWidth = 0f
    private val mPaint = Paint()
    private val mPath = Path()
    private var mGradient: LinearGradient? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        mVisibleWidth = getDimen(R.dimen.jelly_view_width)
        if (!mIsInitialized) {
            init()
        }
        redraw(canvas)
    }

    override fun init() {
        layoutParams = FrameLayout.LayoutParams(width + mVisibleWidth.toInt() * 2, height)
        translationX = width - getDimen(R.dimen.jelly_view_size).toFloat()
        mStartPosition = translationX
        mEndPosition = -mVisibleWidth
        mIsInitialized = true
        mGradient = createGradient()
    }

    private fun redraw(canvas: Canvas?) {
        mPaint.shader = mGradient
        mPath.apply {
            moveTo(mVisibleWidth, 0f)
            lineTo(width.toFloat(), 0f)
            lineTo(width.toFloat(), height.toFloat())
            lineTo(mVisibleWidth, height.toFloat())
            quadTo(mVisibleWidth - mDifference, height / 2f, mVisibleWidth, 0f)
        }
        canvas?.drawPath(mPath, mPaint)
        mPath.reset()
        mPath.close()
    }

    private fun createGradient(): LinearGradient? {
        return LinearGradient(0f, 0f, width.toFloat(), 0f, startColor,
                endColor,
                Shader.TileMode.CLAMP)
    }

    override fun collapse() {
        if (isBusy) return

        isBusy = true
        isExpanded = false
        animateJellyCollapsing()
        moveBack()
    }

    override fun expand() {
        if (isBusy) return

        isBusy = true
        isExpanded = true
        animateJellyExpanding()
        moveForward(true)
    }

    override fun expandImmediately() {
        if (isBusy) return

        isBusy = true
        isExpanded = true

        animateJelly(1, true, 0)
        translationX = width - getDimen(R.dimen.jelly_view_size).toFloat()
        mStartPosition = translationX
        mEndPosition = -mVisibleWidth
        moveForward(false)
    }

    private fun animateJellyExpanding() {
        animateJelly(1, true, Constant.ANIMATION_DURATION)
    }

    private fun animateJellyCollapsing() {
        animateJelly(-1, false, Constant.ANIMATION_DURATION)
    }

    private fun animateJelly(coefficient: Int, moveOffset: Boolean, animDuration: Long) {
        mVisibleWidth = getDimen(R.dimen.jelly_view_width)
        ValueAnimator.ofFloat(0f, mVisibleWidth / 2).apply {
            duration = animDuration
            interpolator = JellyInterpolator()
            addUpdateListener {
                mDifference = animatedValue as Float * coefficient
                invalidate()
            }
            addListener(object : AnimationListener() {
                override fun onAnimationEnd(animation: Animator?) {
                    isBusy = false
                    mDifference = 0f
                    invalidate()

                    if (moveOffset) {
                        moveOffset()
                    }
                }
            })
        }.start()
    }

    private fun moveOffset() {
        ValueAnimator.ofFloat(0f, getDimen(R.dimen.jelly_view_offset)).apply {
            duration = 150
            interpolator = BounceInterpolator()
            addUpdateListener {
                translationX -= animatedValue as Float
            }
        }.start()
    }

    private fun moveForward(offset: Boolean) {
        var endPosition = mEndPosition
        if (offset) endPosition += getDimen(R.dimen.jelly_view_offset)
        ValueAnimator.ofFloat(mStartPosition, endPosition).apply {
            translationX = mStartPosition
            duration = Constant.ANIMATION_DURATION / 3
            addUpdateListener {
                translationX = animatedValue as Float
            }
        }.start()
    }

    private fun moveBack() {
        ValueAnimator.ofFloat(mEndPosition, mStartPosition).apply {
            translationX = mEndPosition
            duration = Constant.ANIMATION_DURATION / 3
            addUpdateListener {
                translationX = animatedValue as Float
            }
        }.start()
    }

}