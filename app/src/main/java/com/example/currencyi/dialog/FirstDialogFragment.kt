package com.example.currencyi.dialog

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.currencyi.R

class FirstDialogFragment : DialogFragment(R.layout.custom_dialog) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view) {
            findViewById<Button>(R.id.removeButton).setOnClickListener {
                (activity as FirstDialogCallBack).onCurrencyRemove()
            }

            findViewById<Button>(R.id.cancelButton).setOnClickListener {
                dismiss()
            }

        }
    }

}

interface FirstDialogCallBack {
    fun onCurrencyRemove()
}