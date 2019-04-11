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
import net.ukr.vlsv.ippon_secretar.IpponApi.RefCompetitionsResponse
import net.ukr.vlsv.ippon_secretar.IpponApi.RefDeskResponse
import net.ukr.vlsv.ippon_secretar.IpponApi.RefJudgesNumberResponse
import net.ukr.vlsv.ippon_secretar.activities.ListHatActivity
import net.ukr.vlsv.ippon_secretar.activities.MainActivity
import net.ukr.vlsv.ippon_secretar.data_base.table.CompetitionsTable
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

    private val _competitions = MutableLiveData<Int>()
    val competitions: LiveData<Int> get() = _competitions

    private val _listCompetitions = MutableLiveData<MutableList<CompetitionsTable>>()
    val listCompetitions: LiveData<MutableList<CompetitionsTable>> get() = _listCompetitions

    init {
        update_Desk()
        update_JudgesNumber()
        update_Competitions()
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

    fun update_Competitions() {
        val listCompetitions = db_Repository.getListCompetitions()
        var idCompetitions   = db_Repository.competitions_ID
        var findCompetitions = listCompetitions.find{ it -> it.id == idCompetitions}

        if (findCompetitions == null) {
            if (listCompetitions.count() > 0) {findCompetitions = listCompetitions[0]}
        }

        _listCompetitions.value = listCompetitions

        if (findCompetitions != null) {
            var indexCompetitions = listCompetitions.indexOf(findCompetitions)
            if (indexCompetitions == -1) indexCompetitions = 0
            _competitions.value = indexCompetitions
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
        val listCompetitions_API = ipponRepository_1C.getRefCompetitionsList(user.login_user_name, user.login_password).await()

        try {
            ShortSettingsSync_DeskTable(listDesk_API)
            ShortSettingsSync_JudgesNumber(listJudgesNumber_API)
            ShortSettingsSync_Competitions(listCompetitions_API)

        } catch (e: Exception) {
            withContext(dispatchersProvider.Main) {}
            messageUtils.showError(e)
        }

        withContext(dispatchersProvider.Main) { // update text
            update_Desk()
            update_JudgesNumber()
            update_Competitions()
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

    private fun ShortSettingsSync_Competitions(list_API: RefCompetitionsResponse) {
        val list_DB  = db_Repository.getListCompetitions()

        list_API.value.forEach {
            val api_id   = it.Code
            var findItem = list_DB.find{ it -> it.api_id == api_id}

            if (findItem === null) {
                db_Repository.insertCompetitions(CompetitionsTable(0, api_id, it.Description, it.date_from, it.date_to, it.place_id))}
            else {
                findItem.description = it.Description
                db_Repository.updateCompetitions(findItem)}
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

    @MainThread
    fun onShortSettingsCompetitionsUpDown(up: Boolean) {
        val listCompetitions  = db_Repository.getListCompetitions()
        var indexCompetitions = 0
        var findCompetitions  = listCompetitions.find{ it -> it.id == db_Repository.competitions_ID}
        var count             = listCompetitions.count()

        if (count == 0) return

        indexCompetitions = listCompetitions.indexOf(findCompetitions)

        if (indexCompetitions == -1) indexCompetitions = 0

        if (up) {
            indexCompetitions++
            if (indexCompetitions >= count) indexCompetitions = count - 1
        }
        else {
            indexCompetitions--
            if (indexCompetitions < 0) indexCompetitions = 0
        }

        db_Repository.competitions_ID = listCompetitions[indexCompetitions].id

        update_Competitions()
    }

    @MainThread
    fun onShortSettingsSpinnerCompetitions(position: Int) {
        val listCompetitions = db_Repository.getListCompetitions()
        if (listCompetitions.count() == 0) return
        val competitions_ID  = listCompetitions[position].id
        if (db_Repository.competitions_ID != competitions_ID) {
            db_Repository.competitions_ID = competitions_ID
        }
    }

    @MainThread
    fun onShortSettingsPrevClicked() {
        _viewAction.value = ViewAction.Navigate(MainActivity::class.java, MainActivity.REQUEST_CODE)
    }

    @MainThread
    fun onShortSettingsNextClicked() {
        _viewAction.value = ViewAction.Navigate(ListHatActivity::class.java, ListHatActivity.REQUEST_CODE)
    }

}