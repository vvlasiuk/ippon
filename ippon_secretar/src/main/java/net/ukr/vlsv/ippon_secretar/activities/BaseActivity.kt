package net.ukr.vlsv.ippon_secretar.activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import net.ukr.vlsv.ippon_library.info.ippon_Info
import net.ukr.vlsv.ippon_secretar.application.AppClass
import net.ukr.vlsv.ippon_secretar.dialogs.LoadingDialog
import net.ukr.vlsv.ippon_secretar.livedata.ViewAction
import net.ukr.vlsv.ippon_secretar.viewmodels.BaseViewModel
import net.ukr.vlsv.ippon_secretar.viewmodels.ViewModelFactory
import javax.inject.Inject
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import net.ukr.vlsv.ippon_secretar.R
//import net.ukr.vlsv.ippon_secretar.shared_preferences.IpponSharedPreferences


abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected abstract val viewModel: BaseViewModel

//    lateinit var sharedPreferences: IpponSharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        sharedPreferences = IpponSharedPreferences(this)

        setTitle(
            ippon_Info().getActivityTitle(
                resources.getString(R.string.app_name),
                net.ukr.vlsv.ippon_secretar.BuildConfig.VERSION_NAME
            )
        )

        AppClass.component.inject(this)

        viewModel.viewAction.observe(this, Observer {
            if (!onViewAction(it!!)) {
                handleViewActionDefault(it)
            }
        })
        viewModel.showSpinner.observe(this, Observer {
            changeSpinnerDialogState(it == true)
        })
    }

    /**
     * Called when view model posts [ViewAction].
     *
     * @return true if action was handled and default handling
     * implementation does not need to be called, false otherwise
     */
    open fun onViewAction(action: ViewAction): Boolean = false

    private fun handleViewActionDefault(action: ViewAction) {
        when (action) {
            is ViewAction.Navigate -> navigate(action)
            is ViewAction.Finish -> doFinish(action)
        }
    }

    private fun navigate(action: ViewAction.Navigate) {
        val intent = action.buildIntent(this)
        if (action.requestCode == null) {
            startActivity(intent)
        } else {
            startActivityForResult(intent, action.requestCode)
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun changeSpinnerDialogState(isShouldShow: Boolean) {
        if (isShouldShow) {
            LoadingDialog.newInstance().show(supportFragmentManager, LoadingDialog::class.java.name)
        } else {
            (supportFragmentManager.findFragmentByTag(LoadingDialog::class.java.name) as? DialogFragment)?.dismiss()
        }
    }

    private fun doFinish(action: ViewAction.Finish) {
        action.resultCode?.let { setResult(it) }
        finish()
    }

    protected inline fun <reified T : BaseViewModel> viewModelDelegate() =
            object : ReadOnlyProperty<BaseActivity, T> {

                var value: T? = null

                override fun getValue(thisRef: BaseActivity, property: KProperty<*>): T {
                    if (value == null) {
                        value = ViewModelProviders.of(thisRef, viewModelFactory).get(T::class.java)
                    }
                    return value as T
                }
            }
}