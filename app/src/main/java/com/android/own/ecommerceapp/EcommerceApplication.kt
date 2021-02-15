package com.android.own.ecommerceapp

import android.app.Application
import com.android.own.ecommerceapp.di.component.ApplicationComponent
import com.android.own.ecommerceapp.di.component.DaggerApplicationComponent
import com.android.own.ecommerceapp.di.module.ApplicationModule


class EcommerceApplication : Application() {


    lateinit var applicationComponent: ApplicationComponent


    override fun onCreate() {
        super.onCreate()
        injectDependencies()

    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
        applicationComponent.inject(this)
    }


}
