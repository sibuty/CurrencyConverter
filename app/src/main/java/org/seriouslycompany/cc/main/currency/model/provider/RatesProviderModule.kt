/*
 *
 * Copyright (C) 2017, Igor Goryainov
 * All rights reserved.
 */

package org.seriouslycompany.cc.main.currency.model.provider

import dagger.Module
import dagger.Provides
import org.seriouslycompany.cc.AppScope
import org.seriouslycompany.cc.base.provider.rest.Api

/**
 */
@AppScope
@Module
class RatesProviderModule {

  @AppScope
  @Provides
  fun provider(api: Api) = RatesProvider(api)
}