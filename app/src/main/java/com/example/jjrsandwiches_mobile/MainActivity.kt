package com.example.jjrsandwiches_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jjrsandwiches_mobile.ui.theme.JJRSandwichesMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
        }
            MessageCard("Some content is here")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun MessageCard(content: String){
    Text(text = "text content $content", modifier = Modifier
        .padding(16.dp) // Outer padding; outside background
        .background(color = Color.Green) // Solid element background color
        .padding(16.dp))
}

@Preview(showBackground = true)
@Composable
fun MessageCardPreview(){
    JJRSandwichesMobileTheme {
        MessageCard("Some content is here")
    }
}
