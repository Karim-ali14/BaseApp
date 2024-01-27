package com.karimali.baseapp.common.utils

object Enums {

    enum class EditTextInputType(val value: String){
        Phone("phone"),
        Password("password"),
        Text("text"),
        Email("email")
    }

    enum class NavigationTypes {
        SignUp,
        ResetPassword
    }

    enum class HomeItemsType{
        Banners,
        Section,
        Products,
        Services,
    }


}