/*
 *
 * Copyright (C) 2017, Igor Goryainov
 * All rights reserved.
 */

package org.seriouslycompany.cc.base.ext

import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer

/**
 */
inline fun Disposable.bind(container: DisposableContainer): Disposable {
  container.add(this)
  return this
}