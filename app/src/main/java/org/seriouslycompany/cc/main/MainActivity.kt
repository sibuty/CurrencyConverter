/*
 *
 * Copyright (C) 2017, Igor Goryainov
 * All rights reserved.
 */

package org.seriouslycompany.cc.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatSpinner
import android.widget.EditText
import org.seriouslycompany.cc.R
import org.seriouslycompany.cc.base.ext.view

class MainActivity : AppCompatActivity() {

  private val etFirst by view<EditText>(R.id.et_first_currency)
  private val etSecond by view<EditText>(R.id.et_second_currency)
  private val sFirst by view<AppCompatSpinner>(R.id.s_first_currency)
  private val sSecond by view<EditText>(R.id.s_second_currency)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  override fun onStart() {
    super.onStart()
  }
}
