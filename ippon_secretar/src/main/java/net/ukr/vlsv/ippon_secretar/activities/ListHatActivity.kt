package net.ukr.vlsv.ippon_secretar.activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
//import kotlinx.android.synthetic.main.activity_main.*
import net.ukr.vlsv.ippon_secretar.R
import net.ukr.vlsv.ippon_secretar.viewmodels.ListHatViewModel
import kotlinx.android.synthetic.main.activity_list_hat.*
import net.ukr.vlsv.ippon_secretar.adapters.HatsAdapter
import net.ukr.vlsv.ippon_secretar.cache.SPCache

class ListHatActivity : BaseActivity(), View.OnClickListener {

    companion object {
        const val REQUEST_CODE = 103
    }

    override val viewModel: ListHatViewModel by viewModelDelegate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_hat)

        recycler_view_hats.setHasFixedSize(true)
        recycler_view_hats.layoutManager = LinearLayoutManager(this)

        viewModel.listHats.observe(this, Observer {
            recycler_view_hats.adapter = HatsAdapter(it)
//            spinner_desk_number.setSelection(it)
        })


//        supportActionBar?.apply {
//            setDisplayHomeAsUpEnabled(true)
//            setHomeButtonEnabled(true)
//        }

    }

    override fun onContentChanged() {
        super.onContentChanged()

        btn_list_hat_start.setOnClickListener(this)
        btn_list_hat_prev.setOnClickListener(this)

        btn_list_hat_sync.setOnClickListener(this)
        btn_list_hat_add.setOnClickListener(this)
//        btn_list_hat_edit.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_list_hat_prev -> viewModel.onListHatPrevClicked()
            R.id.btn_list_hat_start -> viewModel.onListHatNextClicked()

            R.id.btn_list_hat_sync -> viewModel.onListHatSyncClicked()
            R.id.btn_list_hat_add -> viewModel.onListHatAddClicked()
//            R.id.btn_list_hat_edit -> viewModel.onListHatEditClicked()
        }}

}
