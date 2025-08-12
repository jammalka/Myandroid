package com.example.myapp.ui.theme.screens.dashboard

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email

import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

import androidx.compose.material3.Label
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults


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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import com.example.myapp.R
import com.example.myapp.navigation.ROUTE_LOGIN
import com.example.myapp.navigation.ROUTE_PATIENT
import com.example.myapp.navigation.ROUTE_VIEW_PATIENT
import com.google.firebase.auth.FirebaseAuth
import java.time.format.TextStyle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {
    val selectedItem = remember { mutableStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    val context= LocalContext.current
    Scaffold(bottomBar = {
        NavigationBar(containerColor = Color.Black) {
            NavigationBarItem(selected = selectedItem.value==1,
                onClick = {},
                icon = { Icon(Icons.Filled.Share, contentDescription = "share") },
                label = { Text(text = "share") },
                alwaysShowLabel = true)
        NavigationBarItem(selected = selectedItem.value==0,
            onClick = {
                val intent=Intent(Intent.ACTION_DIAL).apply {
                    data=Uri.parse("tel:0700000000")
                }
                context.startActivity(intent)
            },
            icon = { Icon(Icons.Filled.Phone, contentDescription = "Phone") },
            label = { Text(text = "Phone") },
            alwaysShowLabel = true)
            NavigationBarItem(selected = selectedItem.value==2,
                onClick = {val intent=Intent(Intent.ACTION_SENDTO).apply{
                    data=Uri.parse("mailto:info@emobilis.edu")
                    putExtra(Intent.EXTRA_SUBJECT,"Inquiry")
                    putExtra(Intent.EXTRA_TEXT,"How do i open a bank  account")
                }
                    context.startActivity(intent)
                          },
                icon = { Icon(Icons.Filled.Email, contentDescription = "Email") },
                label = { Text(text = "Email") },
                alwaysShowLabel = true)
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Logout") },
                text = { Text("Are you sure you want to log out?") },
                confirmButton = {
                    TextButton(onClick = {
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate(ROUTE_LOGIN) {
                            popUpTo(0) { inclusive = true }
                        }
                    }) {
                        Text("Yes", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("No", color = Color.Green)
                    }
                }
            )
        }

    })
    { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Image(painter = painterResource( R.drawable.ppl),
                contentDescription = "Background Image",
                contentScale =ContentScale.Crop)
        }
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally){
            TopAppBar(title ={ Text(text = "Equity Hosp.")} ,
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                  IconButton(onClick = {}) {
                      Icon(Icons.Filled.Search, contentDescription = "Search")}
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Person, contentDescription = "Person")}
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = "Logout",
                            modifier = Modifier.clickable { showDialog=true })}

                    },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                ))
            Row (modifier = Modifier.wrapContentWidth()){
                Card(
                    modifier = Modifier.padding(10.dp).clickable {navController.navigate(
                        ROUTE_PATIENT)  },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(Color.White)
                ){ Box(modifier = Modifier.height(100.dp).padding(20.dp),
                    contentAlignment = Alignment.Center){
                    Text(text = "Add Patient", color = Color.Black)} }
                Spacer(modifier = Modifier.width(30.dp))
                Card(
                    modifier = Modifier.padding(10.dp).clickable {navController.navigate(
                        ROUTE_VIEW_PATIENT)  },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(Color.White)
                ){ Box(modifier = Modifier.height(100.dp).padding(20.dp),
                    contentAlignment = Alignment.Center){
                    Text(text = "View Patients", color =Color.Black )} }
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview(){
    DashboardScreen(rememberNavController())
}

