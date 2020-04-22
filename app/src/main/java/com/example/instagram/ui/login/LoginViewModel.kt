package com.example.instagram.ui.login

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

class LoginViewModel(
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

    private val _loggingIn: MutableLiveData<Boolean> = MutableLiveData()
    val loggingIn: LiveData<Boolean>
        get() = _loggingIn

    val emailValidation: LiveData<Resource<Int>> =
        filterValidation(Validator.Validation.Field.EMAIL)

    val passwordValidation: LiveData<Resource<Int>> =
        filterValidation(Validator.Validation.Field.PASSWORD)

    override fun onCreate() {
//        TODO("Not yet implemented")
    }

    private fun filterValidation(field: Validator.Validation.Field):
            LiveData<Resource<Int>> = Transformations.map(_validationList) { validations ->
        validations.find { validation -> validation.field == field }
            ?.run { return@run this.resource } ?: Resource.unknown()
    }

    fun onEmailChange(email: String) = _emailField.postValue(email)

    fun onPasswordChange(password: String) = _passwordField.postValue(password)

    fun doLogin() {

        val email = _emailField.value
        val password = _passwordField.value
        val validations = Validator.validateLoginField(email, password)
        _validationList.postValue(validations)
        if (validations.isNotEmpty() && email != null && password != null) {
            val successValidation = validations.filter { it.resource.status == Status.SUCCESS }
            if (successValidation.size == validations.size && checkInternetConnectionWithMessage()) {
                _loggingIn.value = true
                compositeDisposable.addAll(
                    userRepository.doLoginCall(email, password)
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                            {
                                userRepository.saveCurrentUser(it)
                                _loggingIn.postValue(false)
                                _launchMain.postValue(Event(mapOf()))
                            }, {
                                handleNetworkError(it)
                                _loggingIn.postValue(false)
                            }
                        )
                )

            }
        }
    }
}
