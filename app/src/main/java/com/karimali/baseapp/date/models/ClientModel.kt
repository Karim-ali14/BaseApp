package com.karimali.baseapp.date.models

data class ClientModel(
    val created_at: String,
    val email: String,
    val email_verified_at: String,
    val id: Int,
    val image: String,
    val is_active: String,
    val name: String,
    val phone: String,
    val updated_at: String,
    var token:String
)