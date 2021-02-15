package com.android.own.ecommerceapp.di.component

import com.android.own.ecommerceapp.di.FragmentScope
import com.android.own.ecommerceapp.di.module.FragmentModule
import com.android.own.ecommerceapp.ui.dashboard.product.ProductListFragment
import com.android.own.ecommerceapp.ui.dashboard.productcraft.ProductCartListFragment
import com.android.own.ecommerceapp.ui.dashboard.productdetail.ProductDetailFragment
import dagger.Component

@FragmentScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [FragmentModule::class]
)
interface FragmentComponent {

    fun inject(fragment: ProductListFragment)

    fun inject(fragment: ProductDetailFragment)

    fun inject(fragment: ProductCartListFragment)

}