package com.android.own.ecommerceapp.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.own.ecommerceapp.EcommerceApplication
import com.android.own.ecommerceapp.di.component.ActivityComponent
import com.android.own.ecommerceapp.di.component.DaggerActivityComponent
import com.android.own.ecommerceapp.di.module.ActivityModule
import javax.inject.Inject

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    @Inject
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildActivityComponent())
        super.onCreate(savedInstanceState)
        setContentView(provideLayoutId())
        setupObservers()
        setupView(savedInstanceState)
        viewModel.onCreate()
    }

    private fun buildActivityComponent() =
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as EcommerceApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()

    protected open fun setupObservers() {

    }

    override fun onBackPressed() {
        /*if(supportFragmentManager.backStackEntryCount > 0)
            supportFragmentManager.popBackStack();
        else
            super.onBackPressed();*/


        val fragmentList: List<*> = supportFragmentManager.fragments

        var handled = false
        for (f in fragmentList) {
            if (f is BaseFragment<*>) {
                handled = f.onBackPressed()
                if (handled) {
                    break
                }
            }
        }

        if (!handled) {
            super.onBackPressed()
        }
    }

    @LayoutRes
    protected abstract fun provideLayoutId(): Int

    protected abstract fun injectDependencies(activityComponent: ActivityComponent)

    protected abstract fun setupView(savedInstanceState: Bundle?)

    open fun goBack() = onBackPressed()

    interface OnItemClickListener {
        fun onItemClicked(position: Int, view: View)
    }

    protected fun RecyclerView.addOnItemClickListener(onClickListener: OnItemClickListener) {
        this.addOnChildAttachStateChangeListener(object :
            RecyclerView.OnChildAttachStateChangeListener {

            override fun onChildViewDetachedFromWindow(view: View) {
                view.setOnClickListener(null)
            }


            override fun onChildViewAttachedToWindow(view: View) {

                view.setOnClickListener {
                    val holder = getChildViewHolder(view)
                    onClickListener.onItemClicked(holder.adapterPosition, view)
                }
            }

        })
    }
}