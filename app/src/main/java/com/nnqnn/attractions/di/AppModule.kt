package com.nnqnn.attractions.di

import com.nnqnn.attractions.domain.AttractionsRepository
import com.nnqnn.attractions.domain.ThemeManager
import com.nnqnn.attractions.ui.AttractionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { AttractionsRepository() }
    single { ThemeManager(get()) }
    viewModel { AttractionsViewModel(get(), get()) }
}

