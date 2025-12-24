package com.nnqnn.attractions.di

import android.app.Application
import androidx.room.Room
import com.nnqnn.attractions.data.local.AppDatabase
import com.nnqnn.attractions.domain.AttractionsRepository
import com.nnqnn.attractions.domain.FavoritesStore
import com.nnqnn.attractions.domain.ThemeManager
import com.nnqnn.attractions.domain.WeatherRepository
import com.nnqnn.attractions.network.WeatherApi
import com.nnqnn.attractions.ui.AttractionsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { AttractionsRepository() }
    single { ThemeManager(get()) }

    // Network
    single {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<WeatherApi> { get<Retrofit>().create(WeatherApi::class.java) }

    // DB
    single {
        Room.databaseBuilder(get<Application>(), AppDatabase::class.java, "attractions.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<AppDatabase>().favoriteDao() }
    single { get<AppDatabase>().weatherDao() }
    single { FavoritesStore(get()) }
    single { WeatherRepository(get(), get()) }

    viewModel { AttractionsViewModel(get(), get(), get(), get()) }
}

