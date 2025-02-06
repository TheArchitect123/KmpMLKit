package com.architect.kmpmlkit.faceDetection.models

data class FacialCoordinates(
    val eulerCoordinates: EulerCoordinates? = null,
    val coordinates: LatitudeLongitude? = null,
    val facialFeature: FacialLandmarks,
    val isSmiling : Boolean = false,
    val isEyesClosed : Boolean = false
)