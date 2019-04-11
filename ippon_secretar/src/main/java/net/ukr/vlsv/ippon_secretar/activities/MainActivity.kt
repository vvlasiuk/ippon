package net.ukr.vlsv.ippon_secretar.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
//import kotlinx.android.synthetic.main.activity_location.*
import net.ukr.vlsv.ippon_secretar.R
import net.ukr.vlsv.ippon_secretar.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), View.OnClickListener {

    companion object {
        const val REQUEST_CODE = 101
    }

    override val viewModel: MainViewModel by viewModelDelegate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(viewModel)

        viewModel.userNameAPI_1C.observe(this, Observer {
            login_user_name.setText(it?.login_user_name)
            login_password.setText(it?.login_password)
        })

        setContentView(R.layout.activity_main)
    }

    override fun onContentChanged() {
        super.onContentChanged()
//        btn_change_location.setOnClickListener(this)
        btn_login.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!viewModel.onActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
//            R.id.btn_change_location -> viewModel.onChangeLocationClicked()
            R.id.btn_login -> {
                viewModel.onLoginClicked(login_user_name.text.toString(), login_password.text.toString())
            }
        }
    }
}
