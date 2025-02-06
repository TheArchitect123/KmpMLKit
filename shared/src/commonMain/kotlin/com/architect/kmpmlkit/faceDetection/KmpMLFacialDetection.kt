package com.architect.kmpmlkit.faceDetection

import com.architect.neuralKmp.typealiases.DefaultActionWithBoolParam
import com.architect.neuralKmp.typealiases.DefaultFacialFeaturesAction

expect class KmpMLFacialDetection {
    companion object {
        fun isFaceSmiling(imageData: ByteArray, action: DefaultActionWithBoolParam)
        fun areEyesOpen(imageData: ByteArray, action: DefaultActionWithBoolParam)

        fun startLiveStreamingFacialDetectionCoordinates(
            imageData: ByteArray,
            liveResults: DefaultFacialFeaturesAction
        )

        fun getFacialDetectionCoordinatesForImageBuffer(
            imageData: ByteArray,
            liveResults: DefaultFacialFeaturesAction
        )

        fun getFacialDetectionCoordinatesForImagePath(
            imagePath: String,
            liveResults: DefaultFacialFeaturesAction
        )
    }
}