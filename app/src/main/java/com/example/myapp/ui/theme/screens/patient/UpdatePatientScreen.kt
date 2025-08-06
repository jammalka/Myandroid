package com.example.myapp.ui.theme.screens.patient


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.painterResource
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
import com.example.myapp.models.Patient
import com.example.myapp.navigation.ROUTE_VIEW_PATIENT
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

@Composable
fun UpdatePatientScreen(navController: NavController,patientId:String){
    var patientViewModel:PatientViewModel= viewModel()
    var patient by remember { mutableStateOf<Patient?>(null) }
    LaunchedEffect(patientId) {
        val ref=FirebaseDatabase.getInstance().getReference("Patients").child(patientId)
        val snapshot =ref.get().await()
        patient=snapshot.getValue(Patient::class.java)?.apply {
            id=patientId
        }
    }
    if (patient==null){
        Box(modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center)
        { CircularProgressIndicator() }
        return
    }
    var name by remember { mutableStateOf(patient!!.name?:"") }
    var gender by remember { mutableStateOf(patient!!.gender?:"") }
    var nationality by remember { mutableStateOf(patient!!.nationality?:"") }
    var age by remember { mutableStateOf(patient!!.age?:"") }
    var phone_number by remember { mutableStateOf(patient!!.phone_number?:"") }
    var diagnosis by remember { mutableStateOf(patient!!.diagnosis?:"") }

    val imageUri = remember() { mutableStateOf <Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent())
    {   it?.let { Uri-> imageUri.value=Uri }
    }
    val context= LocalContext.current
    Box{
        Image(painter = painterResource(id =R.drawable.joy),
            contentDescription = "Register Background",
            contentScale = ContentScale.FillBounds)

    }
    Column (modifier = Modifier.fillMaxSize().padding(15.dp), horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "UPDATE PATIENT",
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Card(shape = CircleShape, modifier =Modifier.padding(10.dp).size(200.dp)) {
            AsyncImage(model = imageUri.value ?:patient!!.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp).clickable {
                    launcher.launch("image/*")
                })
        }
        Text(text = "Tap To Change Picture")
        OutlinedTextField(
            value = name,
            onValueChange = {  name = it },
            label={ Text("Enter name", color = Color.Red) },
            placeholder ={ Text("Please enter your name", color = Color.Red) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color=Color.White),

            )
        OutlinedTextField(
            value = age,
            onValueChange = {  age = it },
            label={ Text("Enter Age",color=Color.Red) },
            placeholder ={ Text("Please enter age",color=Color.Red) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color=Color.White),
        )
        OutlinedTextField(
            value = gender,
            onValueChange = {  gender = it },
            label={ Text("Enter Gender",color=Color.Red) },
            placeholder ={ Text("Please enter gender",color=Color.Red) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color=Color.White),
        )
        OutlinedTextField(
            value = phone_number,
            onValueChange = {  phone_number = it },
            label={ Text("Enter phone number",color=Color.Red) },
            placeholder ={ Text("Please enter your phone number",color=Color.Red) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color=Color.White)
        )
        OutlinedTextField(
            value = nationality,
            onValueChange = {  nationality = it },
            label={ Text("Enter Nationality",color=Color.Red) },
            placeholder ={ Text("Please enter nationality",color=Color.Red) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color=Color.White)
        )

        OutlinedTextField(
            value = diagnosis,
            onValueChange = {  diagnosis = it },
            label={ Text("Enter Diagnosis",color=Color.Red) },
            placeholder ={ Text("Please enter diagnosis",color=Color.Red) },
            modifier = Modifier.fillMaxWidth().height(150.dp),
            textStyle = TextStyle(color=Color.White),
            singleLine = false

        )
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
            Button(onClick = {navController.navigate(ROUTE_VIEW_PATIENT)}) { Text(text = "GO BACK") }
            Button(onClick = {}) { Text(text = "UPDATE") }
        }

    }

}


