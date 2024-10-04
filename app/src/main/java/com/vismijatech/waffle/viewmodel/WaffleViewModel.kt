package com.vismijatech.waffle.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.solana.mobilewalletadapter.clientlib.ActivityResultSender
import com.solana.mobilewalletadapter.clientlib.MobileWalletAdapter
import com.solana.mobilewalletadapter.clientlib.RpcCluster
import com.solana.mobilewalletadapter.clientlib.TransactionResult
import com.vismijatech.waffle.usecase.Connected
import com.vismijatech.waffle.usecase.NotConnected
import com.vismijatech.waffle.usecase.WalletConnectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Vivek Sawant on 04-10-2024.
 */

data class WalletViewState(
    val isLoading: Boolean = false,
    val canTransact: Boolean = false,
    val solBalance: Double = 0.0,
    val userAddress: String = "",
    val userLabel: String = "",
    val noWallet: Boolean = false
)

@HiltViewModel
class WaffleViewModel @Inject constructor(
    private val walletAdapter: MobileWalletAdapter,
    private val walletConnectionUseCase: WalletConnectionUseCase
): ViewModel() {
    private val _state = MutableStateFlow(WalletViewState())

    val viewState: StateFlow<WalletViewState>
        get() = _state

    init {
        viewModelScope.launch {
            walletConnectionUseCase.walletDetails
                .collect{walletDetails ->
                    val detailState = when (walletDetails){
                        is Connected -> {
                            WalletViewState(
                                isLoading = false,
                                canTransact = true,
                                solBalance = 0.0,
                                userAddress = walletDetails.publicKey,
                                userLabel = walletDetails.accountLabel
                            )
                        }
                        is NotConnected -> WalletViewState()
                    }
                    _state.value = detailState
                }
        }
    }

    fun connect(
        identityUri: Uri,
        iconUri: Uri,
        identityName: String,
        activityResultSender: ActivityResultSender
    ) {
        viewModelScope.launch {
            val result = walletAdapter.transact(activityResultSender){
                authorize(
                    identityUri,
                    iconUri,
                    identityName,
                    rpcCluster = RpcCluster.Devnet
                )
            }

            when(result) {
                is TransactionResult.Success -> {
                    walletConnectionUseCase.persistConnection(
                        result.payload.publicKey,
                        result.payload.accountLabel ?: "",
                        result.payload.authToken
                    )
                }
                is TransactionResult.NoWalletFound -> {
                    _state.update {
                        _state.value.copy(
                            noWallet = true,
                            canTransact = true
                        )
                    }
                }
                is TransactionResult.Failure -> {

                }
            }
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            walletConnectionUseCase.clearConnection()
        }
    }
}