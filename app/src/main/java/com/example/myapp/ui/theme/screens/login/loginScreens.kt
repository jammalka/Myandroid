package com.example.myapp.ui.theme.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapp.R
import com.example.myapp.data.AuthViewModel
import com.example.myapp.navigation.ROUTE_REGISTER
import com.example.myapp.ui.theme.screens.register.registerScreen

@Composable
fun loginScreen(navController:NavController){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authViewModel: AuthViewModel = viewModel()

    Box{
        Image(painter = painterResource(id =R.drawable.login),
            contentDescription = "Register Background",
            contentScale = ContentScale.FillBounds)

    }
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Text(text ="LOGIN HERE",
            fontSize=40.sp,
            fontFamily= FontFamily.Monospace,
            fontStyle= FontStyle.Italic,
            color= Color.White,
            textAlign= TextAlign.Center,
            modifier= Modifier
                .fillMaxWidth()
                .padding(5.dp))
        Spacer(modifier = Modifier.height(35.dp))
        Image(painter = painterResource(id= R.drawable.logo), contentDescription = "image logo",
            modifier = Modifier
                .fillMaxWidth().height(80.dp),
            contentScale = ContentScale.Fit)
        OutlinedTextField(value = email,
            onValueChange = {email  =it},
            label={ Text("Enter Email") },
            placeholder ={ Text("Please enter your Email") },
            leadingIcon ={ Icon(Icons.Default.Email, contentDescription ="Email icon") },
            modifier = Modifier.fillMaxWidth(0.8f),
            textStyle = TextStyle(color=Color.Blue)
        )
        OutlinedTextField(value = password,
            onValueChange = {password=it},
            label={ Text("Enter Password") },
            placeholder ={ Text("Please enter your password") },
            leadingIcon ={ Icon(Icons.Default.Lock , contentDescription ="Lock icon") },
            visualTransformation= PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(0.8f),
            textStyle = TextStyle(color=Color.Blue),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password))
        Spacer(modifier = Modifier.height(25.dp))
        val context = LocalContext.current
        Button(onClick = {
            authViewModel.login(email=email,password=password,navController=navController,context=context)
        }, colors = ButtonDefaults.buttonColors(Color.LightGray),
            modifier = Modifier.fillMaxWidth(0.8f))
        { Text(text = "Login", color = Color.Black)  }
        Text(text ="If not registered, register here",
            color =Color.White ,
            modifier = Modifier.clickable{navController.navigate(ROUTE_REGISTER)})

    }
}

annotation class composable

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun loginScreenPreview(){
    loginScreen(rememberNavController())
}