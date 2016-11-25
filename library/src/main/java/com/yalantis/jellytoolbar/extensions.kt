package com.yalantis.jellytoolbar

import android.support.annotation.DimenRes
import android.view.View

/**
 * Created by irinagalata on 11/23/16.
 */

fun View.getDimen(@DimenRes res: Int) = context.resources.getDimensionPixelOffset(res).toFloat()