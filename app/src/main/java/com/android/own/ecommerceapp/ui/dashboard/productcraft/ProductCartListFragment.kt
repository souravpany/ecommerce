package com.android.own.ecommerceapp.ui.dashboard.productcraft

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.own.ecommerceapp.R
import com.android.own.ecommerceapp.base.BaseFragment
import com.android.own.ecommerceapp.data.model.Items
import com.android.own.ecommerceapp.di.component.FragmentComponent
import com.android.own.ecommerceapp.ui.MainSharedViewModel
import com.android.own.ecommerceapp.ui.dashboard.productcraft.adapter.CartItemListAdapter
import kotlinx.android.synthetic.main.fragment_product_cart_list.*
import javax.inject.Inject


class ProductCartListFragment : BaseFragment<ProductCartListViewModel>(), View.OnClickListener,
    CartItemListAdapter.CartInterface {

    @Inject
    lateinit var mainSharedViewModel: MainSharedViewModel

    private lateinit var cartItemListAdapter: CartItemListAdapter

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager


    companion object {

        const val TAG = "ProductCraftListFragment"

        fun newInstance(): ProductCartListFragment {
            val args = Bundle()
            val fragment = ProductCartListFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun provideLayoutId(): Int = R.layout.fragment_product_cart_list

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun setupView(view: View) {

        val activity = activity as AppCompatActivity?
        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)


        cartRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager

        }

        imgBack.setOnClickListener(this)


    }

    override fun setupObservers() {
        super.setupObservers()

        mainSharedViewModel.allCartProduct.observe(this, Observer {
            it?.run {
                cartItemListAdapter =
                    CartItemListAdapter(
                        activity!!,
                        it as ArrayList<Items>,
                        this@ProductCartListFragment
                    )
                cartRecyclerView.adapter = cartItemListAdapter
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
            R.id.imgBack -> {
                mainSharedViewModel.onListProductRedirect()
            }

        }
    }

    /**
     * Could handle back press.
     * @return true if back press was handled
     */
    override fun onBackPressed(): Boolean {

        mainSharedViewModel.onListProductRedirect()
        return true
    }

    override fun changeQuantity(quantity: Int?) {
        mainSharedViewModel.productTotalPrice.postValue(quantity)

        orderTotalTextView.text = (mainSharedViewModel.productTotalPrice.value?.plus(quantity!!)).toString()
    }
}