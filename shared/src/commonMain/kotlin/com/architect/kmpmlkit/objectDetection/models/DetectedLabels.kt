package com.architect.kmpmlkit.objectDetection.models

data class DetectedLabels(val labels: List<Pair<String, Float>>, val coordinates: BoundedBoxCoordinates)

