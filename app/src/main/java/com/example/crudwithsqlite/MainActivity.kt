package com.example.crudwithsqlite

import android.content.DialogInterface
import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var firstname: EditText? = null
    var lastname: EditText? = null
    var textView: TextView? = null
    var controller: DB_Controller? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firstname = findViewById<View>(R.id.firstname_input) as EditText
        lastname = findViewById<View>(R.id.lastname_input) as EditText
        textView = findViewById<View>(R.id.textView) as TextView
        controller = DB_Controller(this, "", null, 1)
    }

    fun btn_click(view: View) {
        when (view.getId()) {
            R.id.btn_add -> try {
                controller!!.insert_student(firstname!!.text.toString(), lastname!!.text.toString())
            } catch (e: SQLiteException) {
                Toast.makeText(this@MainActivity, "ALREADY EXIST", Toast.LENGTH_SHORT).show()
            }
            R.id.btn_delete -> controller!!.delete_student(firstname!!.text.toString())
            R.id.btn_update -> {
                val dialog: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
                dialog.setTitle("ENTER NEW FIRSTNAME ")
                val new_firstname = EditText(this)
                dialog.setView(new_firstname)
                dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i -> controller!!.update_student(firstname!!.text.toString(), new_firstname.text.toString()) })
                dialog.show()
            }
            R.id.btn_list -> controller!!.list_all_students(textView!!)
        }
    }
}