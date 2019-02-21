package net.ukr.vlsv.ippon_secretar.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.ukr.vlsv.ippon_secretar.annotations.OpenClass
import net.ukr.vlsv.ippon_secretar.coroutines.CoroutineDispatchersProvider
import net.ukr.vlsv.ippon_secretar.livedata.SingleLiveEvent
import net.ukr.vlsv.ippon_secretar.livedata.ViewAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

@OpenClass
abstract class BaseViewModel(
        application: Application,
        protected val dispatchersProvider: CoroutineDispatchersProvider
) : AndroidViewModel(application), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = dispatchersProvider.Default

    //region LiveData

    @Suppress("PropertyName")
    protected val _viewAction = SingleLiveEvent<ViewAction>()
    val viewAction: LiveData<ViewAction> get() = _viewAction

    @Suppress("MemberVisibilityCanBePrivate", "PropertyName")
    protected val _showSpinner = MutableLiveData<Boolean>()
    val showSpinner: LiveData<Boolean> get() = _showSpinner
    //endregion

    override fun onCleared() {
        coroutineContext.cancel()
        super.onCleared()
    }
}
