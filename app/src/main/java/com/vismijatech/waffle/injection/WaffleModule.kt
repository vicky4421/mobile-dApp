package com.vismijatech.waffle.injection

import androidx.lifecycle.ViewModel
import com.solana.mobilewalletadapter.clientlib.MobileWalletAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Created by Vivek Sawant on 04-10-2024.
 */
@Module
@InstallIn(ViewModelComponent::class)
class WaffleModule {
    @Provides
    fun providesMobileWalletAdapter(): MobileWalletAdapter {
        return MobileWalletAdapter()
    }
}