package com.example.myapp.data

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.models.Patient
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.InputStream

class PatientViewModel:ViewModel() {
    val cloudinaryUrl="https://api.cloudinary.com/v1_1/dfwzynrgx/image/upload"
   val   uploadPreset="MY_APP_IMAGES"
    fun uploadPatient(imageUri: Uri?, name:String, gender:String, nationality:String, age:String,phone_number:String, diagnosis:String, context: Context){
        viewModelScope.launch (Dispatchers.IO ){
            try{
                val imageUrl=imageUri?.let{uploadToCloudinary(context,it)}
                val ref=FirebaseDatabase.getInstance().getReference("Patients").push()
                val patientData=mapOf(
                    "id" to ref.key,
                    "name" to name,
                    "gender" to gender,
                    "nationality" to  nationality,
                    "age" to age,
                    "phone_number" to phone_number,
                    "diagnosis" to diagnosis,
                    "imageUrl" to imageUrl
                )
                ref.setValue(patientData).await()
                withContext(Dispatchers.Main){
                    Toast.makeText(context, "Patient saved successfully", Toast.LENGTH_LONG).show()

                }
            }catch (e:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(context, "Patient NOT saved ", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun uploadToCloudinary(context: Context,uri: Uri):String{
        val contentResolver=context.contentResolver
        val inputStream: InputStream?=contentResolver.openInputStream(uri)
        val fileBytes =inputStream?.readBytes()?:throw Exception("Image Read Failed")
        val requestBody=MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("file","image.jpg",
              RequestBody.create("image*/".toMediaTypeOrNull(),fileBytes))
            .addFormDataPart("upload_preset",uploadPreset).build()
        val request = Request.Builder().url(cloudinaryUrl).post(requestBody).build()
        val response = OkHttpClient().newCall(request).execute()
        if (!response.isSuccessful) throw Exception("Upload Failed")
        val responseBody = response.body?.string()
        val secureUrl = Regex("\"secure_url\":\"(.*?)\"")
            .find(responseBody ?: "")?.groupValues?.get(1)
        return secureUrl ?: throw Exception("Failed to get image URL")





    }
    private val _patients= mutableStateListOf<Patient>()
    val patients:List<Patient> = _patients
    fun fetchPatients(context: Context){
        val ref =FirebaseDatabase.getInstance().getReference("Patients")
        ref.get().addOnSuccessListener{snapshot->
           _patients.clear()
            for(child in snapshot.children){
                val patient=child.getValue(Patient::class.java)
                patient?.let{ _patients.add(it)}
            }
        }.addOnFailureListener {
            Toast.makeText(context, " Failed to load patients. ", Toast.LENGTH_LONG).show()

        }
    }
    fun deletePatient(patientId:String,context: Context){
        val ref= FirebaseDatabase.getInstance()
            .getReference("Patients").child(patientId)
        ref.removeValue().addOnSuccessListener {
            _patients.removeAll{it.id==patientId}}.addOnFailureListener{
            Toast.makeText(context, " Patient not deleted. ", Toast.LENGTH_LONG).show()
        }
        }
    fun updatePatient(patientId: String,
                      imageUri: Uri?,
                      name: String,
                      age: String,
                      gender: String,
                      phone_number: String,
                      nationality: String,
                      diagnosis: String,
                      context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val imageUrl=imageUri?.let { uploadToCloudinary(context,it) }
                val updatePatient= mapOf(
                    "id" to patientId,
                    "name" to name,
                    "age" to age,
                    "gender" to gender,
                    "nationality" to  nationality,
                    "phone_number" to phone_number,
                    "diagnosis" to diagnosis,
                    "imageUrl" to imageUrl
                )
                val ref =FirebaseDatabase.getInstance().getReference("Patients")
                    .child(patientId)
                ref.setValue(updatePatient).await()
                fetchPatients(context)
                withContext(Dispatchers.Main){
                    Toast.makeText(context, " PatientS Updated Successfully. ", Toast.LENGTH_LONG).show()
                }
            }catch (e:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(context, "Failed to Update Patient  ", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    }
