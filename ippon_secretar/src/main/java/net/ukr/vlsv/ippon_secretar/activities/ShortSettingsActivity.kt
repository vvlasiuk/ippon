package net.ukr.vlsv.ippon_secretar.activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import net.ukr.vlsv.ippon_secretar.R
import net.ukr.vlsv.ippon_secretar.viewmodels.ShortSettingsViewModel
import kotlinx.android.synthetic.main.activity_short_settings.*
import net.ukr.vlsv.ippon_secretar.cache.SPCache

class ShortSettingsActivity : BaseActivity(), View.OnClickListener {

    companion object {
        const val REQUEST_CODE = 118 //117
    }

    override val viewModel: ShortSettingsViewModel by viewModelDelegate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_short_settings)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        viewModel.listDeskNumber.observe(this, Observer {
            val adapter = ArrayAdapter<String>(this, R.layout.spinner_item, R.id.tv_spiner_item)
            it.forEach {adapter.add(it.description)}
            if (adapter.count == 0) {adapter.add("Номер столу")}
            spinner_desk_number.adapter = adapter
        })

        viewModel.deskNumber.observe(this, Observer {
            spinner_desk_number.setSelection(it)
        })

        viewModel.listJudgesNumber.observe(this, Observer {
            val adapter = ArrayAdapter<String>(this, R.layout.spinner_item, R.id.tv_spiner_item)
            it.forEach {adapter.add(it.description)}
            if (adapter.count == 0) {adapter.add("Кількість суддів")}
            spinner_judges_number.adapter = adapter
        })

        viewModel.judgesNumber.observe(this, Observer {
            spinner_judges_number.setSelection(it)
        })
    }

    override fun onContentChanged() {
        super.onContentChanged()
            btn_short_settings_sync.setOnClickListener(this)

            btn_desk_number_up.setOnClickListener(this)
            btn_desk_number_down.setOnClickListener(this)

            btn_judges_number_up.setOnClickListener(this)
            btn_judges_number_down.setOnClickListener(this)

            spinner_desk_number.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, v: View, position: Int, id: Long) {
                    viewModel.onShortSettingsSpinnerDeskNumber(position)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        spinner_judges_number.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, v: View, position: Int, id: Long) {
                viewModel.onShortSettingsSpinnerJudgesNumber(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
          }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_short_settings_sync -> viewModel.onShortSettingsSyncClicked()

            R.id.btn_desk_number_up      -> viewModel.onShortSettingsDeskNumberUpDown(true)
            R.id.btn_desk_number_down    -> viewModel.onShortSettingsDeskNumberUpDown(false)

            R.id.btn_judges_number_up    -> viewModel.onShortSettingsJudgesNumberUpDown(true)
            R.id.btn_judges_number_down  -> viewModel.onShortSettingsJudgesNumberUpDown(false)
        }
    }


}
