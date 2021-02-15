package com.android.own.ecommerceapp.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.android.own.ecommerceapp.R
import com.android.own.ecommerceapp.base.BaseActivity
import com.android.own.ecommerceapp.di.component.ActivityComponent
import com.android.own.ecommerceapp.ui.MainSharedViewModel
import com.android.own.ecommerceapp.ui.dashboard.product.ProductListFragment
import com.android.own.ecommerceapp.ui.dashboard.productcraft.ProductCartListFragment
import com.android.own.ecommerceapp.ui.dashboard.productdetail.ProductDetailFragment
import com.android.own.ecommerceapp.util.Toaster
import javax.inject.Inject

class DashBoardActivity : BaseActivity<DashBoardViewModel>() {

    @Inject
    lateinit var mainSharedViewModel: MainSharedViewModel


    private var activeFragment: Fragment? = null

    override fun provideLayoutId(): Int = R.layout.activity_dash_board

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {

    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.productListNavigation.observe(this, Observer {
            it.getIfNotHandled()?.run { showProductList() }
        })


        viewModel.productDetailNavigation.observe(this, Observer {
            it.getIfNotHandled()?.run { showProductDetail() }
        })

        viewModel.productCartNavigation.observe(this, Observer {
            it.getIfNotHandled()?.run { showProductCraftList() }
        })

        mainSharedViewModel.productDetailRedirection.observe(this, Observer {
            it.getIfNotHandled()?.run {
                viewModel.onProductDetailCall()
            }
        })

        mainSharedViewModel.productCartRedirection.observe(this, Observer {
            it.getIfNotHandled()?.run {
                viewModel.onProductCartCall()
            }
        })


        mainSharedViewModel.productListProductRedirection.observe(this, Observer {
            it.getIfNotHandled()?.run {
                viewModel.onProductListCall()
            }
        })
    }


    private fun showProductList() {
        //   if (activeFragment is ProductListFragment) return

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment =
            supportFragmentManager.findFragmentByTag(ProductListFragment.TAG) as ProductListFragment?

        if (fragment == null) {
            fragment = ProductListFragment.newInstance()
            fragmentTransaction.add(R.id.containerFragment, fragment, ProductListFragment.TAG)
        } else {
            fragmentTransaction.show(fragment)
        }

        if (activeFragment != null) fragmentTransaction.hide(activeFragment as Fragment)

        fragmentTransaction.commit()

        activeFragment = fragment
    }

    private fun showProductDetail() {
          if (activeFragment is ProductDetailFragment) return

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment =
            supportFragmentManager.findFragmentByTag(ProductDetailFragment.TAG) as ProductDetailFragment?

        if (fragment == null) {
            fragment = ProductDetailFragment.newInstance()
            fragmentTransaction.add(R.id.containerFragment, fragment, ProductDetailFragment.TAG)
        } else {
            fragmentTransaction.show(fragment)
        }

        if (activeFragment != null) fragmentTransaction.hide(activeFragment as Fragment)

        fragmentTransaction.commit()

        activeFragment = fragment
    }


    private fun showProductCraftList() {
         if (activeFragment is ProductCartListFragment) return

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment =
            supportFragmentManager.findFragmentByTag(ProductCartListFragment.TAG) as ProductCartListFragment?

        if (fragment == null) {
            fragment = ProductCartListFragment.newInstance()
            fragmentTransaction.add(R.id.containerFragment, fragment, ProductCartListFragment.TAG)
        } else {
            fragmentTransaction.show(fragment)
        }

        if (activeFragment != null) fragmentTransaction.hide(activeFragment as Fragment)

        fragmentTransaction.commit()

        activeFragment = fragment
    }
}