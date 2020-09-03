package co.com.test.di


import co.com.test.BuildConfig
import co.com.test.viewmodel.UserDatabaseViewModel
import co.com.test.repository.AuthorizationInterceptor
import co.com.test.repository.RestInterface
import co.com.test.viewmodel.LoginViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @desc define view model class
 **/
val viewModelModule = module {
    viewModel { LoginViewModel(get()) }

}

/**
 * @desc create module for web service
 **/
val appModule = module {
    single {
        createWebService<RestInterface>(
            okHttpClient = createHttpClient(),
            baseUrl = BuildConfig.PATH_URL
        )
    }
}

/**
 * @desc create HttpClient object
 **/
fun createHttpClient(): OkHttpClient {
    val client = OkHttpClient.Builder()
    client.addInterceptor(AuthorizationInterceptor())
    client.writeTimeout(60, TimeUnit.MINUTES)
    client.readTimeout(60, TimeUnit.MINUTES)
    return client.build()
}

/**
 * @desc build our Retrofit service
 **/
inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    baseUrl: String
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .client(okHttpClient)
        .build()
    return retrofit.create(T::class.java)
}

/**
 * @desc database view module
 **/
val databaseModule = module {
    single { UserDatabaseViewModel(get()) }
}
/**
 * List of all modules.
 */
val mainAppModules = listOf(
    databaseModule,
    appModule,
    viewModelModule
)