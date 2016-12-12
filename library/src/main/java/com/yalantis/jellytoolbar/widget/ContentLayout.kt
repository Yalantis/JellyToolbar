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

    private var mStartPosition = 0f
    private var mEndPosition = 0f
    private var mIsInitialized = false

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.layout_content, this)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        if (!mIsInitialized) {
            init()
            mIsInitialized = true
        }
    }

    override fun init() {
        translationX = width.toFloat() - getDimen(R.dimen.icon_full_size)
        mStartPosition = width.toFloat() - getDimen(R.dimen.icon_full_size)
        mEndPosition = -height.toFloat() + getDimen(R.dimen.icon_full_size) - getDimen(R.dimen.icon_padding) * 0.5f
    }

    override fun collapse() {
        ValueAnimator.ofFloat(mEndPosition, mStartPosition).apply {
            startDelay = 50
            translationX = mEndPosition
            duration = Constant.ANIMATION_DURATION / 3
            interpolator = BounceInterpolator()
            addUpdateListener {
                translationX = animatedValue as Float
                icon.alpha = 0.5f + 0.5f * animatedFraction
                cancelIcon.rotationY = 180 * animatedFraction
            }
        }.start()
        ValueAnimator.ofFloat(0f, 90f).apply {
            startDelay = 50
            duration = 50
            addUpdateListener { cancelIcon.rotation = animatedValue as Float }
        }.start()
    }

    override fun expand() {
        ValueAnimator.ofFloat(mStartPosition, mEndPosition).apply {
            startDelay = 50
            translationX = mStartPosition
            duration = Constant.ANIMATION_DURATION / 3
            interpolator = BounceInterpolator()
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