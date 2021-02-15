package com.android.own.ecommerceapp.di.module

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.own.ecommerceapp.base.BaseFragment
import com.android.own.ecommerceapp.ui.MainSharedViewModel
import com.android.own.ecommerceapp.ui.dashboard.product.ProductListViewModel
import com.android.own.ecommerceapp.ui.dashboard.productcraft.ProductCartListViewModel
import com.android.own.ecommerceapp.ui.dashboard.productdetail.ProductDetailViewModel
import com.android.own.ecommerceapp.util.ViewModelProviderFactory
import com.android.own.ecommerceapp.util.network.NetworkHelper
import com.android.own.ecommerceapp.util.rx.SchedulerProvider
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import java.util.ArrayList


@Module
class FragmentModule(private val fragment: BaseFragment<*>) {

    @Provides
    fun provideLinearLayoutManager(): LinearLayoutManager = LinearLayoutManager(fragment.context)


    @Provides
    fun provideProductListViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper, firebaseDatabase: FirebaseDatabase
    ): ProductListViewModel = ViewModelProviders.of(
        fragment, ViewModelProviderFactory(ProductListViewModel::class) {
            ProductListViewModel(
                schedulerProvider, compositeDisposable, networkHelper, firebaseDatabase
                , ArrayList()
            )
        }).get(ProductListViewModel::class.java)

    @Provides
    fun provideProductDetailViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper, firebaseDatabase: FirebaseDatabase
    ): ProductDetailViewModel = ViewModelProviders.of(
        fragment, ViewModelProviderFactory(ProductDetailViewModel::class) {
            ProductDetailViewModel(
                schedulerProvider, compositeDisposable, networkHelper, firebaseDatabase
                , ArrayList()
            )
        }).get(ProductDetailViewModel::class.java)

    @Provides
    fun provideProductCraftListViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper, firebaseDatabase: FirebaseDatabase
    ): ProductCartListViewModel = ViewModelProviders.of(
        fragment, ViewModelProviderFactory(ProductCartListViewModel::class) {
            ProductCartListViewModel(
                schedulerProvider, compositeDisposable, networkHelper, firebaseDatabase
                , ArrayList()
            )
        }).get(ProductCartListViewModel::class.java)


    @Provides
    fun provideMainSharedViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable, networkHelper: NetworkHelper
    ): MainSharedViewModel = ViewModelProviders.of(
        fragment.activity!!, ViewModelProviderFactory(MainSharedViewModel::class) {
            MainSharedViewModel(schedulerProvider, compositeDisposable, networkHelper)
        }).get(MainSharedViewModel::class.java)
}