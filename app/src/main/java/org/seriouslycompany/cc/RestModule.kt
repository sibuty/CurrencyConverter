/*
 *
 * Copyright (C) 2017, Igor Goryainov
 * All rights reserved.
 */

package org.seriouslycompany.cc

import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Module
import dagger.Provides
import org.seriouslycompany.cc.base.provider.rest.Api

/**
 */
@AppScope
@Module
class RestModule {

  @Provides
  @AppScope
  fun api(jsonMapper: ObjectMapper) = Api.create(BuildConfig.SERVER_URL, jsonMapper)
}