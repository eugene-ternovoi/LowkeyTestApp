package com.ujinturnaway.lowkeytestapp.domain.network

class AuthProviderImpl: AuthProvider {
    override fun provideAuthHeader(): String {
        // It's not secure but i don't care about this secret
        return "l7sBHyFwZp7XNcVA3l3W1D8AMllWDLWbPbyj6BHHss313dPPSixxWinq"
    }
}