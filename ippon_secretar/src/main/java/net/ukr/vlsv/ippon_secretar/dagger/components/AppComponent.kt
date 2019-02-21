package net.ukr.vlsv.ippon_secretar.dagger.components

import net.ukr.vlsv.ippon_secretar.activities.BaseActivity
import net.ukr.vlsv.ippon_secretar.dagger.modules.NetworkModule
import net.ukr.vlsv.ippon_secretar.dagger.modules.SystemModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SystemModule::class, NetworkModule::class])
interface AppComponent {

    fun inject(activity: BaseActivity)
}
