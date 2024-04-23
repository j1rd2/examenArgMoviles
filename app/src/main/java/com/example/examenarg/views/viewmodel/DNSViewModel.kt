package com.example.examenarg.views.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examenarg.data.model.DNSRecord
import com.example.examenarg.data.repository.DNSRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DNSViewModel(private val repository: DNSRepository) : ViewModel() {
    private val _dnsRecords = MutableLiveData<List<DNSRecord>>()
    val dnsRecords: LiveData<List<DNSRecord>> = _dnsRecords

    fun fetchDNSRecords(domain: String) {
        viewModelScope.launch {
            // Assuming getDNSRecords returns a Retrofit Call, you need to execute it to get Response
            val call = repository.getDNSRecords(domain)
            call.enqueue(object : Callback<List<DNSRecord>> {
                override fun onResponse(call: Call<List<DNSRecord>>, response: Response<List<DNSRecord>>) {
                    if (response.isSuccessful) {
                        // Post value when response is successful
                        _dnsRecords.postValue(response.body() ?: emptyList())
                    } else {
                        // Handle the case where the server responds with an error
                        _dnsRecords.postValue(emptyList())
                    }
                }

                override fun onFailure(call: Call<List<DNSRecord>>, t: Throwable) {
                    // Handle failure, e.g., no internet, server down, etc.
                    _dnsRecords.postValue(emptyList())
                }
            })
        }
    }
}