package org.seriouslycompany.cc.di.ext

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

/**
 */
fun String.toBigDecimalSafe(): BigDecimal {
  return try {
    (DecimalFormat.getInstance() as DecimalFormat).apply {
      isParseBigDecimal = true
      decimalFormatSymbols = DecimalFormatSymbols().apply {
        groupingSeparator = ','
        decimalSeparator = '.'
      }
    }.parse(this) as BigDecimal
  } catch (e: Exception) {
    BigDecimal(0)
  }.roundBeforeThree()
}