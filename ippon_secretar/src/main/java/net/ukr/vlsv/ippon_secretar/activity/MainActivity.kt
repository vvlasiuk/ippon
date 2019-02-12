package net.ukr.vlsv.ippon_secretar.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_login.*
import net.ukr.vlsv.ippon_library.info.ippon_Info
import net.ukr.vlsv.ippon_secretar.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle(
            ippon_Info().getActivityTitle(
                resources.getString(R.string.app_name),
                net.ukr.vlsv.ippon_secretar.BuildConfig.VERSION_NAME
            )
        )

        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutMain, Login())
            .commit()
    }



    override fun onStart() {
        super.onStart()
    }
}
