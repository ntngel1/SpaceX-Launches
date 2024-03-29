package com.ntngel1.spacexlaunches.app.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import moxy.MvpAppCompatFragment

abstract class BaseFragment : MvpAppCompatFragment() {

    @get:LayoutRes
    protected abstract val layoutId: Int

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId, container, false)
}