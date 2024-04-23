package com.example.examenarg.data.repository

import com.example.examenarg.data.model.DNSRecord
import com.example.examenarg.data.remote.ApiService
import com.example.examenarg.data.remote.RetrofitInstance
import retrofit2.Call
import retrofit2.Response

class DNSRepository {
    private val apiService: ApiService = RetrofitInstance.retrofit.create(ApiService::class.java)

    // Change return type to Response
    suspend fun getDNSRecords(domain: String): Call<List<DNSRecord>> {
        return apiService.getDNSRecords(domain)
    }
}

