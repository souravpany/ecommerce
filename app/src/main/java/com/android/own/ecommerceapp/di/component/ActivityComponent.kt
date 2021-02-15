package com.android.own.ecommerceapp.di.component

import com.android.own.ecommerceapp.ui.login.LoginActivity
import com.android.own.ecommerceapp.di.ActivityScope
import com.android.own.ecommerceapp.di.module.ActivityModule
import com.android.own.ecommerceapp.ui.dashboard.DashBoardActivity
import com.android.own.ecommerceapp.ui.signup.SignUpActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent {

    fun inject(activity: LoginActivity)

    fun inject(activity: SignUpActivity)

    fun inject(activity: DashBoardActivity)


}