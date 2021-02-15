package com.android.own.ecommerceapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import com.android.own.ecommerceapp.R
import com.android.own.ecommerceapp.base.BaseActivity
import com.android.own.ecommerceapp.di.component.ActivityComponent
import com.android.own.ecommerceapp.ui.dashboard.DashBoardActivity
import com.android.own.ecommerceapp.ui.signup.SignUpActivity
import com.android.own.ecommerceapp.util.Event
import com.android.own.ecommerceapp.util.Status
import com.android.own.ecommerceapp.util.Toaster
import com.android.own.ecommerceapp.util.log.Logger
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<LoginViewModel>(), View.OnClickListener {

    override fun provideLayoutId(): Int = R.layout.activity_login

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        Logger.d("LoginActivity", "setupView")

        initListener()


        edtEmail.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onEmailChange(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

        edtPassword.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onPasswordChange(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })


    }

    private fun initListener() {
        createAccountText.setOnClickListener(this)
        btLogin.setOnClickListener(this)
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.launchRegister.observe(this, Observer<Event<Map<String, String>>> {
            it.getIfNotHandled()?.run {
                startActivity(Intent(applicationContext, SignUpActivity::class.java))
                finishAffinity()
            }
        })

        viewModel.launchDashBoard.observe(this, Observer<Event<Map<String, String>>> {
            it.getIfNotHandled()?.run {
                startActivity(Intent(applicationContext, DashBoardActivity::class.java))
                finishAffinity()
            }
        })


        viewModel.emailField.observe(this, Observer {
            if (edtEmail.text.toString() != it) edtEmail.setText(it)
        })

        viewModel.emailValidation.observe(this, Observer {
            when (it.status) {
                Status.ERROR -> layout_email.error = it.data?.run { getString(this) }
                else -> layout_email.isErrorEnabled = false
            }
        })

        viewModel.passwordField.observe(this, Observer {
            if (edtPassword.text.toString() != it) edtPassword.setText(it)
        })

        viewModel.passwordValidation.observe(this, Observer {
            when (it.status) {
                Status.ERROR -> layout_password.error = it.data?.run { getString(this) }
                else -> layout_password.isErrorEnabled = false
            }
        })

        viewModel.loggingIn.observe(this, Observer {
            pbLoading.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.createAccountText -> viewModel.onRegisterClick()

            R.id.btLogin -> viewModel.onLogin()
        }
    }
}