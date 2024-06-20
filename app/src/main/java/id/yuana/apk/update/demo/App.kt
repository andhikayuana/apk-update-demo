package id.yuana.apk.update.demo

import android.app.Application
import id.yuana.apk.update.demo.di.AppModule

class App : Application() {

    lateinit var appModule: AppModule

    override fun onCreate() {
        super.onCreate()
        appModule = AppModule
    }
}