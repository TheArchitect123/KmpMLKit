package com.architect.testclient.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import com.architect.kmpessentials.logging.KmpLogging
import com.architect.neuralKmp.KmpMLAndroid
import com.architect.testclient.Greeting
import com.architect.neuralKmp.smartReply.KmpMLSmartReply

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        KmpMLAndroid.initializeMLKit(this)
        KmpMLSmartReply.getSmartReplyFromBot("Hello there"){
            it.forEach { reply ->
                KmpLogging.writeInfo("SMART_REPLY", reply)
            }

        }
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingView(Greeting().greet())
                }
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
