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
class Currency {

  @get:JsonProperty("base")
  @set:JsonProperty("base")
  var base: String? = ""
  @get:JsonProperty("date")
  @set:JsonProperty("date")
  var date: String? = ""
  @get:JsonProperty("rates")
  @set:JsonProperty("rates")
  var rates: LinkedHashMap<String, Double>? = LinkedHashMap()
}