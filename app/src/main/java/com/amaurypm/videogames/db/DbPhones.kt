package com.amaurypm.videogames.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.amaurypm.videogames.db.DbHelper
import com.amaurypm.videogames.model.Phone

/**
 * Creado por Amaury Perea Matsumura el 21/10/22
 */
class DbPhones(private val context: Context): DbHelper(context) {

    //Aquí se van a implementar las operaciones CRUD (Create, Read, Update and Delete)

    fun insertPhone(modelo: String, marca:String, capacidad: String): Long{
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        var id: Long = 0

        try{
            val values = ContentValues()

            values.put("modelo", modelo)
            values.put("marca", marca)
            values.put("capacidad", capacidad)

            id = db.insert("phones", null, values)

        }catch(e: Exception){
            //Manejo de excepción
        }finally {
            db.close()
        }

        return id
    }

    fun getPhones(): ArrayList<Phone>{
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        var listPhones = ArrayList<Phone>()
        var PhoneTmp: Phone? = null
        var cursorPhones: Cursor? = null

        cursorPhones = db.rawQuery("SELECT * FROM phones", null)

        if(cursorPhones.moveToFirst()){
            do{
                PhoneTmp = Phone(cursorPhones.getInt(0), cursorPhones.getString(1), cursorPhones.getString(2), cursorPhones.getString(3))
                listPhones.add(PhoneTmp)
            }while(cursorPhones.moveToNext())
        }

        cursorPhones.close()

        return listPhones
    }

    fun getPhone(id: Int): Phone?{
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        var Phone: Phone? = null

        var cursorPhones: Cursor? = null

        cursorPhones = db.rawQuery("SELECT * FROM PhoneS WHERE id = $id LIMIT 1", null)

        if(cursorPhones.moveToFirst()){
            Phone = Phone(cursorPhones.getInt(0), cursorPhones.getString(1), cursorPhones.getString(2), cursorPhones.getString(3))
        }

        cursorPhones.close()

        return Phone
    }

    fun updatePhone(id: Int, modelo: String, marca: String, capacidad: String): Boolean{
        var banderaCorrecto = false

        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        try{
            db.execSQL("UPDATE PhoneS SET modelo = '$modelo', marca = '$marca', capacidad = '$capacidad' WHERE id = $id")
            banderaCorrecto = true
        }catch(e: Exception){
            //Manejo de la excepción
        }finally {
            db.close()
        }

        return banderaCorrecto
    }

    fun deletePhone(id: Int): Boolean{
        var banderaCorrecto = false

        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        try{
            db.execSQL("DELETE FROM phones WHERE id = $id")
            banderaCorrecto = true
        }catch(e: Exception){

        }finally {
            db.close()
        }

        return banderaCorrecto
    }

}