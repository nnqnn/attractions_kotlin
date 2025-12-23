package com.nnqnn.attractions.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nnqnn.attractions.domain.AttractionsRepository
import com.nnqnn.attractions.domain.ThemeManager
import com.nnqnn.attractions.model.Attraction
import com.nnqnn.attractions.model.AttractionCategory

class AttractionsViewModel(
    private val repository: AttractionsRepository,
    private val themeManager: ThemeManager
) : ViewModel() {

    private val _favorites = MutableLiveData<Set<Int>>(emptySet())
    val favorites: LiveData<Set<Int>> = _favorites

    private val _query = MutableLiveData("")
    private val _category = MutableLiveData<AttractionCategory?>(null)
    private val _favoritesOnly = MutableLiveData(false)

    private val _items = MutableLiveData<List<Attraction>>(repository.getAll())
    val items: LiveData<List<Attraction>> = _items

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
        val updated = (_favorites.value ?: emptySet()).let { set ->
            if (set.contains(id)) set - id else set + id
        }
        _favorites.value = updated
        refresh()
    }

    fun applyTheme() = themeManager.applyCurrent()
    fun toggleTheme() = themeManager.toggleDark()
    fun isDark(): Boolean = themeManager.isDark()

    private fun refresh() {
        val q = _query.value ?: ""
        val cat = _category.value
        val favs = _favorites.value ?: emptySet()
        val favOnly = _favoritesOnly.value ?: false
        _items.value = repository.filter(q, cat, favs, favOnly)
    }
}

