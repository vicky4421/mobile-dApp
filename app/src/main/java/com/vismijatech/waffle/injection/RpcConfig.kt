package com.vismijatech.waffle.injection

import com.solana.mobilewalletadapter.clientlib.RpcCluster
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Vivek Sawant on 04-10-2024.
 */
@Singleton
class RpcConfig @Inject constructor(): IRpcConfig {
    override val solanaRpcUrl = "https://api.devnet.solana.com"
    override val rpcCluster = RpcCluster.Devnet
}