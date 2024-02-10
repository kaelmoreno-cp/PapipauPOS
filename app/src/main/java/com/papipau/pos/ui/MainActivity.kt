package com.papipau.pos.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.papipau.pos.EnterUpcDialog
import com.papipau.pos.R
import com.papipau.pos.databinding.ActivityMainBinding
import com.papipau.pos.models.AddedProduct
import com.papipau.pos.models.Product
import timber.log.Timber
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val list = arrayListOf<AddedProduct>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        clear()
        test()

        initRecyclerView()
        initClicks()
        initDateTime()
    }

    private fun initDateTime() {
        binding.apply {
            val dateTime = SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss",
                Locale.getDefault()
            ).format(Calendar.getInstance().time)
            dateTimeTextView.text = "DATE/TIME: $dateTime"
        }
    }

    private fun initRecyclerView() {
        val transactionItemsRecyclerViewAdapter = TransactionItemsRecyclerViewAdapter(list)
        binding.itemsRecyclerView.adapter = transactionItemsRecyclerViewAdapter
        computeTotal()
    }

    private fun clear() {
        list.clear()
        list.add(AddedProduct())
        computeTotal()
    }

    private fun computeTotal() {
        var total = BigDecimal(0.0)
        list.forEach {
            total = total.add(it.product.price.multiply(BigDecimal(1)))
        }
        val df = DecimalFormat("#,###,###.00")
        binding.totalTextView.text = "P ${df.format(total)}"
        Timber.e("TOTAL ${df.format(total)}")
    }

    private fun initClicks() {
        binding.cancelButton.setOnClickListener {
            showAlert(getString(R.string.cancel), "Cancel this transaction?") {
                clear()
                val transactionItemsRecyclerViewAdapter = TransactionItemsRecyclerViewAdapter(list)
                binding.itemsRecyclerView.adapter = transactionItemsRecyclerViewAdapter
            }
        }

    }

    private fun showAlert(title: String, message: String, onOk: () -> Unit) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                onOk()
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .create().show()
    }


    private fun test() {
        list.add(
            AddedProduct(
                Product(
                    upc = "09123432",
                    name = "Samsung S23 Ultra 12gb/256gb",
                    price = BigDecimal(62000)
                ),
                1
            )
        )
        list.add(
            AddedProduct(
                Product(
                    upc = "9182331",
                    name = "Nokia 3310",
                    price = BigDecimal(4000)
                ),
                1
            )
        )
        list.add(
            AddedProduct(
                Product(
                    upc = "5212434",
                    name = "AquaFlash Blue",
                    price = BigDecimal(500)
                ),
                1
            )
        )
        list.add(
            AddedProduct(
                Product(
                    upc = "8236253",
                    name = "Edifier Speaker",
                    price = BigDecimal(4000)
                ),
                1
            )
        )
        list.add(
            AddedProduct(
                Product(
                    upc = "7245223",
                    name = "Charger 30watts",
                    price = BigDecimal(2000)
                ),
                1
            )
        )
    }
}
