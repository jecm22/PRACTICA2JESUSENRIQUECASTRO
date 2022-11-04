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
import com.amaurypm.videogames.view.activities.MainActivity
import com.amaurypm.videogames.databinding.ActivityInsertBinding

class InsertActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityInsertBinding
    private var marca = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinner: Spinner = binding.spinner2
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
            spinner.setSelection(adapter.getPosition("BMW"))
        }
    }

    fun click(view: View) {
        val dbPhones = DbPhones(this)
        with(binding){
            when{
                tietmodelo.text.toString().isEmpty() -> {
                    tietmodelo.error = resources.getString(R.string.novacio)
                    Toast.makeText(this@InsertActivity, resources.getString(R.string.llenetodo), Toast.LENGTH_SHORT).show()
                }



                tietcapacidad.text.toString().isEmpty() -> {
                    tietcapacidad.error = resources.getString(R.string.novacio)
                    Toast.makeText(this@InsertActivity, resources.getString(R.string.llenetodo), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    //Realizamos la inserción
                    val id = dbPhones.insertPhone(tietmodelo.text.toString(), spinner2.selectedItem as String, tietcapacidad.text.toString())

                    if(id>0){
                        Toast.makeText(this@InsertActivity, resources.getString(R.string.exito), Toast.LENGTH_SHORT).show()
                        tietmodelo.setText("")
                        tietcapacidad.setText("")
                        tietmodelo.requestFocus()
                    }else{
                        Toast.makeText(this@InsertActivity, resources.getString(R.string.error), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


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
        return
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }
}