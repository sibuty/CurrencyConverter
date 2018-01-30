/*
 *
 * Copyright (C) 2017, Igor Goryainov
 * All rights reserved.
 */

package org.seriouslycompany.cc

import android.app.Application
import com.fasterxml.jackson.databind.ObjectMapper
import dagger.BindsInstance
import dagger.Component
import org.seriouslycompany.cc.base.provider.rest.Api

/**
 */
@AppScope
@Component(modules = arrayOf(JsonModule::class, RestModule::class))
abstract class AppComponent {

  abstract val application: Application
  abstract val jsonMapper: ObjectMapper
  abstract val api: Api

  @Component.Builder
  interface Builder {
    fun build(): AppComponent
    @BindsInstance
    fun application(application: Application): Builder
  }
}