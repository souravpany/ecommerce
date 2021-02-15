package com.android.own.ecommerceapp.ui.dashboard.productcraft

import androidx.lifecycle.MutableLiveData
import com.android.own.ecommerceapp.base.BaseViewModel
import com.android.own.ecommerceapp.data.model.Items
import com.android.own.ecommerceapp.util.network.NetworkHelper
import com.android.own.ecommerceapp.util.rx.SchedulerProvider
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.disposables.CompositeDisposable


class ProductCartListViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val firebaseDatabase: FirebaseDatabase,
    var productList: ArrayList<Items>
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    val titleField: MutableLiveData<String> = MutableLiveData()
    val descriptionField: MutableLiveData<String> = MutableLiveData()
    val priceField: MutableLiveData<String> = MutableLiveData()


    fun onNameChange(title: String) = titleField.postValue(title)

    fun onDescriptionChange(description: String) = descriptionField.postValue(description)

    fun onPriceChange(price: String) = priceField.postValue(price)



    override fun onCreate() {

    }
}