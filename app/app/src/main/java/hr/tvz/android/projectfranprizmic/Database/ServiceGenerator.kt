package hr.tvz.android.projectfranprizmic.Database

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceGenerator {

    val gson: Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") // Ensures parsing of the date format
        .create()

    public fun <S> createService(serviceClass: Class<S>?, baseUrl: String?): S {
        //HttpLoggingInterceptor služi za Logging - može usporavati aplikaciju!
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()
        return retrofit.create(serviceClass)
    }

    /*fun <S> createService(serviceClass: Class<S>?, baseUrl: String?, token: AccessToken?): S? {
        if (token != null) {
            val httpClient = OkHttpClient.Builder()
            val retrofitBuilder = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Accept", "application/json")
                    .header(
                        "Authorization",
                        token.tokenType + " " + token.accessToken
                    )
                    .method(original.method(), original.body())
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            val client = httpClient.build()
            val retrofit = retrofitBuilder.client(client).build()
            return retrofit.create(serviceClass)
        }
        return null
    }*/
}