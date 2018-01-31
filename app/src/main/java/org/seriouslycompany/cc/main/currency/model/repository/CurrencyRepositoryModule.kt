/*
 *
 * Copyright (C) 2017, Igor Goryainov
 * All rights reserved.
 */

package org.seriouslycompany.cc.main.currency.model.repository

import dagger.Module
import dagger.Provides
import org.seriouslycompany.cc.AppScope
import org.seriouslycompany.cc.main.currency.model.provider.RatesProvider

/**
 */
@AppScope
@Module
class CurrencyRepositoryModule {

  @AppScope
  @Provides
  fun repository(ratesProvider: RatesProvider) = CurrencyRepository(ratesProvider)
}