/*
 *
 * Copyright (C) 2017, Igor Goryainov
 * All rights reserved.
 */

package org.seriouslycompany.cc.main.currency.model.provider

import io.reactivex.Single
import org.seriouslycompany.cc.base.provider.rest.Api
import org.seriouslycompany.cc.main.currency.model.Rates

/**
 */
class RatesProvider(val api: Api) {

  fun isoCodes(): Single<List<String>> = api.getInfo().map(Rates::toIsoCodes)

  fun rate(isoCodeIn: String, isoCodeOut: String): Single<Double> = api.rate(isoCodeIn, isoCodeOut).map { it.rates.entries.first().value }
}