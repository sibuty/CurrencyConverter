/*
 *
 * Copyright (C) 2017, Igor Goryainov
 * All rights reserved.
 */

package org.seriouslycompany.cc.base.ext

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler

/**
 */
inline fun <T> Observable<T>.ioMainTransformer() = subscribeOn(IoScheduler()).observeOn(AndroidSchedulers.mainThread())