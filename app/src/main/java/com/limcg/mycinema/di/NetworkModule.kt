package com.limcg.mycinema.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.limcg.mycinema.BuildConfig
import com.limcg.mycinema.repository.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Named("base_url")
    fun provideBaseUrl() : String = "https://api.themoviedb.org"

    @Provides
    @Named("api_key")
    fun provideApiKey() : String = "328c283cd27bd1877d9080ccb1604c91"

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.apply { loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY }
        OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
    } else OkHttpClient
            .Builder()
            .build()

    @Singleton
    @Provides
    fun provideGson() : Gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setLenient()
            .create()

    @Singleton
    @Provides
    fun provideRetrofit(@Named("base_url") baseUrl : String,
                        gson : Gson,
                        okHttpClient: OkHttpClient) : Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) : ApiService = retrofit.create(ApiService::class.java)

}