package com.example.companytime

import android.app.Application
import com.example.companytime.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CompanyTimeApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CompanyTimeApp)
            modules(appModules)
        }
    }
}