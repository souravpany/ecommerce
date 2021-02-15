package com.android.own.ecommerceapp.ui.dashboard.product

import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import com.android.own.ecommerceapp.base.BaseViewModel
import com.android.own.ecommerceapp.data.model.Items
import com.android.own.ecommerceapp.util.network.NetworkHelper
import com.android.own.ecommerceapp.util.rx.SchedulerProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.disposables.CompositeDisposable


class ProductListViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val firebaseDatabase: FirebaseDatabase,
    var productList: ArrayList<Items>
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {


    val allProduct = MutableLiveData<List<Items>>()


    override fun onCreate() {
        getAllProduct()
    }

    private fun getAllProduct() {

        firebaseDatabase.reference.child("Items")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {
                    for (dataSnapshot1 in dataSnapshot.children) {
                        val p: Items? = dataSnapshot1.getValue<Items>(Items::class.java)
                        if (p != null) {
                            productList.add(p)
                        }
                    }

                    allProduct.postValue(productList)

                }

                override fun onCancelled(@NonNull databaseError: DatabaseError) {

                }
            })

    }

    fun retrieveData(position: Int): Items {

        return productList[position]

    }

}