/*
 *
 * Copyright (C) 2017, Igor Goryainov
 * All rights reserved.
 */

package org.seriouslycompany.cc.base.ext

import android.app.Activity
import android.support.annotation.IdRes
import android.view.View

/**
 */
@Suppress("UNCHECKED_CAST")
inline fun <T : View> Activity.view(@IdRes id: Int) = lazy { findView<T>(id) }

@Suppress("UNCHECKED_CAST")
inline fun <T : View> Activity.view(@IdRes id: Int, crossinline initializer: (T) -> T) = lazy { initializer(findView(id)) }

@Suppress("UNCHECKED_CAST")
inline fun <T : View> Activity.findView(@IdRes id: Int): T = findViewById(id)