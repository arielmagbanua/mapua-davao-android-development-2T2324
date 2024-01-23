package com.example.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.activities.ui.theme.ActivitiesTheme

class OtherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val stringExtra = intent?.getStringExtra("EXTRA_VALUE")

        setContent {
            ActivitiesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    Greeting(stringExtra ?: "Hello Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(message: String, modifier: Modifier = Modifier) {
    Text(
        text = message,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ActivitiesTheme {
        Greeting("Android")
    }
}