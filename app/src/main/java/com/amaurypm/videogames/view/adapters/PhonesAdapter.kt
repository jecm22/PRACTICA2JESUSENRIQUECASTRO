package com.amaurypm.videogames.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amaurypm.videogames.R
import com.amaurypm.videogames.view.activities.MainActivity
import com.amaurypm.videogames.databinding.ListElementBinding
import com.amaurypm.videogames.model.Phone

/**
 * Creado por Amaury Perea Matsumura el 22/10/22
 */
class PhonesAdapter(private val context: Context, val phones: ArrayList<Phone>): RecyclerView.Adapter<PhonesAdapter.ViewHolder>(){

    private val layoutInflater = LayoutInflater.from(context)

    class ViewHolder(view: ListElementBinding): RecyclerView.ViewHolder(view.root){
        val tvmodelo = view.tvmodelo
        val tvmarca = view.tvmarca
        val tvcapacidad = view.tvcapacidad
        val image = view.imageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListElementBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvmodelo.text = phones[position].modelo
        holder.tvmarca.text = phones[position].marca
        holder.tvcapacidad.text = phones[position].capacidad
        when(phones[position].marca){
            "Apple" -> {
                holder.image.setImageResource(R.drawable.apple)
            }
            "Huawei" -> {
                holder.image.setImageResource(R.drawable.huawei)
            }
            "Samsung" -> {
                holder.image.setImageResource(R.drawable.samsung)
            }
            else ->{
                holder.image.setImageResource(R.drawable.apple)
            }
        }


        //Para los clicks de cada elemento viewholder

        holder.itemView.setOnClickListener {
            //Manejar el click
            if(context is MainActivity) context.selectedPhone(phones[position])
        }

    }

    override fun getItemCount(): Int {
        return phones.size
    }

}