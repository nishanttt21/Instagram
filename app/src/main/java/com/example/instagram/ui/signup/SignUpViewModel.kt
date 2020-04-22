package com.example.instagram.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.instagram.data.repository.UserRepository
import com.example.instagram.ui.base.BaseViewModel
import com.example.instagram.utils.common.Event
import com.example.instagram.utils.common.Resource
import com.example.instagram.utils.common.Status
import com.example.instagram.utils.common.Validator
import com.example.instagram.utils.network.NetworkHelper
import com.example.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SignUpViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val userRepository: UserRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {
    private val _validationList: MutableLiveData<List<Validator.Validation>> = MutableLiveData()

    private val _launchMain: MutableLiveData<Event<Map<String, String>>> = MutableLiveData()
    val launchMain: LiveData<Event<Map<String, String>>>
        get() = _launchMain

    private val _emailField: MutableLiveData<String> = MutableLiveData()
    val emailField: LiveData<String>
        get() = _emailField

    private val _passwordField: MutableLiveData<String> = MutableLiveData()
    val passwordField: LiveData<String>
        get() = _passwordField

    private val _nameField: MutableLiveData<String> = MutableLiveData()
    val nameField: LiveData<String>
        get() = _nameField

    private val _signingIn: MutableLiveData<Boolean> = MutableLiveData()
    val signingIn: LiveData<Boolean>
        get() = _signingIn

    val emailValidation: LiveData<Resource<Int>> =
        filterValidation(Validator.Validation.Field.EMAIL)

    val passwordValidation: LiveData<Resource<Int>> =
        filterValidation(Validator.Validation.Field.PASSWORD)
    val nameValidation: LiveData<Resource<Int>> =
        filterValidation(Validator.Validation.Field.NAME)

    private fun filterValidation(field: Validator.Validation.Field):
            LiveData<Resource<Int>> = Transformations.map(_validationList) { validations ->
        validations.find { validation -> validation.field == field }
            ?.run { return@run this.resource } ?: Resource.unknown()
    }

    fun onEmailChange(email: String) = _emailField.postValue(email)

    fun onPasswordChange(password: String) = _passwordField.postValue(password)

    fun onNameChange(name: String) = _nameField.postValue(name)


    override fun onCreate() {
//        TODO("Not yet implemented")
    }

    fun doSignUp() {
        val email = _emailField.value
        val password = _passwordField.value
        val name = _nameField.value
        val validations = Validator.validateSignUpField(name, email, password)
        _validationList.postValue(validations)
        if (validations.isNotEmpty() && email != null && password != null && name != null) {
            val successValidation = validations.filter { it.resource.status == Status.SUCCESS }
            if (successValidation.size == validations.size && checkInternetConnectionWithMessage()) {
                _signingIn.value = true
                compositeDisposable.addAll(
                    userRepository.doSignUpCall(name, email, password)
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                            {
                                userRepository.saveCurrentUser(it)
                                _signingIn.postValue(false)
                                _launchMain.postValue(Event(mapOf()))
                            }, {
                                handleNetworkError(it)
                                _signingIn.postValue(false)
                            }
                        )
                )

            }
        }
    }
}