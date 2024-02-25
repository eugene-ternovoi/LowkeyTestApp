package com.ujinturnaway.lowkeytestapp.domain.network

interface AuthProvider {
    fun provideAuthHeader(): String
}