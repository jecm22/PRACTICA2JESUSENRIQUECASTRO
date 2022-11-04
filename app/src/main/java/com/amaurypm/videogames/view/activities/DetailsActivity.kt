package com.amaurypm.videogames.view.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.amaurypm.videogames.db.DbPhones
import com.amaurypm.videogames.R
import com.amaurypm.videogames.databinding.ActivityDetailsBinding
import com.amaurypm.videogames.model.Phone

class DetailsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityDetailsBinding
    private var marca = ""
    private lateinit var dbPhones: DbPhones
    var phone: Phone? = null
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinner: Spinner = binding.spinner1
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

        val myAdap = binding.spinner1.adapter

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

                //para que se desactive el teclado en los edittext
                tietmodelo.inputType = InputType.TYPE_NULL
                tietcapacidad.inputType = InputType.TYPE_NULL
            }
        }
        spinner.setEnabled(false);
    }

    fun click(view: View) {
        when(view.id){
            R.id.btnEdit -> {
                val intent = Intent(this, EditActivity::class.java)
                intent.putExtra("ID", id)
                startActivity(intent)
                finish()
            }
            R.id.btnDelete -> {
                //Aquí iría el código para borrar el registro

                AlertDialog.Builder(this)
                    .setTitle(resources.getString(R.string.confirmacion))
                    .setMessage(resources.getString(R.string.seguroeliminar,phone?.modelo))
                    .setPositiveButton(resources.getString(R.string.aceptar), DialogInterface.OnClickListener { dialog, which ->
                        if(dbPhones.deletePhone(id)){
                            Toast.makeText(this@DetailsActivity, resources.getString(R.string.exito), Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@DetailsActivity, MainActivity::class.java))
                            finish()
                        }else{
                            Toast.makeText(this@DetailsActivity, resources.getString(R.string.error), Toast.LENGTH_SHORT).show()
                        }
                    })
                    .setNegativeButton(resources.getString(R.string.cancelar), DialogInterface.OnClickListener { dialog, which ->
                      dialog.dismiss()
                    })
                    .show()

            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
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

    override fun onNothingSelected(p0: AdapterView<*>?) {
        return
    }
}