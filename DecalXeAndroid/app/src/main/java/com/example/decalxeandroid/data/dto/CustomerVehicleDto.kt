package com.example.decalxeandroid.data.dto

import com.google.gson.annotations.SerializedName

data class CustomerVehicleDto(
    @SerializedName("vehicleID")
    val vehicleID: String,
    @SerializedName("customerID")
    val customerID: String,
    @SerializedName("customerFullName")
    val customerFullName: String,
    @SerializedName("chassisNumber")
    val chassisNumber: String,
    @SerializedName("licensePlate")
    val licensePlate: String?,
    @SerializedName("color")
    val color: String?,
    @SerializedName("year")
    val year: Int?,
    @SerializedName("initialKM")
    val initialKM: Double?,
    @SerializedName("modelID")
    val modelID: String,
    @SerializedName("modelName")
    val modelName: String,
    @SerializedName("brandName")
    val brandName: String
)

data class CreateCustomerVehicleDto(
    @SerializedName("customerID")
    val customerID: String,
    @SerializedName("chassisNumber")
    val chassisNumber: String,
    @SerializedName("licensePlate")
    val licensePlate: String?,
    @SerializedName("color")
    val color: String?,
    @SerializedName("year")
    val year: Int?,
    @SerializedName("initialKM")
    val initialKM: Double?,
    @SerializedName("modelID")
    val modelID: String
)

data class UpdateCustomerVehicleDto(
    @SerializedName("customerID")
    val customerID: String?,
    @SerializedName("chassisNumber")
    val chassisNumber: String?,
    @SerializedName("licensePlate")
    val licensePlate: String?,
    @SerializedName("color")
    val color: String?,
    @SerializedName("year")
    val year: Int?,
    @SerializedName("initialKM")
    val initialKM: Double?,
    @SerializedName("modelID")
    val modelID: String?
)
