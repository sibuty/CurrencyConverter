/*
 *
 * Copyright (C) 2017, Igor Goryainov
 * All rights reserved.
 */

package org.seriouslycompany.cc.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatSpinner
import android.widget.EditText
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import org.seriouslycompany.cc.R
import org.seriouslycompany.cc.base.ext.ioMainTransformer
import org.seriouslycompany.cc.base.ext.view
import org.seriouslycompany.cc.di.ext.appComponent
import org.seriouslycompany.cc.main.currency.adapter.CurrencyArrayAdapter
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class MainActivity : AppCompatActivity() {

  private val etFirst by view<EditText>(R.id.et_first_currency)
  private val etSecond by view<EditText>(R.id.et_second_currency)
  private val sFirst by view<AppCompatSpinner>(R.id.s_first_currency)
  private val sSecond by view<AppCompatSpinner>(R.id.s_second_currency)

  private val viewState = ViewState();

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    appComponent.currencyRepository.currencyList()
        .map { it.map(Currency::getInstance) }
        .map { CurrencyArrayAdapter(MainActivity@this, it) }
        .observeOn(AndroidSchedulers.mainThread())
        .ioMainTransformer()
        .doOnNext(sFirst::setAdapter)
        .doOnNext(sSecond::setAdapter)
        .subscribe()

    etFirst.textChanges()
        .map(CharSequence::toString)
        .map(String::toBigDecimal)
        .filter { viewState.sourceValue != it }
        .doOnNext { viewState.sourceValue = it }
        .map { it.multiply(BigDecimal(2)) }
        .filter { viewState.destinationValue != it }
        .doOnNext { viewState.destinationValue = it }
        .map { viewState.destinationValue.toPlainString() }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(etSecond::setText)

    etSecond.textChanges()
        .map(CharSequence::toString)
        .map(String::toBigDecimal)
        .filter { viewState.destinationValue != it }
        .doOnNext { viewState.destinationValue = it }
        .map { it.divide(BigDecimal(2)) }
        .filter { viewState.sourceValue != it }
        .doOnNext { viewState.sourceValue = it }
        .map { viewState.sourceValue.toPlainString() }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(etFirst::setText)
  }

  override fun onStart() {
    super.onStart()
  }

  class ViewState {
    var sourceValue: BigDecimal = BigDecimal(0)
    var destinationValue: BigDecimal = BigDecimal(0)
  }
}


fun String.toBigDecimal(): BigDecimal {
  try {
    return (DecimalFormat.getInstance() as DecimalFormat).apply {
      isParseBigDecimal = true

      decimalFormatSymbols = DecimalFormatSymbols().apply {
        groupingSeparator = ','
        decimalSeparator = '.'
      }
    }.parse(this) as BigDecimal
  } catch (e: Exception) {
    return BigDecimal(0)
  }
}