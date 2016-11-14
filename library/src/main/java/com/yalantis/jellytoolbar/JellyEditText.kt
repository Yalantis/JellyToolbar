package com.yalantis.jellytoolbar

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.BounceInterpolator
import com.yalantis.jellytoolbar.interpolator.JellyInterpolator

/**
 * Created by irinagalata on 11/7/16.
 */
class JellyEditText : View {

    var diff = 0f

    var w = 0f

    var isExpanded = false

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        w = height.toFloat()
        val path = Path()
        val paint = Paint()
        paint.shader = LinearGradient(0f, 0f, width.toFloat(), 0f, ContextCompat.getColor(context, R.color.endColor),
                ContextCompat.getColor(context, R.color.startColor), Shader.TileMode.CLAMP)
        path.moveTo(w, 0f)
        path.lineTo(width.toFloat(), 0f)
        path.lineTo(width.toFloat(), w)
        path.lineTo(w, w)
        path.quadTo(w - diff, w / 2, w, 0f)
        canvas?.drawPath(path, paint)
        path.close()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action != MotionEvent.ACTION_DOWN) return false

        if (isExpanded) {
            collapse()
        } else {
            expand()
        }

        return true
    }

    private fun collapse() {
        isExpanded = false
        ValueAnimator.ofFloat(0f, w / 2).apply {
            duration = 1200
            interpolator = JellyInterpolator()
            addUpdateListener {
                diff = -(animatedValue as Float)
                invalidate()
            }
        }.start()
        ValueAnimator.ofFloat(-930f, 0f).apply {
            duration = 400
            addUpdateListener {
                translationX = animatedValue as Float
            }
        }.start()
    }

    private fun expand() {
        isExpanded = true
        ValueAnimator.ofFloat(0f, w / 2).apply {
            duration = 1200
            interpolator = JellyInterpolator()
            addUpdateListener {
                diff = animatedValue as Float
                invalidate()
            }
            addListener(object : AnimationListener() {
                override fun onAnimationEnd(animation: Animator?) {
                    ValueAnimator.ofFloat(0f, 5f).apply {
                        duration = 150
                        interpolator = BounceInterpolator()
                        addUpdateListener {
                            translationX -= animatedValue as Float
                        }
                    }.start()
                }
            })
        }.start()
        ValueAnimator.ofFloat(0f, -927f).apply {
            duration = 400
            addUpdateListener {
                translationX = animatedValue as Float
            }
        }.start()
    }

}