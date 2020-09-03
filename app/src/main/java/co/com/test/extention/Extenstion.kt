package co.com.test.extention

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import com.google.android.material.snackbar.Snackbar


/**
 * @desc this function use for check internet available or not
 **/
fun Context.isOnline(): Boolean {
    val connMgr = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities = connMgr.getNetworkCapabilities(connMgr.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }

    } else {
        val activeNetworkInfo = connMgr.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
    return false
}


/**
 * method will use for show snackbar
 * @param view - target parent view.
 * @param message - message which you want show
 *  @param actionText -action name by default empty
 *  @param onClickListener - action click callback.
 */
fun View.makeSnackBar(
    message: String,
    actionText: String = "",
    onClickListener: View.OnClickListener? = null

) {
    var snackbar: Snackbar? = null
    if (actionText == "") {
        snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    } else {
        if (onClickListener != null) {
            snackbar = Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(actionText, onClickListener)
            snackbar.setActionTextColor(Color.RED)

        }
    }

    snackbar?.let {
        it.show()
    }

}
