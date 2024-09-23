package com.example.farmkat.data.order

import android.os.Parcelable
import com.example.farmkat.data.Address
import com.example.farmkat.data.CartProduct
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random.Default.nextLong

@Parcelize
data class Order(
    //this is another way of giving empty constructor to specify the value on respective rather than writing together in (,,,)
    val orderStatus: String = "",
    val totalPrice: Float = 0f,
    val products: List<CartProduct> = emptyList(),
    val address: Address = Address(),
    val date: String = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date()),
    //to make sure that no 2 orders have same id we take very long range and also add total price which in most case will be different

    val orderId: Long = nextLong(0,100_000_000_000) + totalPrice.toLong()
): Parcelable
