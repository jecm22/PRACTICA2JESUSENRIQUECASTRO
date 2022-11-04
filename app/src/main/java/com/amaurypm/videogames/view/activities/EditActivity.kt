package com.amaurypm.videogames.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.amaurypm.videogames.R
import com.amaurypm.videogames.db.DbPhones
import com.amaurypm.videogames.databinding.ActivityEditBinding
import com.amaurypm.videogames.model.Phone

class EditActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityEditBinding
    private var marca = ""
    private lateinit var dbPhones: DbPhones
    var phone: Phone? = null
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinner: Spinner = binding.spinner3
        spinner.onItemSelectedListener = this
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.phones,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        val bundle = intent.extras

        if(bundle!=null){
            id = bundle.getInt("ID",0)
        }

        dbPhones = DbPhones(this)

        phone = dbPhones.getPhone(id)

        phone?.let{ phone ->
            with(binding){
                tietmodelo.setText(phone.modelo)

                for (i in 0 until spinner.count) {
                    if (spinner.getItemAtPosition(i).toString().equals(phone.marca)) {
                        spinner.setSelection(i)
                        break
                    }
                }

                tietcapacidad.setText(phone.capacidad)

            }
        }
    }

    fun click(view: View) {
        with(binding){
            when{
                tietmodelo.text.toString().isEmpty() -> {
                    tietmodelo.error = resources.getString(R.string.novacio)
                    Toast.makeText(this@EditActivity, resources.getString(R.string.llenetodo), Toast.LENGTH_SHORT).show()
                }

                tietcapacidad.text.toString().isEmpty() -> {
                    tietcapacidad.error = resources.getString(R.string.novacio)
                    Toast.makeText(this@EditActivity, resources.getString(R.string.llenetodo), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    if(dbPhones.updatePhone(id, tietmodelo.text.toString(), spinner3.selectedItem as String, tietcapacidad.text.toString())){
                        Toast.makeText(this@EditActivity, resources.getString(R.string.exito), Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@EditActivity, DetailsActivity::class.java)
                        intent.putExtra("ID", id)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this@EditActivity, resources.getString(R.string.error), Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, DetailsActivity::class.java))
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        marca = parent.getItemAtPosition(pos).toString()

        when(marca){
            parent.getItemAtPosition(0).toString() -> {
                binding.ivHeader.setImageResource(R.drawable.apple)
            }
            parent.getItemAtPosition(1).toString() ->{
                binding.ivHeader.setImageResource(R.drawable.huawei)
            }
            parent.getItemAtPosition(2).toString() -> {
                binding.ivHeader.setImageResource(R.drawable.samsung)
            }
            else ->{
                binding.ivHeader.visibility = View.GONE
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {

    }
}