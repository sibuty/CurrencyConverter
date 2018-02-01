/*
 *
 * Copyright (C) 2017, Igor Goryainov
 * All rights reserved.
 */

package org.seriouslycompany.cc

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import org.seriouslycompany.cc.main.currency.model.repository.CurrencyRepository

/**
 */
@AppScope
@Component(modules = arrayOf(JsonModule::class, RestModule::class))
abstract class AppComponent {

  abstract val application: Application
  abstract val currencyRepository: CurrencyRepository

  @Component.Builder
  interface Builder {
    fun build(): AppComponent
    @BindsInstance
    fun application(application: Application): Builder
  }
}