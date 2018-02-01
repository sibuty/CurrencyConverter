/*
 *
 * Copyright (C) 2017, Igor Goryainov
 * All rights reserved.
 */

package org.seriouslycompany.cc.base.ext

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 */
inline fun <T> Observable<T>.mainTransformer() = observeOn(AndroidSchedulers.mainThread())

inline fun <T> Observable<T>.ioTransformer() = observeOn(Schedulers.io())