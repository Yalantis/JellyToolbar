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
    var startColor: Int = android.R.color.transparent
    var endColor: Int = android.R.color.transparent

    private var isInitialized = false
    private var difference = 0f
    private var startPosition = 0f
    private var endPosition = 0f
    private val paint = Paint()
    private val path = Path()
    private var gradient: LinearGradient? = null
    private val jellyViewSize = getDimen(R.dimen.jelly_view_size)
    private val jellyViewWidth = getDimen(R.dimen.jelly_view_width)
    private val jellyViewOffset = getDimen(R.dimen.jelly_view_offset)

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (!isInitialized) {
            init()
        }
        redraw(canvas)
    }

    override fun init() {
        layoutParams = FrameLayout.LayoutParams(width + jellyViewWidth.toInt() * 2, height)
        translationX = width - jellyViewSize
        startPosition = translationX
        endPosition = -jellyViewWidth
        isInitialized = true
        gradient = createGradient()
    }

    private fun redraw(canvas: Canvas?) {
        paint.shader = gradient
        path.apply {
            moveTo(jellyViewWidth, 0f)
            lineTo(width.toFloat(), 0f)
            lineTo(width.toFloat(), height.toFloat())
            lineTo(jellyViewWidth, height.toFloat())
            quadTo(jellyViewWidth - difference, height / 2f, jellyViewWidth, 0f)
        }
        canvas?.drawPath(path, paint)
        path.reset()
        path.close()
    }

    private fun createGradient(): LinearGradient? {
        return LinearGradient(0f, 0f, width.toFloat(), 0f, startColor,
                endColor,
                Shader.TileMode.CLAMP)
    }

    override fun collapse() {
        isExpanded = false
        animateJellyCollapsing()
        moveBack()
    }

    override fun expand() {
        isExpanded = true
        animateJellyExpanding()
        moveForward(true)
    }

    override fun expandImmediately() {
        isExpanded = true

        animateJelly(1, true, 0)
        translationX = width - jellyViewSize
        startPosition = translationX
        endPosition = -jellyViewWidth
        moveForward(false)
    }

    private fun animateJellyExpanding() {
        animateJelly(1, true, Constant.ANIMATION_DURATION)
    }

    private fun animateJellyCollapsing() {
        animateJelly(-1, false, Constant.ANIMATION_DURATION)
    }

    private fun animateJelly(coefficient: Int, moveOffset: Boolean, animDuration: Long) {
        ValueAnimator.ofFloat(0f, jellyViewWidth / 2).apply {
            duration = animDuration
            interpolator = JellyInterpolator()
            addUpdateListener {
                difference = animatedValue as Float * coefficient
                invalidate()
            }
            addListener(object : AnimationListener() {
                override fun onAnimationEnd(animation: Animator?) {
                    difference = 0f
                    invalidate()

                    if (moveOffset && isExpanded) {
                        moveOffset()
                    }
                }
            })
        }.start()
    }

    private fun moveOffset() {
        ValueAnimator.ofFloat(0f, jellyViewOffset).apply {
            duration = 150
            interpolator = BounceInterpolator()
            addUpdateListener {
                translationX -= animatedValue as Float
            }
        }.start()
    }

    private fun moveForward(offset: Boolean) {
        var endPosition = endPosition
        if (offset) endPosition += jellyViewOffset
        ValueAnimator.ofFloat(startPosition, endPosition).apply {
            translationX = startPosition
            duration = Constant.ANIMATION_DURATION / 3
            addUpdateListener {
                translationX = animatedValue as Float
            }
        }.start()
    }

    private fun moveBack() {
        ValueAnimator.ofFloat(endPosition, startPosition).apply {
            translationX = endPosition
            duration = Constant.ANIMATION_DURATION / 3
            addUpdateListener {
                translationX = animatedValue as Float
            }
        }.start()
    }

}