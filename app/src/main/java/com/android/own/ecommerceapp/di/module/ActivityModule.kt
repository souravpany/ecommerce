package com.android.own.ecommerceapp.di.module

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.own.ecommerceapp.base.BaseActivity
import com.android.own.ecommerceapp.ui.MainSharedViewModel
import com.android.own.ecommerceapp.ui.dashboard.DashBoardViewModel
import com.android.own.ecommerceapp.ui.login.LoginViewModel
import com.android.own.ecommerceapp.ui.signup.SignUpViewModel
import com.android.own.ecommerceapp.util.ViewModelProviderFactory
import com.android.own.ecommerceapp.util.network.NetworkHelper
import com.android.own.ecommerceapp.util.rx.SchedulerProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * Kotlin Generics Reference: https://kotlinlang.org/docs/reference/generics.html
 * Basically it means that we can pass any class that extends BaseActivity which take
 * BaseViewModel subclass as parameter
 */

@Suppress("DEPRECATION")
@Module
class ActivityModule(private val activity: BaseActivity<*>) {

    @Provides
    fun provideLinearLayoutManager(): LinearLayoutManager = LinearLayoutManager(activity)


    @Provides
    fun provideLoginViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper, firebaseAuth: FirebaseAuth
    ): LoginViewModel = ViewModelProviders.of(
        activity, ViewModelProviderFactory(LoginViewModel::class) {
            LoginViewModel(schedulerProvider, compositeDisposable, networkHelper, firebaseAuth)
        }).get(LoginViewModel::class.java)

    @Provides
    fun provideSignUpViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper, firebaseAuth: FirebaseAuth, firebaseDatabase: FirebaseDatabase
    ): SignUpViewModel = ViewModelProviders.of(
        activity, ViewModelProviderFactory(SignUpViewModel::class) {
            SignUpViewModel(
                schedulerProvider,
                compositeDisposable,
                networkHelper,
                firebaseAuth,
                firebaseDatabase
            )
        }).get(SignUpViewModel::class.java)

    @Provides
    fun provideDashBoardViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper
    ): DashBoardViewModel = ViewModelProviders.of(
        activity, ViewModelProviderFactory(DashBoardViewModel::class) {
            DashBoardViewModel(schedulerProvider, compositeDisposable, networkHelper)
        }).get(DashBoardViewModel::class.java)


    @Provides
    fun provideMainSharedViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable, networkHelper: NetworkHelper
    ): MainSharedViewModel = ViewModelProviders.of(
        activity, ViewModelProviderFactory(MainSharedViewModel::class) {
            MainSharedViewModel(schedulerProvider, compositeDisposable, networkHelper)
        }).get(MainSharedViewModel::class.java)


}