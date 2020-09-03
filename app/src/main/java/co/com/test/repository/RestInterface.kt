package co.com.test.repository


import co.com.test.model.UserDataModel
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**
 * @desc Rest API interface handle all API endpoints
 * @author : Mahesh Vayak
 * @required
 **/
interface RestInterface {
    /**
     * @desc user login
     **/
    @POST("login")
    suspend fun login(@Body jsonObject: JsonObject): Response<UserDataModel>






}
