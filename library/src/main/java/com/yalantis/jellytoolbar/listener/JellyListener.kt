package com.yalantis.jellytoolbar.listener

/**
 * Created by irinagalata on 11/25/16.
 */
abstract class JellyListener {

    open fun onToolbarExpandingStarted() = Unit

    open fun onToolbarCollapsingStarted() = Unit

    open fun onToolbarExpanded() = Unit

    open fun onToolbarCollapsed() = Unit

    abstract fun onCancelIconClicked()

}