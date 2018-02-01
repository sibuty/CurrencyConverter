package org.seriouslycompany.cc.di.ext

import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer

/**
 */
fun Disposable.bind(container: DisposableContainer): Disposable = apply { container.add(this) }