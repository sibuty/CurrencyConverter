/*
 *
 * Copyright (C) 2017, Igor Goryainov
 * All rights reserved.
 */

package org.seriouslycompany.cc

import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Module
import dagger.Provides

/**
 */
@AppScope
@Module
class JsonModule {

  @Provides
  @AppScope
  fun jsonMapper() = ObjectMapper()
}