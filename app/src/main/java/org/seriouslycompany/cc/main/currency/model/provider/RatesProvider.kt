/*
 *
 * Copyright (C) 2017, Igor Goryainov
 * All rights reserved.
 */

package org.seriouslycompany.cc.main.currency.model.provider

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.seriouslycompany.cc.AppScope
import org.seriouslycompany.cc.base.provider.rest.Api
import org.seriouslycompany.cc.main.currency.model.Rates
import javax.inject.Inject

/**
 */
@AppScope
class RatesProvider @Inject constructor(private val api: Api) {

  fun isoCodes(): Single<List<String>> = api.getInfo().map(Rates::toIsoCodes).subscribeOn(Schedulers.io())

  fun rate(sourceIsoCode: String, destinationIsoCode: String): Single<Double>
      = api.rate(sourceIsoCode, destinationIsoCode).map { it.rates.entries.firstOrNull()?.value ?: 1.0 }
      .subscribeOn(Schedulers.io())
}