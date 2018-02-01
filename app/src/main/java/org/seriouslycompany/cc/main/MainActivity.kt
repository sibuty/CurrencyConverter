/*
 *
 * Copyright (C) 2017, Igor Goryainov
 * All rights reserved.
 */

package org.seriouslycompany.cc.main

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatSpinner
import android.widget.EditText
import com.jakewharton.rxbinding2.support.v4.widget.refreshes
import com.jakewharton.rxbinding2.support.v4.widget.refreshing
import com.jakewharton.rxbinding2.widget.itemSelections
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.disposables.CompositeDisposable
import org.seriouslycompany.cc.R
import org.seriouslycompany.cc.base.ext.ioTransformer
import org.seriouslycompany.cc.base.ext.mainTransformer
import org.seriouslycompany.cc.base.ext.view
import org.seriouslycompany.cc.di.ext.appComponent
import org.seriouslycompany.cc.di.ext.bind
import org.seriouslycompany.cc.di.ext.roundBeforeThree
import org.seriouslycompany.cc.di.ext.toBigDecimalSafe
import org.seriouslycompany.cc.main.currency.adapter.CurrencyArrayAdapter
import java.math.BigDecimal
import java.util.*

class MainActivity : AppCompatActivity() {

  private val etFirst by view<EditText>(R.id.et_first_currency)
  private val etSecond by view<EditText>(R.id.et_second_currency)
  private val sFirst by view<AppCompatSpinner>(R.id.s_first_currency)
  private val sSecond by view<AppCompatSpinner>(R.id.s_second_currency)
  private val srlContent by view<SwipeRefreshLayout>(R.id.srl_content)

  private val clearOnDestroy = CompositeDisposable()
  private val viewState = ViewState()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    appComponent.currencyRepository.currencyList()
        .mainTransformer()
        .doOnNext { srlContent.refreshing().accept(false) }
        .ioTransformer()
        .filter { it.isNotEmpty() }
        .map { it.map(Currency::getInstance) }
        .map { CurrencyArrayAdapter(MainActivity@ this, it) }
        .mainTransformer()
        .doOnNext(sFirst::setAdapter)
        .doOnNext(sSecond::setAdapter)
        .subscribe()
        .bind(clearOnDestroy)

    srlContent.refreshes()
        .doOnNext { appComponent.currencyRepository.refresh() }
        .subscribe()
        .bind(clearOnDestroy)

    sFirst.itemSelections()
        .skipInitialValue()
        .map { sFirst.adapter.getItem(it) as Currency }
        .map(Currency::getCurrencyCode)
        .filter { viewState.sourceIsoCode != it }
        .doOnNext { viewState.sourceIsoCode = it }
        .filter { viewState.destinationIsoCode.isNotBlank() }
        .concatMap { appComponent.ratesProvider.rate(viewState.sourceIsoCode, viewState.destinationIsoCode).toObservable() }
        .filter { viewState.sourceConversionValue != it }
        .doOnNext { viewState.sourceConversionValue = it }
        .map { viewState.sourceValue.toPlainString() }
        .doOnNext { viewState.clearSourceValue() }
        .mainTransformer()
        .subscribe(etFirst::setText)
        .bind(clearOnDestroy)

    sSecond.itemSelections()
        .skipInitialValue()
        .map { sSecond.adapter.getItem(it) as Currency }
        .map(Currency::getCurrencyCode)
        .filter { viewState.destinationIsoCode != it }
        .doOnNext { viewState.destinationIsoCode = it }
        .filter { viewState.sourceIsoCode.isNotBlank() }
        .concatMap { appComponent.ratesProvider.rate(viewState.destinationIsoCode, viewState.sourceIsoCode).toObservable() }
        .filter { viewState.destinationConversionValue != it }
        .doOnNext { viewState.destinationConversionValue = it }
        .concatMap { appComponent.ratesProvider.rate(viewState.sourceIsoCode, viewState.destinationIsoCode).toObservable() }
        .filter { viewState.sourceConversionValue != it }
        .doOnNext { viewState.sourceConversionValue = it }
        .map { viewState.sourceValue.toPlainString() }
        .doOnNext { viewState.clearSourceValue() }
        .mainTransformer()
        .subscribe(etFirst::setText)
        .bind(clearOnDestroy)

    etFirst.textChanges()
        .map(CharSequence::toString)
        .map(String::toBigDecimalSafe)
        .filter { viewState.sourceValue != it }
        .doOnNext { viewState.sourceValue = it }
        .map { it.multiply(BigDecimal(viewState.sourceConversionValue)).roundBeforeThree() }
        .filter { viewState.destinationValue != it }
        .doOnNext { viewState.destinationValue = it }
        .map { viewState.destinationValue.toPlainString() }
        .mainTransformer()
        .subscribe(etSecond::setText)
        .bind(clearOnDestroy)

    etSecond.textChanges()
        .map(CharSequence::toString)
        .map(String::toBigDecimalSafe)
        .filter { viewState.destinationValue != it }
        .doOnNext { viewState.destinationValue = it }
        .map { it.multiply(BigDecimal(viewState.destinationConversionValue)).roundBeforeThree() }
        .filter { viewState.sourceValue != it }
        .doOnNext { viewState.sourceValue = it }
        .map { viewState.sourceValue.toPlainString() }
        .mainTransformer()
        .subscribe(etFirst::setText)
        .bind(clearOnDestroy)

    /*prepareInputField(etFirst,
        etSecond,
        { viewState.sourceValue },
        { viewState.sourceValue = it },
        { viewState.destinationValue },
        { viewState.destinationValue = it },
        { viewState.sourceConversionValue }
    ).bind(clearOnDestroy)

    prepareInputField(etSecond,
        etFirst,
        { viewState.destinationValue },
        { viewState.destinationValue = it },
        { viewState.sourceValue },
        { viewState.sourceValue = it },
        { viewState.destinationConversionValue }
    ).bind(clearOnDestroy)*/
  }

  override fun onDestroy() {
    super.onDestroy()
    clearOnDestroy.clear()
  }

  /*private fun prepareInputField(sourceEtField: EditText,
                                destinationEtField: EditText,
                                sourceValueGetter: () -> BigDecimal,
                                sourceValueSetter: (BigDecimal) -> Unit,
                                destinationValueGetter: () -> BigDecimal,
                                destinationValueSetter: (BigDecimal) -> Unit,
                                conversionValue: () -> Double) =
      sourceEtField.textChanges()
          .map(CharSequence::toString)
          .map(String::toBigDecimalSafe)
          .filter { sourceValueGetter() != it }
          .doOnNext(sourceValueSetter)
          .map { it.multiply(BigDecimal(conversionValue())).roundBeforeThree() }
          .filter { destinationValueGetter() != it }
          .doOnNext(destinationValueSetter)
          .map { destinationValueGetter().toPlainString() }
          .mainTransformer()
          .subscribe(destinationEtField::setText)*/

  class ViewState {
    var sourceValue: BigDecimal = BigDecimal(0).roundBeforeThree()
    var destinationValue: BigDecimal = BigDecimal(0).roundBeforeThree()
    var sourceIsoCode: String = ""
    var destinationIsoCode: String = ""
    var sourceConversionValue: Double = 1.0
    var destinationConversionValue: Double = 1.0

    fun clearSourceValue() {
      sourceValue = BigDecimal(0).roundBeforeThree()
    }

    fun clearDestinationValueValue() {
      destinationValue = BigDecimal(0).roundBeforeThree()
    }
  }
}