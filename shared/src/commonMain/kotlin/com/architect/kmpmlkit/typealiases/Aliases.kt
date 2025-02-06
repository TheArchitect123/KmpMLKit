package com.architect.neuralKmp.typealiases

import com.architect.kmpmlkit.faceDetection.models.FacialCoordinates
import com.architect.kmpmlkit.imageLabeling.models.ImageLabels
import com.architect.kmpmlkit.objectDetection.models.DetectedLabels
import com.architect.kmpmlkit.poseDetection.models.PosePosition

typealias DefaultActionWithBoolParam = (Boolean) -> Unit
typealias DefaultActionWithStringParam = (String?) -> Unit
typealias DefaultManyStringsAction = (List<String>) -> Unit
typealias DefaultFacialFeaturesAction = (List<FacialCoordinates>) -> Unit
typealias DefaultPoseFeaturesAction = (List<PosePosition>) -> Unit
typealias DefaultImageLabelsAction = (List<ImageLabels>) -> Unit
typealias DefaultObjectLabelsAction = (List<DetectedLabels>) -> Unit



