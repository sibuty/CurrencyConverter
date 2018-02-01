package org.seriouslycompany.cc.di.ext

import java.math.BigDecimal
import java.math.RoundingMode

/**
 */
fun BigDecimal.roundBeforeThree(): BigDecimal = setScale(3, RoundingMode.DOWN)