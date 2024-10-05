package com.vismijatech.waffle.injection

import com.solana.mobilewalletadapter.clientlib.RpcCluster

/**
 * Created by Vivek Sawant on 04-10-2024.
 */
interface IRpcConfig {
    val solanaRpcUrl: String
    val rpcCluster: RpcCluster
}