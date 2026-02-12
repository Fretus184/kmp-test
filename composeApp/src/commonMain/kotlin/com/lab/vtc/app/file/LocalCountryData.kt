package com.lab.vtc.app.file

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import vtc.composeapp.generated.resources.Res

@Serializable
data class CountryCodeJson(
    val name: String,
    val flag: String,
    val code: String,
    val dial_code: String
)

suspend fun readJsonFromResource(): List<CountryCodeJson> {
    val jsonString = Res.readBytes(("files/country_dial_info.json")).decodeToString()
    return Json.decodeFromString<List<CountryCodeJson>>(jsonString)
}