package com.example.tute5

import android.os.Bundle
import android.os.Message
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tute5.models.FormData
import com.example.tute5.models.validations.ValidationResult

class MainActivity : AppCompatActivity() {


    lateinit var edtStudentId:EditText
    lateinit var spnYear:Spinner
    lateinit var spnSemester:Spinner
    lateinit var cbAgree:CheckBox

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edtStudentId = findViewById(R.id.edtStudentId)
        spnYear = findViewById(R.id.spnYear)
        spnSemester = findViewById(R.id.spnSemester)
        cbAgree = findViewById(R.id.cbAgree)
    }

    fun displayAlert(title:String, message: String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK"){dialog, which ->

        }

        val dialog = builder.create()
        dialog.show()
    }

    fun submit(v: View){
        val myForm = FormData(
            edtStudentId.text.toString(),
            spnYear.selectedItem.toString(),
            spnSemester.selectedItem.toString(),
            cbAgree.isChecked
            )

        val studentIdValidation = myForm.validateStudentId()
        val spnYearValidation = myForm.validateYear()
        val spnSemesterValidation = myForm.validateSemester()
        val cbAgreeValidation = myForm.validateAgreement()



        //when statements

        when (studentIdValidation){
            is ValidationResult.Valid -> {
                count++
            }

            is ValidationResult.Invalid -> {
                edtStudentId.error = studentIdValidation.errorMessage
            }

            is ValidationResult.Empty -> {
                edtStudentId.error = studentIdValidation.errorMessage
            }
        }

        when (spnYearValidation){
            is ValidationResult.Valid -> {
                count++
            }

            is ValidationResult.Invalid -> {  //we just have to include this

            }

            is ValidationResult.Empty -> {
                val tv:TextView = spnYear.selectedView as TextView
                tv.error = ""
                tv.text = spnYearValidation.errorMessage
            }
        }

        when(spnSemesterValidation){
            is ValidationResult.Valid -> {
                count++
            }

            is ValidationResult.Invalid ->{

            }

            is ValidationResult.Empty -> {
                val tv: TextView = spnSemester.selectedView as TextView
                tv.error = ""
                tv.text = spnSemesterValidation.errorMessage
            }

        }

        when (cbAgreeValidation){
            is ValidationResult.Empty -> {

            }
            is ValidationResult.Invalid -> {
                displayAlert("Error", cbAgreeValidation.errorMessage)
            }
            ValidationResult.Valid -> {
                count++
            }
        }

        if(count == 4){
            displayAlert("Success", "You have successfully registered")
        } else{
            count = 0
        }

    }
}