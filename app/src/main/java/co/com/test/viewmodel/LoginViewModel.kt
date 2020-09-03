package co.com.test.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.com.test.constants.AppConstants
import co.com.test.model.LoginForm
import co.com.test.model.UserDataModel
import co.com.test.repository.RestInterface
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

/**
 * @desc this class will handle API functions
 * @author : Mahesh Vayak
 * @required
 **/
class LoginViewModel constructor(private val restInterface: RestInterface) : BaseViewModel() {
    val successUserResponse = MutableLiveData<UserDataModel>()
    val errorUserResponse = MutableLiveData<ResponseBody>()
    val validationLiveData = MutableLiveData<Int>()
    val buildJsonObjectForLogin=MutableLiveData<JsonObject>()
    val isLoginFormValid = MutableLiveData<Boolean>()
    /**
     *  Method will use for login user.
     */
    fun userLogin(jsonObject: JsonObject) {
        viewModelScope.launch(apiException() + Dispatchers.Main) {
            val response = restInterface.login(jsonObject)

            when (response.code()) {
                AppConstants.API_SUCCESS_CODE -> {
                    successUserResponse.postValue(response.body())
                }
                else -> {
                    errorUserResponse.postValue(response.errorBody())
                }

            }

        }
    }

    /**
     *  Method will use for check form validation.
     */
    fun checkFormValidation(loginForm: LoginForm) {
        when {
            loginForm.isUserNameNotValid() -> validationLiveData.postValue(1)
            loginForm.isPasswordNotValid() -> validationLiveData.postValue(2)
            else -> isLoginFormValid.postValue(true)
        }
    }

    /**
     * @desc Method will use for create JsonObject for user login
     */
    fun makeObjectForLogin(loginForm: LoginForm) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("username", loginForm.username)
        jsonObject.addProperty("password", loginForm.password)
        buildJsonObjectForLogin.postValue(jsonObject)
    }

    /**
     * Clears the [ViewModel] when the [Fragment] or [Activity] is not visible to user.
     */
    fun onDetach() {
        viewModelScope.cancel()
    }




}