package net.ukr.vlsv.ippon_secretar.viewmodels

import android.app.Activity
import android.app.Application
import android.widget.ArrayAdapter
import androidx.annotation.MainThread
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import net.ukr.vlsv.ippon_secretar.R
import net.ukr.vlsv.ippon_secretar.coroutines.CoroutineDispatchersProvider
import net.ukr.vlsv.ippon_secretar.data_base.DataBaseRepository
import net.ukr.vlsv.ippon_secretar.data_base.table.DeskTable
import net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.IpponRepository_1C
import net.ukr.vlsv.ippon_secretar.livedata.ViewAction
import net.ukr.vlsv.ippon_secretar.repositories.ForecastRepository
import net.ukr.vlsv.ippon_secretar.utils.MessageUtils
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.ukr.vlsv.ippon_secretar.IpponApi.RefDeskResponse
import net.ukr.vlsv.ippon_secretar.IpponApi.RefJudgesNumberResponse
import net.ukr.vlsv.ippon_secretar.data_base.table.JudgesNumberTable
import net.ukr.vlsv.ippon_secretar.network.responses.LoginResponse


class ShortSettingsViewModel(
        application: Application,
        coroutineDispatchersProvider: CoroutineDispatchersProvider,
        private val ipponRepository_1C: IpponRepository_1C,
        private val messageUtils: MessageUtils,
        private val db_Repository: DataBaseRepository

) : BaseViewModel(application, coroutineDispatchersProvider) {

    private val _deskNumber = MutableLiveData<Int>()
    val deskNumber: LiveData<Int> get() = _deskNumber

    private val _listDeskNumber = MutableLiveData<MutableList<DeskTable>>()
    val listDeskNumber: LiveData<MutableList<DeskTable>> get() = _listDeskNumber

    private val _judgesNumber = MutableLiveData<Int>()
    val judgesNumber: LiveData<Int> get() = _judgesNumber

    private val _listJudgesNumber = MutableLiveData<MutableList<JudgesNumberTable>>()
    val listJudgesNumber: LiveData<MutableList<JudgesNumberTable>> get() = _listJudgesNumber

    init {
        update_Desk()
        update_JudgesNumber()
    }

    fun update_Desk() {
        val listDesk = db_Repository.getListDesk()
        var idDesk   = db_Repository.desk_ID
        var findDesk = listDesk.find{ it -> it.id == idDesk}

        if (findDesk == null) {
            if (listDesk.count() > 0) {findDesk = listDesk[0]}
        }

        _listDeskNumber.value = listDesk

        if (findDesk != null) {
            var indexDesk = listDesk.indexOf(findDesk)
            if (indexDesk == -1) indexDesk = 0
            _deskNumber.value = indexDesk
        }

    }

    fun update_JudgesNumber() {
        val listJudgesNumber = db_Repository.getListJudgesNumber()
        var idJudgesNumber   = db_Repository.judgesNumber_ID
        var findJudgesNumber = listJudgesNumber.find{ it -> it.id == idJudgesNumber}

        if (findJudgesNumber == null) {
            if (listJudgesNumber.count() > 0) {findJudgesNumber = listJudgesNumber[0]}
        }

        _listJudgesNumber.value = listJudgesNumber

        if (findJudgesNumber != null) {
            var indexJudgesNumber = listJudgesNumber.indexOf(findJudgesNumber)
            if (indexJudgesNumber == -1) indexJudgesNumber = 0
            _judgesNumber.value = indexJudgesNumber
        }

    }

    @MainThread
    fun onShortSettingsSyncClicked() {
        ShortSettingsSync()
    }

    private fun ShortSettingsSync() = launch {

        withContext(dispatchersProvider.Main) {_showSpinner.value = true}

        val user                 = ipponRepository_1C.userNameAPI_1C
        val listDesk_API         = ipponRepository_1C.getRefDeskList(user.login_user_name, user.login_password).await()
        val listJudgesNumber_API = ipponRepository_1C.getRefJudgesNumberList(user.login_user_name, user.login_password).await()

        try {
            // sync
            ShortSettingsSync_DeskTable(listDesk_API)
            ShortSettingsSync_JudgesNumber(listJudgesNumber_API)


        } catch (e: Exception) {
            withContext(dispatchersProvider.Main) {
                // _weatherData.value = null
            }
            messageUtils.showError(e)
        }

        // update text
        withContext(dispatchersProvider.Main) {
            update_Desk()
            update_JudgesNumber()
        }

        withContext(dispatchersProvider.Main) {_showSpinner.value = false}
    }

    private fun ShortSettingsSync_DeskTable(listDesk_API: RefDeskResponse) {
        val deskList_DB  = db_Repository.getListDesk()

        listDesk_API.value.forEach {
            val api_id = it.Code
            var deskFind = deskList_DB.find{ it -> it.api_id == api_id}

            if (deskFind === null) {
                db_Repository.insertDesk(DeskTable(0, api_id, it.Description))}
            else {
                deskFind.description = it.Description
                db_Repository.updateDesk(deskFind)}
        }}

    private fun ShortSettingsSync_JudgesNumber(list_API: RefJudgesNumberResponse) {
        val list_DB  = db_Repository.getListJudgesNumber()

        list_API.value.forEach {
            val api_id   = it.Code
            var findItem = list_DB.find{ it -> it.api_id == api_id}

            if (findItem === null) {
                db_Repository.insertJudgesNumber(JudgesNumberTable(0, api_id, it.Description, it.number))}
            else {
                findItem.description = it.Description
                db_Repository.updateJudgesNumber(findItem)}
        }}

    @MainThread
    fun onShortSettingsDeskNumberUpDown(up: Boolean) {
        val listDesk = db_Repository.getListDesk()
        var indexDesk = 0
        var findDesk  = listDesk.find{ it -> it.id == db_Repository.desk_ID}
        var count     = listDesk.count()

        if (count == 0) return

        indexDesk = listDesk.indexOf(findDesk)

        if (indexDesk == -1) indexDesk = 0

        if (up) {
            indexDesk++
            if (indexDesk >= count) indexDesk = count - 1
        }
        else {
            indexDesk--
            if (indexDesk < 0) indexDesk = 0
        }

        db_Repository.desk_ID = listDesk[indexDesk].id

        update_Desk()
    }

    @MainThread
    fun onShortSettingsSpinnerDeskNumber(position: Int) {
        val listDesk = db_Repository.getListDesk()
        if (listDesk.count() == 0) return
        val desk_ID  = listDesk[position].id
        if (db_Repository.desk_ID != desk_ID) {
            db_Repository.desk_ID = desk_ID
        }
    }

    @MainThread
    fun onShortSettingsJudgesNumberUpDown(up: Boolean) {
        val listJudgesNumber  = db_Repository.getListJudgesNumber()
        var indexJudgesNumber = 0
        var findJudgesNumber  = listJudgesNumber.find{ it -> it.id == db_Repository.judgesNumber_ID}
        var count             = listJudgesNumber.count()

        if (count == 0) return

        indexJudgesNumber = listJudgesNumber.indexOf(findJudgesNumber)

        if (indexJudgesNumber == -1) indexJudgesNumber = 0

        if (up) {
            indexJudgesNumber++
            if (indexJudgesNumber >= count) indexJudgesNumber = count - 1
        }
        else {
            indexJudgesNumber--
            if (indexJudgesNumber < 0) indexJudgesNumber = 0
        }

        db_Repository.judgesNumber_ID = listJudgesNumber[indexJudgesNumber].id

        update_JudgesNumber()
    }

    @MainThread
    fun onShortSettingsSpinnerJudgesNumber(position: Int) {
        val listJudgesNumber = db_Repository.getListJudgesNumber()
        if (listJudgesNumber.count() == 0) return
        val judgesNumber_ID  = listJudgesNumber[position].id
        if (db_Repository.judgesNumber_ID != judgesNumber_ID) {
            db_Repository.judgesNumber_ID = judgesNumber_ID
        }
    }

}