/*
 *
 * Copyright (C) 2017, Igor Goryainov
 * All rights reserved.
 */

package org.seriouslycompany.cc.base.provider.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.seriouslycompany.cc.BuildConfig
import org.seriouslycompany.cc.main.currency.model.Currency
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET

/**
 */
interface Api {

  @GET("latest")
  fun getInfo(): Observable<Currency>

  companion object {

    fun create(baseUrl: String, mapper: ObjectMapper = ObjectMapper()): Api = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(JacksonConverterFactory.create(mapper.registerKotlinModule()))
        .apply {
          if (BuildConfig.DEBUG) {
            client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
          }
        }
        .baseUrl(baseUrl)
        .build()
        .create(Api::class.java)
  }
}