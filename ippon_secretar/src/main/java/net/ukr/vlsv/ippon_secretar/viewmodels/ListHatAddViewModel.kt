package net.ukr.vlsv.ippon_secretar.viewmodels

import android.app.Application
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import androidx.annotation.MainThread
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.ukr.vlsv.ippon_secretar.IpponApi.RefOcrScanResponse
import net.ukr.vlsv.ippon_secretar.activities.ListHatActivity
import net.ukr.vlsv.ippon_secretar.cache.SPCache
import net.ukr.vlsv.ippon_secretar.coroutines.CoroutineDispatchersProvider
import net.ukr.vlsv.ippon_secretar.data_base.DataBaseRepository
import net.ukr.vlsv.ippon_secretar.data_base.table.HatsTable
import net.ukr.vlsv.ippon_secretar.data_base.table.SitkaTable
import net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.IpponRepository_1C
import net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.data.RefDataFiles_1C
import net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.data.RefOcrScan_1C
import net.ukr.vlsv.ippon_secretar.ippon_api.ippon_api_1c.data.RefSitkaOcrScan
import net.ukr.vlsv.ippon_secretar.ippon_api.ocrsdk_api_abbyy.AbbyyRepository
import net.ukr.vlsv.ippon_secretar.livedata.ViewAction
import net.ukr.vlsv.ippon_secretar.utils.MessageUtils
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.util.*


class ListHatAddViewModel  (
        application: Application,
        coroutineDispatchersProvider: CoroutineDispatchersProvider,
        private val messageUtils: MessageUtils,
        private val db_Repository: DataBaseRepository,
        private val ipponRepository_1C: IpponRepository_1C,
        private val abbyyRepository: AbbyyRepository

) : BaseViewModel(application, coroutineDispatchersProvider) {

    private val _listSitka = MutableLiveData<MutableList<SitkaTable>>()
    val listSitka: LiveData<MutableList<SitkaTable>> get() = _listSitka

    private val _categoryName = MutableLiveData<String>()
    val categoryName: LiveData<String> get() = _categoryName

    lateinit var sitkaTable: List<RefSitkaOcrScan>
    lateinit var categoryNameString: String


    init {
        updateRecyclerViewHatsList()
    }

    fun updateRecyclerViewHatsList() {
        if (db_Repository.hats_ID > 0) {
            _listSitka.value = db_Repository.getListSitka()
        }
    }


    @MainThread
    fun onClicked_Cancel() {
        _viewAction.value = ViewAction.Navigate(ListHatActivity::class.java, ListHatActivity.REQUEST_CODE)
    }

    @MainThread
    fun onClicked_Save(saveAndClose: Boolean, hatsTable: HatsTable) {

        SaveHatAndList(hatsTable)

        if (saveAndClose) {
            _viewAction.value = ViewAction.Navigate(ListHatActivity::class.java, ListHatActivity.REQUEST_CODE)
        }
    }

    @MainThread
    fun onClicked_NewRow() {
        _listSitka.value!!.add(SitkaTable(0,0,1,0,0,0,0,0,0,"Спортсмен 1", "Спортсмен 2",0,0,0))
        _listSitka.value = _listSitka.value
    }

    @MainThread
    fun fillSitkaFromScan(path: String) {
        fillSitkaFromScanAsinc(path)

//        if (sitkaTable.count() > 0) {
//            _listSitka.value!!.clear()
//
//            sitkaTable.forEach {
//                _listSitka.value!!.add(SitkaTable(0, 0, 0, 0, 0, 0, 0, 0, 0, it.FIO1, it.FIO2, 0, 0, 0))
//            }
//        }
//
//        _listSitka.value = _listSitka.value

//        _listSitka.value!!.add(SitkaTable(0,0,1,0,0,0,0,0,0,"Спортсмен 1", "Спортсмен 2",0,0,0))
//        _listSitka.value = _listSitka.value
    }
//    @MainThread
//    fun onClicked_DelRow() {
//    }

//    @MainThread
//    fun onClicked_Scan() {
//    }


    fun fillSitkaFromScanAsinc(path: String) = launch {
        withContext(dispatchersProvider.Main) {_showSpinner.value = true}

        try {
            val user   = ipponRepository_1C.userNameAPI_1C

            val imageFile = File(path)
            val fis       = FileInputStream(imageFile)
            val bm        = BitmapFactory.decodeStream(fis)
            val baos      = ByteArrayOutputStream()

            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val bytes = baos.toByteArray()

//            val base64 = android.util.Base64.encode(bytes, android.util.Base64.DEFAULT) //getEncoder().encodeToString(bytes)
            val sBase64 = android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT) //getEncoder().encodeToString(bytes)
            val tskStatus = ipponRepository_1C.addFile(user.login_user_name, user.login_password, RefDataFiles_1C("File 1", sBase64, "image/jpeg")).await()

            val idRef     = tskStatus.Ref_Key
            val rezSitka = ipponRepository_1C.ocr_scan(user.login_user_name, user.login_password, RefOcrScan_1C(idRef)).await()

            val rezOK        = rezSitka.ok
            categoryNameString = rezSitka.Description

            if (rezOK == "1") {
                sitkaTable = rezSitka.sitka
            }
//            db_Repository.insertHats(hatsTable)
        } catch (e: Exception) {
            withContext(dispatchersProvider.Main) {}
            messageUtils.showError(e)
        }

        withContext(dispatchersProvider.Main) {
            _showSpinner.value = false

            _categoryName.value = categoryNameString

            if (sitkaTable.count() > 0) {
                _listSitka.value!!.clear()

                sitkaTable.forEach {
                    _listSitka.value!!.add(SitkaTable(0, 0, 0, 0, 0, 0, 0, 0, 0, it.FIO1, it.FIO2, 0, 0, 0))
                }

                _listSitka.value = _listSitka.value
            }

        }
    }
    fun SaveHatAndList(hatsTable: HatsTable) = launch {
        withContext(dispatchersProvider.Main) {_showSpinner.value = true}

        try {
            db_Repository.insertHats(hatsTable)

            sitkaTable.forEach {
                db_Repository.insertListSitka(SitkaTable(0, 0, 0, 0, 0, 0, 0, 0, 0, it.FIO1, it.FIO2, 0, 0, 0)) //RefSitkaOcrScan
            }
        } catch (e: Exception) {
            withContext(dispatchersProvider.Main) {}
            messageUtils.showError(e)
        }

        withContext(dispatchersProvider.Main) {_showSpinner.value = false}
    }


}