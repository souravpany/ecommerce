package com.android.own.ecommerceapp.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.android.own.ecommerceapp.R
import com.android.own.ecommerceapp.base.BaseViewModel
import com.android.own.ecommerceapp.data.model.User
import com.android.own.ecommerceapp.util.Event
import com.android.own.ecommerceapp.util.Resource
import com.android.own.ecommerceapp.util.Status
import com.android.own.ecommerceapp.util.common.Validator
import com.android.own.ecommerceapp.util.log.Logger
import com.android.own.ecommerceapp.util.network.NetworkHelper
import com.android.own.ecommerceapp.util.rx.SchedulerProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.disposables.CompositeDisposable

class SignUpViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : BaseViewModel(
    schedulerProvider, compositeDisposable, networkHelper
) {


    val launchLogin: MutableLiveData<Event<Map<String, String>>> = MutableLiveData()
    val launchDashBoard: MutableLiveData<Event<Map<String, String>>> = MutableLiveData()

    private val validationsList: MutableLiveData<List<Validator.ValidationRegister>> =
        MutableLiveData()
    val nameField: MutableLiveData<String> = MutableLiveData()
    val emailField: MutableLiveData<String> = MutableLiveData()
    val passwordField: MutableLiveData<String> = MutableLiveData()
    val registerIn: MutableLiveData<Boolean> = MutableLiveData()

    val emailValidation: LiveData<Resource<Int>> =
        filterValidation(Validator.ValidationRegister.FieldRegister.EMAIL)
    val passwordValidation: LiveData<Resource<Int>> =
        filterValidation(Validator.ValidationRegister.FieldRegister.PASSWORD)
    val nameValidation: LiveData<Resource<Int>> =
        filterValidation(Validator.ValidationRegister.FieldRegister.NAME)


    private fun filterValidation(field: Validator.ValidationRegister.FieldRegister) =
        Transformations.map(validationsList) {
            it.find { validation -> validation.field == field }
                ?.run { return@run this.resource }
                ?: Resource.unknown()
        }


    fun onNameChange(name: String) = nameField.postValue(name)

    fun onEmailChange(email: String) = emailField.postValue(email)

    fun onPasswordChange(password: String) = passwordField.postValue(password)


    override fun onCreate() {
        Logger.d("SignUpViewModel", "signUpViewModel")
    }

    fun onLoginTextClick() {
        launchLogin.postValue(Event(emptyMap()))
    }

    fun onRegister() {
        val email = emailField.value
        val password = passwordField.value
        val name = nameField.value


        val validations = Validator.validateRegisterFields(name, email, password)
        validationsList.postValue(validations)


        if (validations.isNotEmpty() && email != null && password != null && name != null) {
            val successValidation = validations.filter { it.resource.status == Status.SUCCESS }
            if (successValidation.size == validations.size && checkInternetConnectionWithMessage()) {
                registerIn.postValue(true)
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { it ->
                        if (it.isSuccessful) {

                            val user = User(name, email, password)

                            firebaseAuth.currentUser?.uid?.let { it1 ->
                                firebaseDatabase.getReference("Users")
                                    .child(it1)
                                    .setValue(user).addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            registerIn.postValue(false)
                                            clearFields()
                                            launchDashBoard.postValue(Event(emptyMap()))
                                            messageStringId.postValue(Resource.error(R.string.register_success))
                                        } else {
                                            registerIn.postValue(false)
                                            messageString.postValue(Resource.error(it.exception?.message))
                                        }
                                    }
                            }

                        } else {
                            registerIn.postValue(false)
                            messageString.postValue(Resource.error(it.exception?.message))
                        }

                    }
            }
        }

    }

    private fun clearFields() {
        nameField.postValue("")
        passwordField.postValue("")
        emailField.postValue("")
    }

}