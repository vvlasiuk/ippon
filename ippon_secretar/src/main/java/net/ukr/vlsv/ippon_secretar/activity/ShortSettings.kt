package net.ukr.vlsv.ippon_secretar.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.ukr.vlsv.ippon_secretar.R

class ShortSettings: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_short_settings, container, false)
        return view
    }
}
