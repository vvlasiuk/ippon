package net.ukr.vlsv.ippon_secretar.application

import android.app.Application
import net.ukr.vlsv.ippon_secretar.dagger.components.AppComponent
import net.ukr.vlsv.ippon_secretar.dagger.components.DaggerAppComponent
import net.ukr.vlsv.ippon_secretar.dagger.modules.SystemModule

open class AppClass : Application() {

    companion object {

        lateinit var component: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()
        component = buildDaggerComponent()
    }

    protected open fun buildDaggerComponent(): AppComponent {
        return DaggerAppComponent.builder().systemModule(SystemModule(this)).build()
    }
}