package com.android.own.ecommerceapp.ui

import androidx.lifecycle.MutableLiveData
import com.android.own.ecommerceapp.base.BaseViewModel
import com.android.own.ecommerceapp.data.model.Items
import com.android.own.ecommerceapp.util.Event
import com.android.own.ecommerceapp.util.network.NetworkHelper
import com.android.own.ecommerceapp.util.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class MainSharedViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable, networkHelper: NetworkHelper
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    override fun onCreate() {}

    val productDetailRedirection = MutableLiveData<Event<Boolean>>()
    val productCartRedirection = MutableLiveData<Event<Boolean>>()
    val productListProductRedirection = MutableLiveData<Event<Boolean>>()
    val productDetailData: MutableLiveData<Items> = MutableLiveData()
    val productTotalPrice: MutableLiveData<Int> = MutableLiveData()
    val allCartProduct = MutableLiveData<List<Items>>()


    fun getProduct(): Items {
        return productDetailData.value!!
    }

    fun getTotalPrice(): MutableLiveData<Int> {
        return productTotalPrice
    }


    fun onProductDetailRedirect() {
        productDetailRedirection.postValue(Event(true))
    }

    fun onProductCartRedirect() {
        productCartRedirection.postValue(Event(true))
    }


    fun onListProductRedirect() {
        productListProductRedirection.postValue(Event(true))
    }

    fun productDetailData(retrieveData: Items) {
        productDetailData.postValue(retrieveData)
    }

}