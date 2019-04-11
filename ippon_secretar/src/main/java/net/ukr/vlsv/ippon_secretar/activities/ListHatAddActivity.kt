package net.ukr.vlsv.ippon_secretar.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list_hat_add.*
import net.ukr.vlsv.ippon_secretar.R
import net.ukr.vlsv.ippon_secretar.adapters.SitkaAdapter
import net.ukr.vlsv.ippon_secretar.cache.SPCache
import net.ukr.vlsv.ippon_secretar.data_base.table.HatsTable
import net.ukr.vlsv.ippon_secretar.data_base.table.SitkaTable
import net.ukr.vlsv.ippon_secretar.viewmodels.ListHatAddViewModel
import java.io.File
import android.net.Uri
import androidx.room.util.CursorUtil.getColumnIndex
import kotlinx.android.synthetic.main.activity_main.*

class ListHatAddActivity : BaseActivity(), View.OnClickListener {
    val CAMERA_REQUEST = 1

    companion object {
        const val REQUEST_CODE = 104
    }

    override val viewModel: ListHatAddViewModel by viewModelDelegate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_hat_add)

        recycler_view_hats_add.setHasFixedSize(true)
        recycler_view_hats_add.layoutManager = LinearLayoutManager(this)

        viewModel.listSitka.observe(this, Observer {
            recycler_view_hats_add.adapter = SitkaAdapter(it)
        })

        viewModel.categoryName.observe(this, Observer {
            list_hat_add_category_name.setText(it)
        })
//
//
    }

    override fun onContentChanged() {
        super.onContentChanged()

        btn_list_hat_add_cancel.setOnClickListener(this)
        btn_list_hat_add_save.setOnClickListener(this)

        btn_list_hat_add_new_row.setOnClickListener(this)
//        btn_list_hat_add_del_row.setOnClickListener(this)

        btn_list_hat_add_scan.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_list_hat_add_save) {
            viewModel.onClicked_Save(true, HatsTable(0, 0, list_hat_add_category_name.text.toString(), 0, 0, 0, 0, 0))
        }

        when (v.id) {
            R.id.btn_list_hat_add_cancel  -> viewModel.onClicked_Cancel()

            R.id.btn_list_hat_add_new_row -> viewModel.onClicked_NewRow()
//            R.id.btn_list_hat_add_del_row -> viewModel.onClicked_DelRow()

//            R.id.btn_list_hat_add_scan    -> viewModel.fillSitkaFromScan(getLastImagePath()) //)onClicked_Scan()
            R.id.btn_list_hat_add_scan    -> onClicked_Scan()
        }}

    fun onClicked_Scan() {
        val cameraIntent  = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (cameraIntent.resolveActivity(packageManager) != null) {
            var fotoFile: File? = null

            try {

//                val directory = File(Environment.getExternalStorageDirectory(), "images")
//                val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//                val directory = File("/storage/sdcard0/Android/data/net.ukr.vlsv.ippon_secretar/files/scan")
//                val directory = File(Environment.getExternalStorageDirectory(), "IPPON")
//                val directory = File(Environment.getExternalStorageDirectory(), "IPPON")
//                if (!directory.exists()) directory.mkdir()

//                fotoFile = File.createTempFile("sitka_scan", ".jpg", directory)
//                fotoFile = File(directory.absolutePath + File.separator + "sitka_scan.jpg")
//                fotoFile = File(directory, "sitka_scan.jpg")
//                fotoFile = directory + "/sitka_scan.jpg"
//Android/data/net.ukr.vlsv.ippon_secretar/files/Pictures
//                if (fotoFile != null) {
//                    val fotoURI1 = FileProvider.getUriForFile(this, packageName + ".provider", fotoFile)
//                    val fotoURI = URI.create(fotoFile.path)
//                    val fotoURI = Uri.fromFile(fotoFile)
//                    val fotoURI = FileProvider.getUriForFile(this, "net.ukr.vlsv.ippon_secretar.provider", fotoFile)
//                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fotoURI)
                    startActivityForResult(cameraIntent, CAMERA_REQUEST)
//                }
            } catch (e: Exception) {Toast.makeText(this, "Error! " + e.toString(), Toast.LENGTH_SHORT).show()}

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
//            val foto = data!!.getExtras().get("data")
//            val picUri = data!!.data
//            val picUri = getLastImageId()
//            val a=1

//            viewModel.fillSitkaFromScan(getLastImageId())
            viewModel.fillSitkaFromScan(getLastImagePath())

        }
    }

    fun getLastImagePath(): String {
        val id: Int = 0
        var fullPath: String = ""
        val imageColumns = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA)
        val imageOrderBy =  MediaStore.Images.Media._ID + " DESC"
        val imageCursor  = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns, null, null, imageOrderBy)

        if (imageCursor.moveToFirst()) {
            val id = imageCursor.getInt(imageCursor.getColumnIndex(MediaStore.Images.Media._ID))
            fullPath = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA))

            imageCursor.close()
        }

        return fullPath
    }

}
