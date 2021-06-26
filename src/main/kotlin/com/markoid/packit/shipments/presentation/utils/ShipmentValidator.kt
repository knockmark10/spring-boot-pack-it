package com.markoid.packit.shipments.presentation.utils

object ShipmentValidator {

    fun isFullNameValid(name: String): Boolean {
        return name.length >= 6 && name.contains(" ")
    }

    fun isNameOrLastNameValid(name: String): Boolean {
        return name.length >= 4
    }

    fun isShipmentNameValid(name: String): Boolean {
        return name.length >= 6
    }

    fun isCityValid(city: String): Boolean {
        return city.length >= 2
    }

    fun isValidLatitudeOrLongitude(value: Double): Boolean {
        return value != 0.0
    }

    fun isPasswordValid(password: String): Boolean =
        password.isNotEmpty() && password.length >= 6

    fun passwordsMatch(pwd1: String, pwd2: String): Boolean = pwd1 == pwd2

}