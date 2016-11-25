package com.yalantis.jellytoolbar.widget

import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.yalantis.jellytoolbar.R
import kotlinx.android.synthetic.main.jelly_toolbar.view.*

/**
 * Created by irinagalata on 11/23/16.
 */
class JellyToolbar : FrameLayout, JellyWidget {

    var toolbar: Toolbar? = null
        private set
        get() {
            return defaultToolbar
        }
    var contentView: View? = null
        set(value) {
            contentLayout.contentView = value
        }
    @DrawableRes var iconRes: Int? = null
        set(value) {
            contentLayout.iconRes = value
        }
    @DrawableRes var cancelIconRes: Int? = null
        set(value) {
            contentLayout.cancelIconRes = value
        }
    @ColorInt var startColor: Int? = null
        set(value) {
            value?.let { jellyView.startColor = value }
        }
    @ColorInt var endColor: Int? = null
        set(value) {
            value?.let { jellyView.endColor = value }
        }

    private var mIsExpanded = false

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.jelly_toolbar, this)

        attrs?.let { retrieveAttributes(attrs) }

        contentLayout.onIconClickListener = View.OnClickListener { expand() }
        contentLayout.onCancelIconClickListener = View.OnClickListener { collapse() }
    }

    private fun retrieveAttributes(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.JellyToolbar)

        val startColorAttr = typedArray.getColor(R.styleable.JellyToolbar_startColor, 0)
        if (startColorAttr != 0) startColor = startColorAttr

        val endColorAttr = typedArray.getColor(R.styleable.JellyToolbar_endColor, 0)
        if (endColorAttr != 0) endColor = endColorAttr

        val iconResAttr = typedArray.getResourceId(R.styleable.JellyToolbar_icon, 0)
        if (iconResAttr != 0) iconRes = iconResAttr

        val cancelIconResAttr = typedArray.getResourceId(R.styleable.JellyToolbar_cancelIcon, 0)
        if (cancelIconResAttr != 0) cancelIconRes = cancelIconResAttr

        val title = typedArray.getString(R.styleable.JellyToolbar_title)
        if (!TextUtils.isEmpty(title)) toolbar?.title = title

        val titleColor = typedArray.getColor(R.styleable.JellyToolbar_titleTextColor, 0)
        if (titleColor != 0) toolbar?.setTitleTextColor(titleColor)

        typedArray.recycle()
    }

    override fun collapse() {
        if (!mIsExpanded || jellyView.isBusy) return

        jellyView.collapse()
        contentLayout.collapse()
        mIsExpanded = false
    }

    override fun expand() {
        if (mIsExpanded || jellyView.isBusy) return

        jellyView.expand()
        contentLayout.expand()
        mIsExpanded = true
    }

}