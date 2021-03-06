package net.ukr.vlsv.ippon_secretar.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.StringRes
import net.ukr.vlsv.ippon_secretar.R
import net.ukr.vlsv.ippon_secretar.annotations.OpenClass
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@OpenClass
@Singleton
class MessageUtils @Inject constructor(private val context: Context) {

    @JvmOverloads
    fun showToast(@StringRes message: Int, duration: Int = Toast.LENGTH_LONG) {
        showToast(context.getString(message), duration)
    }

    @JvmOverloads
    fun showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
        Handler(Looper.getMainLooper()).post { Toast.makeText(context, message, duration).show() }
    }

    fun showError(@StringRes message: Int) {
        showToast(message)
    }

    @JvmOverloads
    fun showError(message: String? = null) {
        showToast(message ?: context.getString(R.string.something_was_wrong))
    }

    fun showError(throwable: Throwable) {
        if (throwable is HttpException) {
            showError(throwable.message())
        } else {
            showError(throwable.message)
        }
        throwable.printStackTrace()
    }
}