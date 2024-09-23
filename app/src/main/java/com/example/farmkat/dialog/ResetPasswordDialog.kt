package com.example.farmkat.dialog

import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.farmkat.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.setupBottomSheetDialog(
    onSendClick: (String) ->Unit
){

    val dialog = BottomSheetDialog(requireContext() , R.style.DialogStyle)
    val view = layoutInflater.inflate(R.layout.reset_password_dialog,null)
    dialog.setContentView(view)
    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    dialog.show()

    val edEmail = view.findViewById<EditText>(R.id.edResetPass)
    val buttonSend = view.findViewById<Button>(R.id.BtnsendResetPass)
    val buttoncancel = view.findViewById<Button>(R.id.BtncancelResetPass)

    buttonSend.setOnClickListener{
        val email = edEmail.text.toString().trim()
        onSendClick(email)
        dialog.dismiss()

    }

    buttoncancel.setOnClickListener{
        dialog.dismiss()
    }
}