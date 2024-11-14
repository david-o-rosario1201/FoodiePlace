package edu.ucne.proyectofinalaplicada2.presentation.welcome

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.proyectofinalaplicada2.R
import edu.ucne.proyectofinalaplicada2.ui.theme.color_oro

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WelcomeScreen(
    onNavigateToLogin: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color_oro),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo"
        )
        Text(
            modifier = Modifier
                .padding(top = 40.dp),
            text = "Es momento de comer!!",
            style = TextStyle(
                fontSize = 48.sp,
                fontWeight = FontWeight(400),
                fontStyle = FontStyle.Italic,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
            )
        )

        Button(
            modifier = Modifier
                .padding(top = 90.dp)
                .background(color = Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(size = 20.dp)),
            onClick = onNavigateToLogin,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            )
        ){
            Text(
                text = "Empezar",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight(400),
                    color = color_oro,
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(
        onNavigateToLogin = {}
    )
}