package com.example.myapp001adicethrowxml

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT)
        )
        setContent {
            DiceThrowTheme {
                DiceThrowScreen()
            }
        }
    }
}

@Composable
fun DiceThrowTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF6650A4),
            onPrimary = Color.White,
            background = Color(0xFFF5F3FF),
            onBackground = Color(0xFF352060),
            surface = Color(0xFFF5F3FF),
            onSurface = Color(0xFF352060)
        ),
        content = content
    )
}

@Composable
fun DiceThrowScreen(modifier: Modifier = Modifier) {
    val diceSymbols = remember { listOf("⚀", "⚁", "⚂", "⚃", "⚄", "⚅") }
    // The shown face survives rotation. An interrupted roll stops on that face.
    var diceValue by rememberSaveable { mutableIntStateOf(1) }
    var isRolling by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val diceDescription = stringResource(R.string.dice_description, diceValue)

    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier.fillMaxSize().safeDrawingPadding().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.dice_title),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = diceSymbols[diceValue - 1],
                modifier = Modifier.padding(vertical = 24.dp)
                    .semantics { contentDescription = diceDescription },
                fontSize = 120.sp
            )
            Button(
                enabled = !isRolling,
                onClick = {
                    if (!isRolling) {
                        isRolling = true
                        scope.launch {
                            try {
                                repeat(10) {
                                    diceValue = (1..6).random()
                                    delay(250)
                                }
                                diceValue = (1..6).random()
                            } finally {
                                isRolling = false
                            }
                        }
                    }
                }
            ) {
                Text(text = stringResource(R.string.roll), fontSize = 24.sp)
            }
        }
    }
}

@Preview(showBackground = true, locale = "cs", widthDp = 360, heightDp = 640)
@Composable
private fun DiceThrowPreview() {
    DiceThrowTheme { DiceThrowScreen() }
}
