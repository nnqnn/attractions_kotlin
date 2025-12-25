package com.nnqnn.attractions.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nnqnn.attractions.domain.AttractionsRepository
import com.nnqnn.attractions.domain.FavoritesStore
import com.nnqnn.attractions.domain.ThemeManager
import com.nnqnn.attractions.domain.WeatherRepository
import com.nnqnn.attractions.model.Attraction
import com.nnqnn.attractions.model.AttractionCategory
import com.nnqnn.attractions.model.MapBounds
import com.nnqnn.attractions.model.WeatherInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AttractionsViewModel(
    private val repository: AttractionsRepository,
    private val themeManager: ThemeManager,
    private val favoritesStore: FavoritesStore,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _favorites = MutableLiveData<Set<Int>>(emptySet())
    val favorites: LiveData<Set<Int>> = _favorites

    private val _weather = MutableLiveData<WeatherInfo?>(null)
    val weather: LiveData<WeatherInfo?> = _weather

    private val _query = MutableLiveData("")
    private val _category = MutableLiveData<AttractionCategory?>(null)
    private val _favoritesOnly = MutableLiveData(false)

    private val _items = MutableLiveData<List<Attraction>>(repository.getAll())
    val items: LiveData<List<Attraction>> = _items

    private val _bounds = MutableLiveData(repository.bounds())
    val bounds: LiveData<MapBounds> = _bounds

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _favorites.postValue(favoritesStore.load())
        }
        refreshWeather()
    }

    fun setQuery(value: String) {
        _query.value = value
        refresh()
    }

    fun setCategory(category: AttractionCategory?) {
        _category.value = category
        refresh()
    }

    fun toggleFavoritesOnly() {
        _favoritesOnly.value = !(_favoritesOnly.value ?: false)
        refresh()
    }

    fun toggleFavorite(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val current = _favorites.value ?: emptySet()
            val updated = if (current.contains(id)) {
                favoritesStore.remove(id)
                current - id
            } else {
                favoritesStore.add(id)
                current + id
            }
            _favorites.postValue(updated)
            refreshOnMain()
        }
    }

    fun applyTheme() = themeManager.applyCurrent()
    fun toggleTheme() = themeManager.toggleDark()
    fun isDark(): Boolean = themeManager.isDark()

    fun refreshWeather() {
        viewModelScope.launch {
            _weather.value = weatherRepository.getWeather()
        }
    }

    private fun refresh() {
        val q = _query.value ?: ""
        val cat = _category.value
        val favs = _favorites.value ?: emptySet()
        val favOnly = _favoritesOnly.value ?: false
        _items.value = repository.filter(q, cat, favs, favOnly)
    }

    private fun refreshOnMain() {
        viewModelScope.launch(Dispatchers.Main) {
            refresh()
        }
    }
}

