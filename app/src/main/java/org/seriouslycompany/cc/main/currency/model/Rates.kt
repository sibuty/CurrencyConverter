/*
 *
 * Copyright (C) 2017, Igor Goryainov
 * All rights reserved.
 */

package org.seriouslycompany.cc.main.currency.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

/**
 */
@JsonPropertyOrder("base", "date", "rates")
class Rates {

  @get:JsonProperty("base")
  @set:JsonProperty("base")
  var base: String? = ""
  @get:JsonProperty("date")
  @set:JsonProperty("date")
  var date: String? = ""
  @get:JsonProperty("rates")
  @set:JsonProperty("rates")
  var rates: LinkedHashMap<String, Double> = LinkedHashMap()

  fun toIsoCodes() : List<String> = rates.run { keys.toMutableList().apply { base?.also { add(it) } }.apply { sort() } }
}