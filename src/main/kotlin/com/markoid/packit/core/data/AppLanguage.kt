package com.markoid.packit.core.data

enum class AppLanguage(val value: String) {
    ENGLISH("en"),
    SPANISH("es");

    companion object {
        /**
         * Gets the [AppLanguage] for the value provided.
         *
         * @param value - The abbreviation of the language desired.
         * @return [AppLanguage] supported or [AppLanguage.ENGLISH] if not found the provided one.
         */
        fun forValue(value: String): AppLanguage = values().firstOrNull { it.value == value } ?: ENGLISH

    }

}