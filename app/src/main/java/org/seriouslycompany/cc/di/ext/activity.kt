/*
 *
 * Copyright (C) 2017, Igor Goryainov
 * All rights reserved.
 */

package org.seriouslycompany.cc.di.ext

import android.app.Activity
import org.seriouslycompany.cc.App
import org.seriouslycompany.cc.AppComponent

/**
 */
val Activity.appComponent: AppComponent get() = (this.application as App).dagger