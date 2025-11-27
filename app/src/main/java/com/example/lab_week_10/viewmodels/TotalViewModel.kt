package com.example.lab_week_10.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TotalViewModel : ViewModel() {
    private val _total = MutableLiveData<Int>()
    val total: LiveData<Int> = _total

    fun setTotal(newTotal: Int) {
        _total.value = newTotal
    }

    fun incrementTotal() {
        _total.value = _total.value?.plus(1)
    }
}