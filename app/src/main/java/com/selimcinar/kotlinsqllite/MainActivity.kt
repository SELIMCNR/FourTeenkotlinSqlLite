package com.selimcinar.kotlinsqllite

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputBinding
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.selimcinar.kotlinsqllite.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        try {
            val myDatabase = this.openOrCreateDatabase("Musicians",Context.MODE_PRIVATE,null)
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS musicians (id INTEGER PRIMARY KEY ,name VARCHAR,age INT)")

            //add
           // myDatabase.execSQL("INSERT INTO musicians(name,age) VALUES('James',50) ")
             myDatabase.execSQL("INSERT INTO musicians(name,age) VALUES('Lars',60) ")
             myDatabase.execSQL("INSERT INTO musicians(name,age) VALUES('Mike',50) ")

            //update
            myDatabase.execSQL("UPDATE musicians SET age =61 WHERE name= 'James'")
            myDatabase.execSQL("UPDATE musicians SET age ='Messi' WHERE id=3")

            //delete
            myDatabase.execSQL("DELETE FROM musicians WHERE name='James'")


            //val cursor = myDatabase.rawQuery("SELECT * FROM musicians",null)
           // val cursor = myDatabase.rawQuery("SELECT * FROM musicians WHERE name='Lars'",null)
           // val cursor = myDatabase.rawQuery("SELECT * FROM musicians WHERE id = 3",null)
           // val cursor = myDatabase.rawQuery("SELECT * FROM musicians WHERE name LIKE '%s'",null) // sonu s biten
            val cursor = myDatabase.rawQuery("SELECT * FROM musicians WHERE name LIKE 'L%'",null) // L ile başlayan

            val nameIx = cursor.getColumnIndex("name")
            val  ageIx = cursor.getColumnIndex("age")
            val idIx = cursor.getColumnIndex("id")
            while (cursor.moveToNext()){
                println("Name: "+cursor.getString(nameIx))
                binding.nametext.text="Name : "+cursor.getString(nameIx)
                println("Age:  "+cursor.getInt(ageIx))
                println("Id: "+cursor.getInt(idIx))
            }
            cursor.close()

        }
        catch (e:Exception){
            e.printStackTrace()
        }

    }
}