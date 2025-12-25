package com.nnqnn.attractions.di

import android.app.Application
import androidx.room.Room
import com.nnqnn.attractions.data.local.AppDatabase
import com.nnqnn.attractions.domain.AttractionsRepository
import com.nnqnn.attractions.domain.FavoritesStore
import com.nnqnn.attractions.domain.ThemeManager
import com.nnqnn.attractions.domain.WeatherRepository
import com.nnqnn.weatherlib.WeatherApi
import com.nnqnn.attractions.ui.AttractionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.nnqnn.weatherlib.WeatherNetwork

val appModule = module {
    single { AttractionsRepository(get()) }
    single { ThemeManager(get()) }

    // Network (weather in separate module)
    single<WeatherApi> { WeatherNetwork.createApi() }

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

