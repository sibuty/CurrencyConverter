/*
 *
 * Copyright (C) 2017, Igor Goryainov
 * All rights reserved.
 */

package org.seriouslycompany.cc

import android.annotation.SuppressLint
import android.support.multidex.MultiDexApplication


/**
 */
@SuppressLint("Registered")
class App : MultiDexApplication() {

  val dagger by lazy { DaggerAppComponent.builder().application(this).build() }

}