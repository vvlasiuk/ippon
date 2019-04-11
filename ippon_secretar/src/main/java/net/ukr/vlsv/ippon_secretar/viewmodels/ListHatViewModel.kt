package net.ukr.vlsv.ippon_secretar.viewmodels

import android.app.Activity
import android.app.Application
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.ukr.vlsv.ippon_secretar.IpponApi.RefHatsResponse
import net.ukr.vlsv.ippon_secretar.activities.ListHatAddActivity
import net.ukr.vlsv.ippon_secretar.activities.ShortSettingsActivity
import net.ukr.vlsv.ippon_secretar.coroutines.CoroutineDispatchersProvider
import net.ukr.vlsv.ippon_secretar.data_base.DataBaseRepository
import net.ukr.vlsv.ippon_secretar.data_base.table.HatsTable
import net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.IpponRepository_1C
import net.ukr.vlsv.ippon_secretar.livedata.ViewAction
import net.ukr.vlsv.ippon_secretar.utils.MessageUtils

class ListHatViewModel (
    application: Application,
    coroutineDispatchersProvider: CoroutineDispatchersProvider,
    private val messageUtils: MessageUtils,
    private val db_Repository: DataBaseRepository,
    private val ipponRepository_1C: IpponRepository_1C

) : BaseViewModel(application, coroutineDispatchersProvider) {

    private val _hats = MutableLiveData<Int>()
    val hats: LiveData<Int> get() = _hats

    private val _listHats = MutableLiveData<MutableList<HatsTable>>()
    val listHats: LiveData<MutableList<HatsTable>> get() = _listHats

    init {
        update_recycler_view_hats()
    }

    fun update_recycler_view_hats() {
        _listHats.value = db_Repository.getListHats()
    }

    @MainThread
    fun onListHatPrevClicked() {
        _viewAction.value = ViewAction.Navigate(ShortSettingsActivity::class.java, ShortSettingsActivity.REQUEST_CODE)
    }

    @MainThread
    fun onListHatNextClicked() {
//        _viewAction.value = ViewAction.Navigate(ShortSettingsActivity::class.java, ShortSettingsActivity.REQUEST_CODE)
    }

    @MainThread
    fun onListHatSyncClicked() {
        ListHatSync()
    }

    @MainThread
    fun onListHatAddClicked() {
//        db_Repository.hats_ID = 0
        _viewAction.value = ViewAction.Navigate(ListHatAddActivity::class.java, ListHatAddActivity.REQUEST_CODE)
    }

    @MainThread
    fun onListHatEditClicked(pos: Int) {
        if (db_Repository.hats_ID > 0) {
            _viewAction.value = ViewAction.Navigate(ListHatAddActivity::class.java, ListHatAddActivity.REQUEST_CODE)
        }
    }

    private fun ListHatSync() = launch {

        withContext(dispatchersProvider.Main) {_showSpinner.value = true}

        val user                 = ipponRepository_1C.userNameAPI_1C
        val listHats_API         = ipponRepository_1C.getRefHatsList(user.login_user_name, user.login_password).await()

        try {
            Sync_HatsTable(listHats_API)

        } catch (e: Exception) {
            withContext(dispatchersProvider.Main) {}
            messageUtils.showError(e)
        }

        withContext(dispatchersProvider.Main) { // update text
            update_recycler_view_hats()
        }

        withContext(dispatchersProvider.Main) {_showSpinner.value = false}
    }

    private fun Sync_HatsTable(listHats_API: RefHatsResponse) {
        val hatsList_DB  = db_Repository.getListHats()

        listHats_API.value.forEach {
            val api_id = it.Code
            var hatsFind = hatsList_DB.find{ it -> it.api_id == api_id}

            if (hatsFind === null) {
                db_Repository.insertHats(HatsTable(0, api_id, it.Description, it.competitions_id, it.categories_id, it.type_competition_id, it.desk_id, it.finish))}
            else {
                hatsFind.description = it.Description
                db_Repository.updateHats(hatsFind)}
        }}
}