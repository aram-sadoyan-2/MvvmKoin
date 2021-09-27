package com.camp.mvvmkoin

import android.app.Application
import com.camp.mvvmkoin.module.DmModule.Companion.apiModule
import com.camp.mvvmkoin.module.DmModule.Companion.databaseModule
import com.camp.mvvmkoin.module.DmModule.Companion.networkModule
import com.camp.mvvmkoin.module.DmModule.Companion.repositoryModule
import com.camp.mvvmkoin.module.DmModule.Companion.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(
                apiModule,
                viewModelModule,
                repositoryModule,
                networkModule,
                databaseModule
            )
        }
    }
}