package com.android.own.ecommerceapp.ui.dashboard.productdetail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.android.own.ecommerceapp.R
import com.android.own.ecommerceapp.base.BaseFragment
import com.android.own.ecommerceapp.data.model.Items
import com.android.own.ecommerceapp.di.component.FragmentComponent
import com.android.own.ecommerceapp.ui.MainSharedViewModel
import kotlinx.android.synthetic.main.fragment_product_details.*
import javax.inject.Inject


class ProductDetailFragment : BaseFragment<ProductDetailViewModel>(), View.OnClickListener {

    @Inject
    lateinit var mainSharedViewModel: MainSharedViewModel

    private var finalCartList: ArrayList<Items> = ArrayList()


    companion object {

        const val TAG = "ProductDetailFragment"

        fun newInstance(): ProductDetailFragment {
            val args = Bundle()
            val fragment = ProductDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun provideLayoutId(): Int = R.layout.fragment_product_details

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun setupView(view: View) {

        val activity = activity as AppCompatActivity?
        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)



        viewModel.titleField.observe(this, Observer {
            if (tvProductName.text.toString() != it) tvProductName.text = it
        })

        viewModel.descriptionField.observe(this, Observer {
            if (tvDescription.text.toString() != it) tvDescription.text = it
        })

        viewModel.priceField.observe(this, Observer {
            if (tvProductPrice.text.toString() != it) tvProductPrice.text = it
        })

        addToCartButton.setOnClickListener(this)
        imgBack.setOnClickListener(this)
    }

    override fun setupObservers() {
        super.setupObservers()


        mainSharedViewModel.productDetailData.observe(this, Observer {
            viewModel.onNameChange(it.name.toString())
            viewModel.onDescriptionChange(it.details.toString())
            viewModel.onPriceChange(it.price.toString())
        })

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.addToCartButton -> {
                finalCartList.add(mainSharedViewModel.getProduct())
                mainSharedViewModel.allCartProduct.postValue(finalCartList)
                mainSharedViewModel.onListProductRedirect()
            }

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
}