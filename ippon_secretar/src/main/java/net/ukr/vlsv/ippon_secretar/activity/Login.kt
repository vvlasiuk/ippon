package net.ukr.vlsv.ippon_secretar.activity

import android.app.FragmentTransaction
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_login.view.*
import net.ukr.vlsv.ippon_secretar.R
import net.ukr.vlsv.ippon_library.R.animator.*
import net.ukr.vlsv.ippon_secretar.IpponApi.IpponRepository
import net.ukr.vlsv.ippon_secretar.IpponApi.data.ConstantsValue
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import java.io.IOException
import retrofit2.Response


class Login: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        view.btn_login.setOnClickListener{

            val ipponRepository = IpponRepository()

            // ГЕТ ЗАПРОС НЕ УДАЛЯТЬ
//            ipponRepository.getConstantValue("version").enqueue(object : Callback<ConstantsValue> {
//                override fun onResponse(
//                    call: Call<ConstantsValue>,
//                    response: Response<ConstantsValue>
//                ) {
//                    if (response.isSuccessful) {
//                        val rezult = response.body()!!.value[0].value
//                        if (rezult < 200) {
//                        }
////                        else // error
//                    }
//                }
//                override fun onFailure(call: Call<ConstantsValue>, t: Throwable) {
//                    val gg = true
//                }
//            })
            // ГЕТ ЗАПРОС НЕ УДАЛЯТЬ


            activity!!.supportFragmentManager.beginTransaction()
                .setCustomAnimations(net.ukr.vlsv.ippon_library.R.animator.slide_in_right, net.ukr.vlsv.ippon_library.R.animator.slide_out_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.frameLayoutMain, ShortSettings())
                .addToBackStack("login")
                .commit()
        }

        return view
    }
}
