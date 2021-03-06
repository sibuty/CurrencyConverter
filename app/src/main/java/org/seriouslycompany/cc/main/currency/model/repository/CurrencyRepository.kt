/*
 *
 * Copyright (C) 2017, Igor Goryainov
 * All rights reserved.
 */

package org.seriouslycompany.cc.main.currency.model.repository

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.seriouslycompany.cc.AppScope
import org.seriouslycompany.cc.main.currency.model.provider.RatesProvider
import javax.inject.Inject

/**
 */
@AppScope
class CurrencyRepository @Inject constructor(private val ratesProvider: RatesProvider) {

  private val currencyCodesPublisher = BehaviorSubject.create<List<String>>()

  fun refresh() = loadCurrencies()

  fun currencyList(): Observable<List<String>> {
    if (currencyCodesPublisher.value == null) {
      loadCurrencies()
    }
    return currencyCodesPublisher
  }

  private fun loadCurrencies() = ratesProvider.isoCodes()
      .subscribe(currencyCodesPublisher::onNext, { it.printStackTrace(); currencyCodesPublisher.onNext(emptyList())})

  fun convertableCurrenciesFor(currencyCode: String) = currencyList().map { it.filter { it != currencyCode } }

  fun convertableCurrenciesFor(currencyCodeEmitter: Observable<String>): Observable<List<String>>
      = currencyCodeEmitter.concatMap(::convertableCurrenciesFor)


}