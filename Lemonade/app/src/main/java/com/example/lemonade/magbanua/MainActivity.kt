package com.example.lemonade.magbanua

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.magbanua.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Lemonade()
                }
            }
        }
    }
}

@Composable
fun Lemonade() {
    var currentStep by remember { mutableStateOf(1) }
    var squeezeCount by remember { mutableStateOf(0) }

    when (currentStep) {
        1 -> TextAndImage(
            drawableId = R.drawable.lemon_tree,
            contentDescription = stringResource(R.string.lemon_tree),
            text = stringResource(R.string.tap_the_lemon_tree_to_select_a_lemon),
            onClick = {
                currentStep = 2
                squeezeCount = (2..4).random()
            }
        )
        2 -> TextAndImage(
            drawableId = R.drawable.lemon_squeeze,
            contentDescription = stringResource(R.string.lemon),
            text = stringResource(R.string.keep_tapping_the_lemon_to_squeeze_it),
            onClick = {
                --squeezeCount

                if (squeezeCount == 0) {
                    currentStep = 3
                }
            }
        )
        3 -> TextAndImage(
            drawableId = R.drawable.lemon_drink,
            contentDescription = stringResource(R.string.glass_of_lemonade),
            text = stringResource(R.string.tap_the_lemonade_to_drink_it),
            onClick = {
                currentStep = 4
            }
        )
        4 -> TextAndImage(
            drawableId = R.drawable.lemon_restart,
            contentDescription = stringResource(R.string.empty_glass),
            text = stringResource(R.string.tap_the_empty_glass_to_start_again),
            onClick = {
                currentStep = 1
            }
        )
    }
}

@Composable
fun TextAndImage(
    drawableId: Int,
    text: String,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color.Green)
                .clickable(onClick = onClick)
        ) {
            Image(
                modifier = modifier.padding(20.dp),
                painter = painterResource(drawableId),
                contentDescription = contentDescription
            )
        }
        Spacer(modifier = modifier.height(16.dp))
        Text(
            text = text,
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    LemonadeTheme {
        Lemonade()
    }
}