/*
 *
 * Copyright (C) 2017, Igor Goryainov
 * All rights reserved.
 */

package org.seriouslycompany.cc.base.ext

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 */
inline fun <T> Observable<T>.ioMainTransformer() = observeOn(AndroidSchedulers.mainThread())