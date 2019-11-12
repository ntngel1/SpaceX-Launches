package com.ntngel1.spacexlaunches.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.gateway.retrofit.SpaceXApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var api: SpaceXApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.appComponent.inject(this)
        api.getLaunches(0, 10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                Log.d("Success", "")
            }, {
                it.printStackTrace()
            })
    }
}
