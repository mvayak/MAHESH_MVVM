package co.com.test.repository

import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.KoinComponent

/**
 * @desc this is class use for pass Authorization token in API header
 * @author : Mahesh Vayak
 * @required
 **/
class AuthorizationInterceptor : Interceptor, KoinComponent {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val builder = request.newBuilder()
        builder.header("Content-Type","application/json")
        builder.header("IMSI","357175048449937")
        builder.header("IMEI","510110406068589")

        request = builder.build() //overwrite old request
        return chain.proceed(request)
    }


}