/*
 *
 * Copyright (C) 2017, Igor Goryainov
 * All rights reserved.
 */

package org.seriouslycompany.cc

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.seriouslycompany.cc.base.provider.rest.Api
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 */
@AppScope
@Module
class RestModule {

  @Provides
  @AppScope
  fun api(jsonMapper: ObjectMapper) = create(BuildConfig.SERVER_URL, jsonMapper)

  private fun create(baseUrl: String, mapper: ObjectMapper = ObjectMapper()): Api = Retrofit.Builder()
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(JacksonConverterFactory.create(mapper.registerKotlinModule()))
      .apply {
        if (BuildConfig.BUILD_TYPE == "debug") {
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