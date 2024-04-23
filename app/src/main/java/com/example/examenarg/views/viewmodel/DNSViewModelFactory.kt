package com.example.examenarg.views.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.examenarg.data.repository.DNSRepository

class DNSViewModelFactory(private val repository: DNSRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DNSViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DNSViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
