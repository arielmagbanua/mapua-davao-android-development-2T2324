package com.example.businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusinessCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BusinessCard()
                }
            }
        }
    }
}

@Composable
fun BusinessCard(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
    ) {
        Spacer(Modifier.width(20.dp))
        Spacer(Modifier.width(20.dp))
        ContactProfile()
        Spacer(Modifier.width(20.dp))
        ContactInformation()
    }
}

@Composable
fun ContactProfile(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.android_logo),
            contentDescription = null,
            modifier = Modifier
                .background(Color.DarkGray)
                .width(150.dp)

        )
        Text(
            text = "Jennifer Doe",
            fontSize = 40.sp
        )
        Text(
            text = "Android Developer Extraordinaire",
            color = colorResource(R.color.dark_green),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ContactInformation(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = null,
                    tint = colorResource(R.color.dark_green)
                )
                Text(text = stringResource(R.string.phone_number))
            }
            Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    tint = colorResource(R.color.dark_green)
                )
                Text(text = stringResource(R.string.social_handle))
            }
            Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    tint = colorResource(R.color.dark_green)
                )
                Text(text = stringResource(R.string.email))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    BusinessCardTheme {
        BusinessCard()
    }
}
