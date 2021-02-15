package com.android.own.ecommerceapp.ui.dashboard.product

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.own.ecommerceapp.R
import com.android.own.ecommerceapp.base.BaseFragment
import com.android.own.ecommerceapp.data.model.Items
import com.android.own.ecommerceapp.di.component.FragmentComponent
import com.android.own.ecommerceapp.ui.MainSharedViewModel
import com.android.own.ecommerceapp.ui.dashboard.product.adapter.ProductListAdapter
import kotlinx.android.synthetic.main.fragment_product_list.*
import javax.inject.Inject


class ProductListFragment : BaseFragment<ProductListViewModel>()
    , View.OnClickListener {


    companion object {

        const val TAG = "ProductListFragment"

        fun newInstance(): ProductListFragment {
            val args = Bundle()
            val fragment = ProductListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var productListAdapter: ProductListAdapter

    @Inject
    lateinit var mainSharedViewModel: MainSharedViewModel

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager


    override fun provideLayoutId(): Int = R.layout.fragment_product_list

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun setupView(view: View) {


        rvProduct.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager

        }


        rvProduct.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                mainSharedViewModel.onProductDetailRedirect()
                mainSharedViewModel.productDetailData(viewModel.retrieveData(position))

            }
        })

        imgCart.setOnClickListener(this)

    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.allProduct.observe(this, Observer {
            it?.run {
                productListAdapter =
                    ProductListAdapter(activity!!, it as ArrayList<Items>)
                rvProduct.adapter = productListAdapter
            }
        })

    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.imgCart -> {
                mainSharedViewModel.onProductCartRedirect()
            }
        }
    }


    /**
     * Could handle back press.
     * @return true if back press was handled
     */
    override fun onBackPressed(): Boolean {

        activity?.finishAffinity()
        return true
    }
}