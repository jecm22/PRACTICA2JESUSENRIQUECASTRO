package com.amaurypm.videogames.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.amaurypm.videogames.db.DbPhones
import com.amaurypm.videogames.model.Phone
import com.amaurypm.videogames.databinding.ActivityMainBinding
import com.amaurypm.videogames.view.adapters.PhonesAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var listPhones: ArrayList<Phone>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val dbPhones = DbPhones(this)

        listPhones = dbPhones.getPhones()

        val PhonesAdapter = PhonesAdapter(this, listPhones)
        binding.rvPhones.layoutManager = LinearLayoutManager(this)
        binding.rvPhones.adapter = PhonesAdapter

        if(listPhones.size == 0) binding.tvSinRegistros.visibility = View.VISIBLE
        else binding.tvSinRegistros.visibility = View.INVISIBLE

    }

    fun click(view: View) {
        startActivity(Intent(this, InsertActivity::class.java))
        finish()
    }

    fun selectedPhone(phone: Phone){
        //Manejamos el click del elemento en el recycler view
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("ID", phone.id)
        startActivity(intent)
        finish()
    }
}