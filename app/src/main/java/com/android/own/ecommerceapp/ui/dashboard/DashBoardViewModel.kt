package com.android.own.ecommerceapp.ui.dashboard

import androidx.lifecycle.MutableLiveData
import com.android.own.ecommerceapp.base.BaseViewModel
import com.android.own.ecommerceapp.util.Event
import com.android.own.ecommerceapp.util.network.NetworkHelper
import com.android.own.ecommerceapp.util.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class DashBoardViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable, networkHelper: NetworkHelper
) : BaseViewModel(
    schedulerProvider, compositeDisposable, networkHelper
) {


    val productListNavigation = MutableLiveData<Event<Boolean>>()
    val productDetailNavigation = MutableLiveData<Event<Boolean>>()
    val productCartNavigation = MutableLiveData<Event<Boolean>>()

    override fun onCreate() {
        onProductListCall()
    }

    fun onProductDetailCall() {
        productDetailNavigation.postValue(Event(true))
    }

    fun onProductCartCall() {
        productCartNavigation.postValue(Event(true))
    }

    fun onProductListCall() {
        productListNavigation.postValue(Event(true))
    }


}