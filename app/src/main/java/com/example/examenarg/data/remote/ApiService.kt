package com.example.examenarg.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.examenarg.data.model.DNSRecord


interface ApiService {
    @GET("dnslookup")
    fun getDNSRecords(@Query("domain") domain: String): Call<List<DNSRecord>>
}
