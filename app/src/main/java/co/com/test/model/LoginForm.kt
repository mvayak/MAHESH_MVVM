package co.com.test.model

import android.text.TextUtils
import android.util.Patterns
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR


class LoginForm : BaseObservable() {

    var username: String? = null
        @Bindable get
        set(username) {
            field = username
            notifyPropertyChanged(BR.username)
        }

    var password: String? = null
        @Bindable get
        set(password) {
            field = password
            notifyPropertyChanged(BR.password)
        }

    fun isUserNameNotValid(): Boolean {
        return TextUtils.isEmpty(username)
    }

    fun isPasswordNotValid(): Boolean {
        return TextUtils.isEmpty(password)
    }


}