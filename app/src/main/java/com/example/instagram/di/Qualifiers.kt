package com.example.instagram.di

import javax.inject.Qualifier
import javax.inject.Scope

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class DatabaseInfo
@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class NetworkInfo
@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class ApplicationContext

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class ActivityContext
