package com.ujinturnaway.lowkeytestapp.domain.network

interface ResponseProcessor<T, R> {
    fun process(response: T): R
}