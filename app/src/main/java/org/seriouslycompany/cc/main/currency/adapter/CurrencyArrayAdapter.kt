/*
 *
 * Copyright (C) 2017, Igor Goryainov
 * All rights reserved.
 */

package org.seriouslycompany.cc.main.currency.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import org.seriouslycompany.cc.R

/**
 */
class CurrencyArrayAdapter(
    context: Context,
    objects: MutableList<String>
) : ArrayAdapter<String>(context, 0, 0, objects) {

  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    return customView(position, convertView, parent)
  }

  override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
    return customView(position, convertView, parent)
  }

  private fun customView(position: Int, convertView: View?, parent: ViewGroup) = if (convertView == null) {
    ViewHolder(LayoutInflater.from(context).inflate(R.layout.currency_item, parent, false)).apply { view?.tag = this }
  } else {
    convertView.tag as ViewHolder
  }.view!!.apply { (findViewById<TextView>(R.id.tv_currency_name)).text = getItem(position) }
}

class ViewHolder(var view: View? = null)