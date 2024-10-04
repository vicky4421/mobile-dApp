package com.vismijatech.waffle

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solana.mobilewalletadapter.clientlib.ActivityResultSender
import com.vismijatech.waffle.composables.WalletConnectButton
import com.vismijatech.waffle.ui.theme.WaffleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityResultSender = ActivityResultSender(this)

        setContent {
            WaffleTheme {
                // A surface container using the 'background' color from the theme
                WaffleApp(activityResultSender)
            }
        }
    }
}

@Composable
fun WaffleApp(activityResultSender: ActivityResultSender){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            WalletConnectButton(
                identityUri = Uri.parse(stringResource(id = R.string.id_url)),
                iconUri = Uri.parse(stringResource(id = R.string.id_favicon)),
                identityName = stringResource(id = R.string.app_name),
                activityResultSender = activityResultSender,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WaffleTheme {
//        WaffleApp()
    }
}