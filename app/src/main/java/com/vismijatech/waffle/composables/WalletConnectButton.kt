package com.vismijatech.waffle.composables

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.solana.mobilewalletadapter.clientlib.ActivityResultSender
import com.vismijatech.waffle.viewmodel.WaffleViewModel

/**
 * Created by Vivek Sawant on 04-10-2024.
 */
@Composable
fun WalletConnectButton(
    identityUri: Uri,
    iconUri: Uri,
    identityName: String,
    activityResultSender: ActivityResultSender,
    modifier: Modifier,
    waffleViewModel: WaffleViewModel = hiltViewModel()
) {
    val viewState = waffleViewModel.viewState.collectAsState().value
    val pubKey = viewState.userAddress
    val buttonText = when {
        viewState.noWallet -> "Please install a wallet"
        pubKey.isEmpty() -> "Connect wallet"
        viewState.userAddress.isNotEmpty() -> pubKey.take(4).plus("...").plus(pubKey.takeLast(4))
        else -> ""
    }
    Column (modifier = modifier) {
        Button(
            modifier = modifier,
            onClick = {
                if (viewState.userAddress.isEmpty())
                    waffleViewModel.connect(identityUri, iconUri, identityName, activityResultSender)
                else
                    waffleViewModel.disconnect()
            }) {
            Text(
                text = buttonText,
                modifier = modifier.padding(8.dp),
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview
@Composable
fun WalletButton(){
//    WalletConnectButton(modifier = Modifier)
}