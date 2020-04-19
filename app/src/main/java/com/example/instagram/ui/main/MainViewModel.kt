package com.example.instagram.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.instagram.data.local.DatabaseService
import com.example.instagram.data.local.entity.Address
import com.example.instagram.data.local.entity.User
import com.example.instagram.data.remote.NetworkService
import com.example.instagram.ui.base.BaseViewModel
import com.example.instagram.utils.NetworkHelper
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel(
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val databaseService: DatabaseService,
    private val networkService: NetworkService
) : BaseViewModel(compositeDisposable, networkHelper) {

    companion object {
        const val TAG = "MainViewModel"
    }

    private val _data = MutableLiveData<String>()
    val data: LiveData<String>
        get() = _data
    val user = MutableLiveData<User>()
    val allUser = MutableLiveData<List<User>>()
    val allAddress = MutableLiveData<List<Address>>()

    private var users: List<User> = emptyList()
    private var addresses: List<Address> = ArrayList()

    init {
        // add dummy users in the database
        compositeDisposable.add(
            databaseService.userDao()
                .count()
                .flatMap {
                    if (it == 0)
                        databaseService
                            .addressDao()
                            .insertMany(
                                Address(city = "Delhi", country = "India", code = 1),
                                Address(city = "New York", country = "US", code = 2),
                                Address(city = "Berlin", country = "Germany", code = 3),
                                Address(city = "London", country = "UK", code = 4),
                                Address(city = "Bangalore", country = "India", code = 5),
                                Address(city = "Barcelona", country = "Spain", code = 6)
                            )
                            .flatMap { addressIds ->
                                databaseService
                                    .userDao()
                                    .insertMany(
                                        User(
                                            name = "Test 1",
                                            addressId = addressIds[0],
                                            dateOfBirth = Date(959684579)
                                        ),
                                        User(
                                            name = "Test 1",
                                            addressId = addressIds[1],
                                            dateOfBirth = Date(959684579)
                                        ),
                                        User(
                                            name = "Test 1",
                                            addressId = addressIds[2],
                                            dateOfBirth = Date(959684579)
                                        ),
                                        User(
                                            name = "Test 1",
                                            addressId = addressIds[3],
                                            dateOfBirth = Date(959684579)
                                        ),
                                        User(
                                            name = "Test 1",
                                            addressId = addressIds[4],
                                            dateOfBirth = Date(959684579)
                                        ),
                                        User(
                                            name = "Test 1",
                                            addressId = addressIds[5],
                                            dateOfBirth = Date(959684579)
                                        )
                                    )
                            }
                    else Single.just(0)
                }
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        Log.d(TAG, "Users present in db $it")
                    },
                    {
                        Log.d(TAG, it.toString())
                    }
                )
        )

    }

    override fun onCreate() {
        _data.value = "Main View Model"
    }

    fun getAllUser() {
        compositeDisposable.add(
            databaseService.userDao()
                .getAllUsers()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        users = it
                        allUser.postValue(it)
                    },
                    {
                        Log.d(TAG, it.toString())
                    }
                )
        )
    }

    fun getAllAddress() {
        compositeDisposable.add(
            databaseService.addressDao()
                .getAllAddresses()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        addresses = it
                        allAddress.postValue(it)
                    },
                    {
                        Log.d(TAG, it.toString())
                    }
                )
        )
    }


    fun getUser(id: Long) {
        compositeDisposable.add(
            databaseService.userDao()
                .getUserById(id)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        user.postValue(it)
                    },
                    {
                        Log.d(TAG, it.toString())
                    }
                )
        )
    }

    fun deleteUser() {
        if (users.isNotEmpty())
            compositeDisposable.add(
                databaseService.userDao()
                    .delete(users[0])
                    .flatMap {
                        databaseService.userDao().getAllUsers()
                    }
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            allUser.postValue(it)
                        },
                        {
                            Log.d(TAG, it.toString())
                        }
                    )
            )
    }

    fun deleteAddress() {
        if (addresses.isNotEmpty())
            compositeDisposable.add(
                databaseService.addressDao()
                    .delete(addresses[0])
                    .flatMap {
                        databaseService.userDao().getAllUsers()
                    }
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            allUser.postValue(it)
                        },
                        {
                            Log.d(TAG, it.toString())
                        }
                    )
            )
    }

    fun onDestroy() {
        compositeDisposable.clear()
    }

}