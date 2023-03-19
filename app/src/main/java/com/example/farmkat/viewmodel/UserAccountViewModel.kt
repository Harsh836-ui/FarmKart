package com.example.farmkat.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmkat.FarmApplication
import com.example.farmkat.data.User
import com.example.farmkat.util.RegisterValidation
import com.example.farmkat.util.Resource
import com.example.farmkat.util.validateEmail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class UserAccountViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val storage: StorageReference,
    app: Application
) : AndroidViewModel(app) {

    private val _user = MutableStateFlow<Resource<User>>(Resource.Unspecified())
    val user = _user
        .asStateFlow()

    private val _updateinfo = MutableStateFlow<Resource<User>>(Resource.Unspecified())
    val updateinfo = _updateinfo.asStateFlow()

    init {
        getUser()
    }

    //function to get user info from firestore

    fun getUser() {
        viewModelScope.launch {
            _user.emit(Resource.Loading())
        }
        firestore.collection("user").document(auth.uid!!).get()
            .addOnSuccessListener {
                //inside here we get the user object
                val user = it.toObject(User::class.java)
                //and if user is not null then
                user?.let {
                    //now we can launch viewmodel scope to emit the success state
                    viewModelScope.launch {
                        _user.emit(Resource.Success(it))
                    }
                }
            }.addOnFailureListener {
                //emit the error
                viewModelScope.launch {
                    _user.emit(Resource.Error(it.message.toString()))
                }
            }
    }

    //function to edit on user information:
    fun updateUser(user: User, imageUri: Uri?) {

        //first we check if inputs are valid that is email is registered and first and last name is not empty

        val areInputsvalid = validateEmail(user.email) is RegisterValidation.Success
                && user.firstName.trim().isNotEmpty()
                && user.lastName.trim().isNotEmpty()

        //after that if inputs are not valid we say it's error
        if (!areInputsvalid) {
            viewModelScope.launch {
                _user.emit(Resource.Error("Check your inputs"))
            }

            return
        }

        viewModelScope.launch {
            _updateinfo.emit(Resource.Loading())
        }

        //if image is not null that means user has uploaded the image then we save its info otherwise we save the info with new image
        if (imageUri == null) {
            saveUserInformation(user, true)
        } else {
            saveUserInformationWithNewImage(user, imageUri)
        }


    }

    private fun saveUserInformationWithNewImage(user: User, imageUri: Uri) {
        //in this fun we will upload the image in firestorage and then we will call the saveuserinformation function
        //i.e upload the image then call this function and send the imagepath
        //here we use firebase with coroutines

        viewModelScope.launch {
            try {
                //to get the bitmap of image
                val imageBitmap = MediaStore.Images.Media.getBitmap(
                    getApplication<FarmApplication>().contentResolver,
                    imageUri
                )
                //after that we want to compress image
                val byteArrayOutputStream = ByteArrayOutputStream()
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 96, byteArrayOutputStream)
                val imageByteArray =
                    byteArrayOutputStream.toByteArray() //to get the byte array of image
                //now to save that
                val imageDirectory =
                    storage.child("profileImages/${auth.uid} / ${UUID.randomUUID()}")

                val result = imageDirectory.putBytes(imageByteArray).await()
                //to get imageurl in form of string
                val imageUrl = result.storage.downloadUrl.await().toString()
                //now we have the image url and thus we save its info and call that fun
                saveUserInformation(user.copy(imagePath = imageUrl), false)

            } catch (e: Exception) {
                //emit an error
                viewModelScope.launch {
                    _user.emit(Resource.Error(e.message.toString()))
                }
            }
        }

    }

    private fun saveUserInformation(user: User, shouldRetrievedOldImage: Boolean) {

        //here we run a transaction becase if that boolean is true then we first retrieve the old image and then update the userdata
        firestore.runTransaction { transaction ->
            //below line is used to access from firebase collection that is in user its document
            val documentRef = firestore.collection("user").document(auth.uid!!)

            if (shouldRetrievedOldImage) {
                //now we replace the old image with new image
                val currentUser = transaction.get(documentRef).toObject(User::class.java)
                val newUser = user.copy(imagePath = currentUser?.imagePath ?: "")
                transaction.set(documentRef, newUser)
            } else {
                transaction.set(documentRef, user)

            }


        }.addOnSuccessListener {
            viewModelScope.launch {
                _updateinfo.emit(Resource.Success(user))
            }
        }.addOnFailureListener {
            viewModelScope.launch {
                _updateinfo.emit(Resource.Error(it.message.toString()))
            }
        }

    }

}