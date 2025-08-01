package com.example.myapp.ui.theme.screens.patient

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.myapp.R
import com.example.myapp.data.PatientViewModel

@Composable
fun AddPatientScreen(navController: NavController){
    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var nationality by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var phone_number by remember { mutableStateOf("") }
    var diagnosis by remember { mutableStateOf("") }
    val imageUri = rememberSaveable() { mutableStateOf <Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        uri: Uri? ->  uri?.let { imageUri.value=it }
    }
  val patientViewModel:PatientViewModel= viewModel()
    val context= LocalContext.current
    Column (modifier = Modifier.fillMaxSize().padding(15.dp), horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "ADD NEW PATIENT",
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Card(shape = CircleShape, modifier =Modifier.padding(10.dp).size(200.dp)) {
          AsyncImage(model = imageUri.value ?: R.drawable.ic_person,
              contentDescription = null,
              contentScale = ContentScale.Crop,
              modifier = Modifier.size(200.dp).clickable {
                  launcher.launch("image/*")
              })
        }
        Text(text = "Upload Picture Here")
        OutlinedTextField(
            value = name,
            onValueChange = {  name = it },
            label={ Text("Enter name", color = Color.Red) },
            placeholder ={ Text("Please enter your name", color = Color.Red) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color=Color.Black),

            )
        OutlinedTextField(
            value = age,
            onValueChange = {  age = it },
            label={ Text("Enter Age",color=Color.Red) },
            placeholder ={ Text("Please enter age",color=Color.Red) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color=Color.Black),
            )
        OutlinedTextField(
            value = gender,
            onValueChange = {  gender = it },
            label={ Text("Enter Gender",color=Color.Red) },
            placeholder ={ Text("Please enter gender",color=Color.Red) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color=Color.Black),
            )
        OutlinedTextField(
            value = phone_number,
            onValueChange = {  phone_number = it },
            label={ Text("Enter phone number",color=Color.Red) },
            placeholder ={ Text("Please enter your phone number",color=Color.Red) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color=Color.Black)
            )
        OutlinedTextField(
            value = nationality,
            onValueChange = {  nationality = it },
            label={ Text("Enter Nationality",color=Color.Red) },
            placeholder ={ Text("Please enter nationality",color=Color.Red) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color=Color.Black)
            )

        OutlinedTextField(
            value = diagnosis,
            onValueChange = {  diagnosis = it },
            label={ Text("Enter Diagnosis",color=Color.Red) },
            placeholder ={ Text("Please enter diagnosis",color=Color.Red) },
            modifier = Modifier.fillMaxWidth().height(150.dp),
            textStyle = TextStyle(color=Color.Black),
            singleLine = false

        )
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
            Button(onClick = {}) { Text(text = "GO BACK") }
            Button(onClick = {patientViewModel.uploadPatient(imageUri.value,
                name,
                gender,
                nationality,
                age,
                phone_number,
                diagnosis,
                context)
            }) { Text(text = "SAVE") }
        }

    }

}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddPatientScreenPreview(){
    AddPatientScreen(rememberNavController())
}

