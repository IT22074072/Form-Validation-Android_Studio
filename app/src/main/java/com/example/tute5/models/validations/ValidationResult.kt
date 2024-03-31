package com.example.tute5.models.validations
sealed class ValidationResult {

    //there are 3 states when getting the id input

    data class Empty(val errorMessage:String):ValidationResult()
    data class Invalid(val errorMessage: String):ValidationResult()
    object Valid:ValidationResult()

}