package com.example.scaffold

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scaffold.ui.theme.ScaffoldTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScaffoldTheme {
                var counter by remember { mutableStateOf(0) }

                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = {
                                Text(text = "Counter App")
                            }
                        )
                    },
                    content = { innerPadding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CurrentCount(count = counter.toString())
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { counter++ }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    }
                )
            }
        }

        Log.d("LOG", "onCreate")
    }

    override fun onStart() {
        super.onStart()
        print("onStart")
        Log.d("LOG", "onStart")
    }

    override fun onResume() {
        super.onResume()
        print("onResume")
        Log.d("LOG", "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        print("onRestart")
        Log.d("LOG", "onRestart")
        Log.i("TAG", "")
        Log.e("TAG", "")
        Log.wtf("TAG", "")
    }

    override fun onPause() {
        super.onPause()
        print("onPause")
        Log.d("LOG", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LOG", "onStop")
        print("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LOG", "onDestroy")
        print("onDestroy")
    }
}

@Composable
fun CurrentCount(count: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = count,
        fontSize = 36.sp
    )
}