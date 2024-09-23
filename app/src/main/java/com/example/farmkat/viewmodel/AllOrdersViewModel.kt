package com.example.farmkat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmkat.data.order.Order
import com.example.farmkat.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllOrdersViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {

    //lets create the state
    private val _allOrders = MutableStateFlow<Resource<List<Order>>>(Resource.Unspecified())

    //export this state to UI
    val allOrders = _allOrders.asStateFlow()

    init {
        getAllorders()
    }

    //function to get all orders
    fun getAllorders() {
        viewModelScope.launch {
            //first we want to emit loading in our state
            _allOrders.emit(Resource.Loading())
        }

        //to retrieve the order for that specific user
        //here we access user then its document from uid then we acccess orders
        firestore.collection("user").document(auth.uid!!).collection("orders").get()
            .addOnSuccessListener {
                val orders = it.toObjects(Order::class.java)
                viewModelScope.launch {
                    _allOrders.emit(Resource.Success(orders))
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _allOrders.emit(Resource.Error(it.message.toString()))
                }
            }
    }
}