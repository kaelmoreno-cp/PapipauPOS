package com.papipau.pos

import android.graphics.Point
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.papipau.pos.databinding.DialogEnterUpcBinding


class EnterUpcDialog(
    private val onOkClick: (String, Int) -> Unit
): DialogFragment() {

    lateinit var binding: DialogEnterUpcBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogEnterUpcBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.okButton.setOnClickListener {
            val upc = binding.upcEditText.text.toString().trim()
            val quantity = binding.upcEditText.text.toString().trim()

            if (upc.isEmpty() || quantity.isEmpty()) {
                Toast.makeText(requireContext(), "Invalid UPC", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            onOkClick(upc, quantity.toInt())
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }



    override fun onResume() {
        super.onResume()
        val window = dialog!!.window
        val size = Point()
        val display = window!!.windowManager.defaultDisplay
        display.getSize(size)
        val width = size.x
        window.setLayout((width * 0.80).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
    }

}