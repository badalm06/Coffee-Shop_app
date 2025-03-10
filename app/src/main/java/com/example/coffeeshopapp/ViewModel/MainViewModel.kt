package com.example.coffeeshopapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.coffeeshopapp.model.ItemsModel
import com.example.coffeeshopapp.repository.MainRepository

class MainViewModel: ViewModel() {
    private val repository: MainRepository by lazy {
        MainRepository()
    }


    fun loadFiltered(id: Int): LiveData<MutableList<ItemsModel>> {
        return repository.loadFiltered(id)
    }
}