package com.yalantis.jellytoolbar.widget

import android.animation.ValueAnimator
import android.content.Context
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.yalantis.jellytoolbar.Constant
import com.yalantis.jellytoolbar.R
import com.yalantis.jellytoolbar.getDimen
import com.yalantis.jellytoolbar.interpolator.BounceInterpolator
import kotlinx.android.synthetic.main.layout_content.view.*

/**
 * Created by irinagalata on 11/23/16.
 */
class ContentLayout : RelativeLayout, JellyWidget {

    var contentView: View? = null
        set(value) {
            value?.let {
                container.removeAllViews()
                container.addView(it)
                field = value
            }
        }
    @DrawableRes var iconRes: Int? = null
        set(value) {
            value?.let {
                icon.setBackgroundResource(it)
                field = value
            }
        }
    @DrawableRes var cancelIconRes: Int? = null
        set(value) {
            value?.let {
                cancelIcon.setBackgroundResource(it)
                field = value
            }
        }

    internal var onIconClickListener: OnClickListener? = null
        set(value) {
            icon.setOnClickListener(value)
            field = value
        }
    internal var onCancelIconClickListener: OnClickListener? = null
        set(value) {
            cancelIcon.setOnClickListener(value)
            field = value
        }

    private var startPosition = 0f
    private var endPosition = 0f
    private var isInitialized = false
    private val iconFullSize = getDimen(R.dimen.icon_full_size)
    private val iconPadding = getDimen(R.dimen.icon_padding)

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.layout_content, this)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        if (!isInitialized) {
            init()
            isInitialized = true
        }
    }

    override fun init() {
        translationX = width.toFloat() - iconFullSize
        startPosition = width.toFloat() - iconFullSize
        endPosition = -height.toFloat() + iconFullSize - iconPadding * 0.5f
    }

    override fun collapse() {
        ValueAnimator.ofFloat(endPosition, startPosition).apply {
            startDelay = 50
            translationX = endPosition
            duration = Constant.ANIMATION_DURATION / 3
            interpolator = BounceInterpolator()
            addUpdateListener {
                translationX = animatedValue as Float
                icon.alpha = 0.5f + 0.5f * animatedFraction

                with(cancelIcon) {
                    rotation = 360 * animatedFraction
                    scaleX = 1 - animatedFraction
                    scaleY = 1 - animatedFraction
                    alpha = 1 - animatedFraction
                    translationX = endPosition - animatedValue as Float
                }
            }
        }.start()
    }

    override fun expand() {
        ValueAnimator.ofFloat(startPosition, endPosition).apply {
            startDelay = 50
            translationX = startPosition
            duration = Constant.ANIMATION_DURATION / 3
            interpolator = BounceInterpolator()

            with(cancelIcon) {
                translationX = 0f
                alpha = 1f
                scaleX = 1f
                scaleY = 1f
            }
            addUpdateListener {
                translationX = animatedValue as Float
                icon.alpha = 1f - 0.5f * animatedFraction
            }
        }.start()
    }

    override fun expandImmediately() {
        expand()
    }

}