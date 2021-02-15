package com.android.own.ecommerceapp.di.component


import android.app.Application
import android.content.Context
import com.android.own.ecommerceapp.EcommerceApplication
import com.android.own.ecommerceapp.di.ApplicationContext
import com.android.own.ecommerceapp.util.rx.SchedulerProvider
import com.android.own.ecommerceapp.di.module.ApplicationModule
import com.android.own.ecommerceapp.util.network.NetworkHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {


    fun inject(app: EcommerceApplication)

    fun getApplication(): Application

    fun getNetworkHelper(): NetworkHelper


    fun getFirebaseAuth(): FirebaseAuth

    fun getFirebaseDatabase(): FirebaseDatabase

    fun getFirebaseFirestore(): FirebaseFirestore

    @ApplicationContext
    fun getContext(): Context

   // fun getDatabaseService(): DatabaseService

    fun getCompositeDisposable(): CompositeDisposable

    fun getSchedulerProvider(): SchedulerProvider

}
