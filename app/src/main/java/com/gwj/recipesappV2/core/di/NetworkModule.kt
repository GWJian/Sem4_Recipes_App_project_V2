package com.gwj.recipesappV2.core.di

import com.gwj.recipesappV2.BuildConfig
import com.gwj.recipesappV2.data.api.RecipeApi
import com.gwj.recipesappV2.core.util.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private val logging = Logger()
    private val client = OkHttpClient().newBuilder().addInterceptor(logging).build()

    @Provides
    @Singleton
    fun provideOkFoodApi(): RecipeApi {
        // Create a new Retrofit instance
        return Retrofit.Builder()
            // Set the API base URL
            .baseUrl(BuildConfig.BASE_URL)
            // Add Gson converter factory to parse JSON responses
            .addConverterFactory(GsonConverterFactory.create())
            // Set OkHttp client
            .client(client)
            .build()
            // Create API implementation
            .create(RecipeApi::class.java)
    }

}