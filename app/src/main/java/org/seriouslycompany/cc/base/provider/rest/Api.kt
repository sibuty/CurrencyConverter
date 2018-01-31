/*
 *
 * Copyright (C) 2017, Igor Goryainov
 * All rights reserved.
 */

package org.seriouslycompany.cc.base.provider.rest

import io.reactivex.Single
import org.seriouslycompany.cc.main.currency.model.Rates
import retrofit2.http.GET
import retrofit2.http.Query

/**
 */
interface Api {

  @GET("latest")
  fun getInfo(): Single<Rates>

  @GET("latest")
  fun rate(@Query("base") isoCodeIn: String, @Query("symbols") isoCodeOut: String): Single<Rates>
}