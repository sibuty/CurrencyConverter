/*
 *
 * Copyright (C) 2017, Igor Goryainov
 * All rights reserved.
 */

package org.seriouslycompany.cc.main

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import org.seriouslycompany.cc.R
import org.seriouslycompany.cc.base.ext.ioMainTransformer
import org.seriouslycompany.cc.di.ext.appComponent

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  override fun onStart() {
    super.onStart()
    appComponent.api.getInfo().ioMainTransformer().subscribe { AlertDialog.Builder(this).setMessage(it.toString()).show() }
  }
}
