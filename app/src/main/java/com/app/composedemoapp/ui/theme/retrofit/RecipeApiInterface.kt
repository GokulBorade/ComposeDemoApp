package com.app.composedemoapp.ui.theme.retrofit

import com.app.composedemoapp.ui.theme.Model.ResipeResponse
import com.app.composedemoapp.ui.theme.Repository.DetailedRecipeResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

interface RecipeApiInterface {

    @GET("recipes/complexSearch") // Removed "?number=10" from the path
    suspend fun getAllRecipe(
        @Query("number") number: Int = 100
    ): Response<ResipeResponse>

    @GET("/recipes/715415/information")
    suspend fun getRecipeById() : Response<DetailedRecipeResponse>

    @GET("/recipes/complexSearch")
    suspend fun getRecipesByCountry(
        @Query("cuisine") cuisine : String ,
        @Query("number") number: Int = 100
    ) : Response<ResipeResponse>
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.spoonacular.com/"
    //private const val API_KEY = "87f2a1d29b1c4c62b0e99a47791dbc09"
    private const val API_KEY = "cf4c34102324460283c75ae75c07ca52"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain: Interceptor.Chain ->
                val original: Request = chain.request()
                val originalHttpUrl = original.url

                // Append API key to every request
                val newUrl = originalHttpUrl.newBuilder()
                    .addQueryParameter("apiKey", API_KEY)
                    .build()

                val requestBuilder = original.newBuilder().url(newUrl)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideRecipeApiInterface(retrofit: Retrofit): RecipeApiInterface {
        return retrofit.create(RecipeApiInterface::class.java)
    }
}
//
//object RetrofitClient {
//    private const val BASE_URL = "https://api.spoonacular.com/"
//
//    val api: RecipeApiInterface by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
//            .build()
//            .create(RecipeApiInterface::class.java)
//    }
//
//    private val client = OkHttpClient.Builder()
//        .addInterceptor { chain: Interceptor.Chain ->
//            val original: Request = chain.request()
//            val originalHttpUrl = original.url
//            // Append API key to every request
//            val newUrl = originalHttpUrl.newBuilder()
//                .addQueryParameter("apiKey", "87f2a1d29b1c4c62b0e99a47791dbc09")
//                .build()
//
//            val requestBuilder = original.newBuilder().url(newUrl)
//            val request = requestBuilder.build()
//            chain.proceed(request)
//        }
//        .build()
//}