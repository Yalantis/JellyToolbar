package com.yalantis.jellytoolbar

import android.view.View
import androidx.annotation.DimenRes

/**
 * Created by irinagalata on 11/23/16.
 */

fun View.getDimen(@DimenRes res: Int) = context.resources.getDimensionPixelOffset(res).toFloat()