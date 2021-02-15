package com.android.own.ecommerceapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.android.own.ecommerceapp.base.BaseViewModel
import com.android.own.ecommerceapp.util.Event
import com.android.own.ecommerceapp.util.Resource
import com.android.own.ecommerceapp.util.Status
import com.android.own.ecommerceapp.util.common.Validator
import com.android.own.ecommerceapp.util.log.Logger
import com.android.own.ecommerceapp.util.network.NetworkHelper
import com.android.own.ecommerceapp.util.rx.SchedulerProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.disposables.CompositeDisposable

class LoginViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val firebaseAuth: FirebaseAuth
) : BaseViewModel(
    schedulerProvider, compositeDisposable, networkHelper
) {


    val launchRegister: MutableLiveData<Event<Map<String, String>>> = MutableLiveData()
    val launchDashBoard: MutableLiveData<Event<Map<String, String>>> = MutableLiveData()


    private val validationsList: MutableLiveData<List<Validator.Validation>> = MutableLiveData()
    val emailField: MutableLiveData<String> = MutableLiveData()
    val passwordField: MutableLiveData<String> = MutableLiveData()
    val loggingIn: MutableLiveData<Boolean> = MutableLiveData()

    val emailValidation: LiveData<Resource<Int>> =
        filterValidation(Validator.Validation.Field.EMAIL)
    val passwordValidation: LiveData<Resource<Int>> =
        filterValidation(Validator.Validation.Field.PASSWORD)

    private fun filterValidation(field: Validator.Validation.Field) =
        Transformations.map(validationsList) {
            it.find { validation -> validation.field == field }
                ?.run { return@run this.resource }
                ?: Resource.unknown()
        }


    fun onEmailChange(email: String) = emailField.postValue(email)

    fun onPasswordChange(password: String) = passwordField.postValue(password)


    override fun onCreate() {
        Logger.d("LoginViewModel", "onCreate")
    }


    fun onLogin() {
        val email = emailField.value
        val password = passwordField.value


        val validations = Validator.validateLoginFields(email, password)
        validationsList.postValue(validations)


        if (validations.isNotEmpty() && email != null && password != null) {
            val successValidation = validations.filter { it.resource.status == Status.SUCCESS }
            if (successValidation.size == validations.size && checkInternetConnectionWithMessage()) {
                loggingIn.postValue(true)

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {

                    if (it.isSuccessful) {
                        launchDashBoard.postValue(Event(emptyMap()))
                    } else {
                        messageString.postValue(Resource.error(it.exception?.message))
                    }
                }

            }
        }
    }


    fun onRegisterClick() {
        launchRegister.postValue(Event(emptyMap()))
    }


}